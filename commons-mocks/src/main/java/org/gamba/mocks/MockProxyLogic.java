package org.gamba.mocks;

import java.lang.reflect.Method;

import org.gamba.mocks.exception.GambaMockException;
import org.gamba.mocks.fluent.Mask;
import org.gamba.mocks.sequences.ISequence;
import org.gamba.mocks.utils.Seq;

class MockProxyLogic implements IMockable {

	private final CallDefinitions callDefinitions;
	private final CallingLog callingLog;
	private boolean proxyIsRecording;

	public MockProxyLogic() {
		callDefinitions = new CallDefinitions();
		callingLog = new CallingLog();
		proxyIsRecording = true;
	}

	public Object invoke(final Method method, final Object... args) throws Throwable {

		if (proxyIsRecording) {
			/*
			 * in recording state
			 */
			return recording(method, args);
		}
		/*
		 * in playing state
		 */
		return playing(method, args);
	}

	private Object playing(final Method method, final Object[] args) throws Throwable {

		for (final CallActionConfig ce : callDefinitions.getCallsConfig()) {

			if (ce.getMethod().equals(method)) {

				boolean argsOK = true;
				for (int i = 0; i < ce.getCallingArgsValues().length; i++) {

					if (!args[i].equals(ce.getCallingArgsValues()[i]) && !ce.getIsAnied()[i]) {
						argsOK = false;
						break;
					}
				}

				if (argsOK) {
					final Object returnValue = ce.getDelegator().getNext(args);
					callingLog.add(new CallLogEntry(method, args, returnValue));
					return returnValue;
				}
			}
		}

		throw new GambaMockException("method call not defined: " + method.getName() + "("
				+ (args != null ? Seq.enList(args).toString() : "") + ")");
	}

	public String obtainCallConfig() {
		final StringBuffer strb = new StringBuffer();

		for (final CallActionConfig cac : callDefinitions.getCallsConfig()) {
			strb.append(cac.toString());
			strb.append('\n');
		}

		return strb.toString();
	}

	public CallingLog obtainCallingLog() {
		return callingLog;
	}

	/**
	 * Un objecte delegat s'ha definit, i en aquest punt la crida s'hi associa,
	 * completant la definició d'un mètode simulat de l'Stub. Retorna un tipus
	 * vàlid corresponent a la crida definida a l'interfície que s'està
	 * simulant.
	 *
	 * @param method
	 * @param args
	 * @return
	 */
	private Object recording(final Method method, final Object[] args) {

		// 2n pas: no s'ha cridat a cap mètode de IStubable, per tant és una
		// crida a la interfície proxejada
		// i s'ha de guardar la crida
		callDefinitions.getLast().setCall(method, args);

		return computeRecordingReturn(method);
	}

	/**
	 * assigna un objecte delegat a la propera crida de mock que es defineixi.
	 *
	 * @see org.gamba.mocks.IMockable#setSequence(org.gamba.mocks.sequences.ISequence,
	 *      org.gamba.mocks.fluent.Mask)
	 */
	public void setSequence(final ISequence sequence, final Mask mask) {
		callDefinitions.add(new CallActionConfig(sequence, mask.getMask()));
	}

	/**
	 * situa el mock en estat de reproducció, verifica la coherència de les
	 * crides definides, i reinicialitza l'estat dels delegats.
	 *
	 * @see org.gamba.mocks.IMockable#stopRecording()
	 */
	public void stopRecording() {
		proxyIsRecording = false;
		callDefinitions.checkRegisteredCalls();
		for (final CallActionConfig cac : callDefinitions.getCallsConfig()) {
			cac.getDelegator().reset();
		}
	}

	/**
	 * donat un mètode, calcula un tipus vàlid a retornar.<br>
	 * si el retorn és primitiu, retorna un tipus vàlid, altrament retorna
	 * <tt>null</tt>.
	 *
	 * @param method
	 * @return
	 */
	private Object computeRecordingReturn(final Method method) {
		final Class<?> type = method.getReturnType();

		if (type.isPrimitive()) {
			if (type.equals(int.class)) {
				return Integer.valueOf(0);
			}
			if (type.equals(long.class)) {
				return Long.valueOf(0);
			}
			if (type.equals(float.class)) {
				return Float.valueOf(0);
			}
			if (type.equals(double.class)) {
				return Double.valueOf(0);
			}
			if (type.equals(boolean.class)) {
				return Boolean.FALSE;
			}
			if (type.equals(char.class)) {
				return Character.valueOf(' ');
			}
		}
		return null;
	}

	public boolean isProxyIsRecording() {
		return proxyIsRecording;
	}

	public void verify() {
		for (final CallActionConfig cac : callDefinitions.getCallsConfig()) {
			if (!cac.getDelegator().testIsFinished()) {
				throw new GambaMockException("unsatisfied expectation: " + cac.getDelegator().toString());
			}
		}
	}

}
