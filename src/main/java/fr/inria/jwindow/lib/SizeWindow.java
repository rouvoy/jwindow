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
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

import fr.inria.jwindow.Window;

public class SizeWindow<T> extends AbstractWindow<T> implements Window<T> {
	private final Queue<T> elements = new SynchronousQueue<T>();
	private final long maxSize;
	private long currentSize;

	public SizeWindow(long size) {
		this.maxSize = size;
		this.currentSize = 0;
	}

	public void insert(T elt) {
		Collection<T> view;
		synchronized (elements) {
			while (this.currentSize >= this.maxSize) {
				this.elements.poll();
				this.currentSize--;
			}
			this.elements.offer(elt);
			view = Collections.unmodifiableCollection(this.elements);
		}
		notify(view);
	}

	public synchronized void clear() {
		this.elements.clear();
		this.currentSize = 0;
	}
}
