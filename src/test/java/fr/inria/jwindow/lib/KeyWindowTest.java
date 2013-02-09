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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.mockito.Mockito;

import fr.inria.jwindow.Window;
import fr.inria.jwindow.WindowListener;

public class KeyWindowTest extends TestCase {
	private WindowListener<Date> listener;
	
	private final Key<Date,Date> key = new Key<Date,Date>() {
		public Date getKey(Date t2) {
			return t2;
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.listener = (WindowListener<Date>) mock(WindowListener.class);
	}

	public void testInsertNullWindow() {
		Window<Date> window = new KeyWindow<Date, Date>(key);
		window.addListener(this.listener);
		Assert.assertNull(window.insert(null));
		verify(this.listener, never()).onWindowChanged(Mockito.anyCollectionOf(Date.class));
	}

	public void testInsertSingletonWindow() throws InterruptedException {
		Window<Date> window = new KeyWindow<Date, Date>(key);
		window.addListener(this.listener);
		window.insert(new Date()).join();
		verify(this.listener, times(1)).onWindowChanged(Mockito.anyCollectionOf(Date.class));
	}

	public void testRandomInsertWindow() throws InterruptedException {
		Window<Date> window = new KeyWindow<Date, Date>(key);
		window.addListener(this.listener);
		for (int i = 0; i < 10; i++)
			window.insert(new Date()).join();
		verify(this.listener, times(10)).onWindowChanged(Mockito.anyCollectionOf(Date.class));
	}
}
