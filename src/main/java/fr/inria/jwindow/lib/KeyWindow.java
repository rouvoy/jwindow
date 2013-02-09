/**
 * Copyright (C) 2013 University Lille 1, Inria
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 *
 * Contact: romain.rouvoy@univ-lille1.fr
 */
package fr.inria.jwindow.lib;

import java.util.HashMap;
import java.util.Map;

public class KeyWindow<S,T> extends AbstractWindow<T> {
	private final Map<S,T> elements = new HashMap<S,T>();
	private final Key<S,T> key;

	public KeyWindow(Key<S,T> key) {
		this.key = key;
	}

	public synchronized Thread insert(T elt) {
		if (elt == null)
			return null;
		this.elements.put(key.getKey(elt),elt);
		return notify(this.elements.values());
	}

	public synchronized void clear() {
		this.elements.clear();
	}
}
