/*
 *  Copyright 2009 m. homs
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.homs.gamba.container.exception;

import org.homs.gamba.container.ents.BeanDef;

/**
 * An exception type for <tt>Gamba IoC Container</tt>
 *
 * @author mhoms
 * @since Ago-2009
 */
public class GambaException extends RuntimeException {

	private static final long serialVersionUID = -7710568077775524520L;

	public GambaException(final String message, final BeanDef beanDefinition) {
		super("\n" + message + "\n en definició: " + beanDefinition.toString());
	}

	public GambaException(final String message, final BeanDef beanDefinition, final Exception nestedException) {
		super("\n" + message + "\n en definició: " + beanDefinition.toString(), nestedException);
	}

}
