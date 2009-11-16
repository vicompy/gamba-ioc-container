package cat.base.baseframe.actions;

import static org.gamba.mocks.fluent.Mocker.*;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.RequestContext;



/**
 * classe de test que verifica el bon comportament de {@link AbstractHandler}. Aquest test
 * utilitza <i>objectes mock</i> de la API <tt>EasyMock</tt>.
 *
 * @author mhoms
 */
public class AbstractHandlerTest {

	/**
	 * la instància demanada no està en el flowScope, així doncs es crea, es
	 * desa en flowScope, i es retorna.
	 */
	@Test
	public void test1() {
		// crea els mocks
//		final MutableAttributeMap mockedFlowScope = createMock(MutableAttributeMap.class);
//		final RequestContext mockedRequestContext = createMock(RequestContext.class);

		final MutableAttributeMap mockedFlowScope = createMock(MutableAttributeMap.class);
		final RequestContext mockedRequestContext = createMock(RequestContext.class);


		// en defineix el comportament
//		expect(mockedRequestContext.getFlowScope()).andReturn(mockedFlowScope).times(2);
//		expect(mockedFlowScope.get("exempleBB")).andReturn(null);
//		expect(mockedFlowScope.put((String) anyObject(), anyObject())).andReturn(null);

		thenReturn(mockedFlowScope, 2).when(mockedRequestContext).getFlowScope();
		thenReturn(((Object) null)).when(mockedFlowScope).get("exempleBB");
		thenReturn(((Object) null)).when(mockedFlowScope, maskBy("**")).put(null, null);

//		// prepara per a simulació
//		replay(mockedRequestContext);
//		replay(mockedFlowScope);

		replay(mockedRequestContext);
		replay(mockedFlowScope);

		// executa la simulació
		final ExempleHandler exempleHandler = new ExempleHandler();
		final ExempleBB exempleBB = (ExempleBB) exempleHandler.loadBackBean(
			mockedRequestContext, "exempleBB", ExempleBB.class
		);
		Assert.assertTrue(exempleBB instanceof ExempleBB);

		// verifica el comportament
//		verify(mockedRequestContext);
//		verify(mockedFlowScope);

		verify(mockedRequestContext);
		verify(mockedFlowScope);

	}

	/**
	 * la instància demanada ja es troba en flowScope, i es retorna tal qual.
	 */
	@Test
	public void test2() {
		// crea els mocks
//		final MutableAttributeMap mockedFlowScope = createMock(MutableAttributeMap.class);
//		final RequestContext mockedRequestContext = createMock(RequestContext.class);

		final MutableAttributeMap mockedFlowScope = createMock(MutableAttributeMap.class);
		final RequestContext mockedRequestContext = createMock(RequestContext.class);

		// en defineix el comportament
//		expect(mockedRequestContext.getFlowScope()).andReturn(mockedFlowScope).times(2);
//		expect(mockedFlowScope.get("exempleBB")).andReturn(new ExempleBB()).times(2);

		thenReturn(mockedFlowScope, 2).when(mockedRequestContext).getFlowScope();
		thenReturn(new ExempleBB(), 2).when(mockedFlowScope).get("exempleBB");

		// prepara per a simulació
		replay(mockedRequestContext);
		replay(mockedFlowScope);

		// executa la simulació
		final ExempleHandler exempleHandler = new ExempleHandler();
		final ExempleBB exempleBB = (ExempleBB) exempleHandler.loadBackBean(
			mockedRequestContext, "exempleBB", ExempleBB.class
		);
		Assert.assertTrue(exempleBB instanceof ExempleBB);

		// verifica el comportament
		verify(mockedRequestContext);
		verify(mockedFlowScope);
	}

}
