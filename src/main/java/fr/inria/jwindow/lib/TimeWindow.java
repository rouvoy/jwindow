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

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class TimeWindow<T> extends AbstractWindow<T> {
	private final Queue<T> elements = new LinkedList<T>();
	private final Queue<Long> timestamps = new LinkedList<Long>();
	private final long ttl;

	public TimeWindow(long ttl, TimeUnit unit) {
		this.ttl = unit.toMillis(ttl);
	}

	public synchronized Thread insert(T elt) {
		if (elt == null)
			return null;
		final long timestamp = System.currentTimeMillis();
		while (!this.timestamps.isEmpty() && (timestamp - this.timestamps.peek()) > this.ttl) {
			this.timestamps.poll();
			this.elements.poll();
		}
		this.timestamps.offer(timestamp);
		this.elements.offer(elt);
		return notify(Collections.unmodifiableCollection(this.elements));
	}

	public synchronized void clear() {
		this.timestamps.clear();
		this.elements.clear();
	}
}
