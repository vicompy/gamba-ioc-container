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
package org.berbis.gretl.exceptions;

/**
 * An exception type for <tt>Gamba Collections</tt> error handling
 *
 * @author mhoms
 * @since Ago-2009
 */
public class BerbisException extends RuntimeException {

	private static final long serialVersionUID = -834166947439734340L;

	public BerbisException(final String msg) {
		super("\n" + msg);
	}

	public BerbisException(final String msg, final Exception exc) {
		super("\n" + msg, exc);
	}

}
