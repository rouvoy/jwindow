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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import fr.inria.jwindow.Window;
import fr.inria.jwindow.WindowListener;

public abstract class AbstractWindow<T> implements Window<T> {

	private final Set<WindowListener<T>> listeners;

	public AbstractWindow() {
		listeners = new HashSet<WindowListener<T>>();
	}

	protected Thread notify(final Collection<T> window) {
		Thread t = new Thread() {
			public void run() {
				for (WindowListener<T> listener : listeners)
					listener.onWindowChanged(window);
			}
		};
		t.start();
		return t;
	}

	public void addListener(WindowListener<T> evt) {
		listeners.add(evt);
	}
}