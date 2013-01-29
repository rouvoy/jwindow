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
import java.util.concurrent.TimeUnit;

import fr.inria.jwindow.Window;

public class TimeWindow<T> extends AbstractWindow<T> implements Window<T> {
	private final Queue<T> elements = new SynchronousQueue<T>();
	private final Queue<Long> timestamps = new SynchronousQueue<Long>();
	private final long ttl;

	public TimeWindow(long ttl, TimeUnit unit) {
		this.ttl = unit.toMillis(ttl);
	}

	public void insert(T elt) {
		Collection<T> view;
		synchronized (elements) {
			long timestamp = System.currentTimeMillis();
			while ((timestamp - timestamps.peek()) > ttl) {
				this.timestamps.poll();
				this.elements.poll();
			}
			this.timestamps.offer(timestamp);
			this.elements.offer(elt);
			view = Collections.unmodifiableCollection(this.elements);
		}
		notify(view);
	}

	public synchronized void clear() {
		synchronized (elements) {
			this.timestamps.clear();
			this.elements.clear();
		}
	}
}
