package org.gamba.mocks.playingproxy;


/**
 * defineix mètodes addicionals al proxy de qualsevol mock, dedicats a la
 * configuració del mateix.
 *
 * @author mhoms
 */
public interface IPlayingProxyControl {
	// thenReturn(1,2,3).when(p).mul(anyInt());

	void verify();
	String obtainCallConfig();
//	CallingLog obtainCallingLog();



}
