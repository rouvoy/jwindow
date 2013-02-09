# Java Window Library

This is a Java library that implements window-based data structures.


## Maven artefact

### Release
The latest released version of the JWindow library artefact is available as:

``` xml
<dependency>
    <groupId>fr.inria.jwindow</groupId>
    <artifactId>jwindow-library</artifactId>
    <version>1.0</version>
</dependency>
```

### Snapshot
The currently developed version of the JWindow library artefact is available as:

``` xml
<dependency>
    <groupId>fr.inria.jfilter</groupId>
    <artifactId>jfilter-library</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Maven compilation

JWindow is a [Maven](http://maven.apache.org "Maven") managed project. All you have to do is to invoke the `install` command from the root directory (`JWINDOW_DIR`):

``` bash
cd $JWINDOW_DIR
mvn install
```



## API usage

``` java
public class Person {
	final String firstname, lastname;

	public Person(String first, String last) {
		this.firstname = first;
		this.lastname = last;
	}

	public static void main(String[] args) {
		WindowListener count = new WindowListener<Person>() {
			void onWindowChanged(Collection<T> view) {
				System.out.println("Current view contains "+view.size()+" element(s)");
			}
		}

		Window<Person> window = new TimeWindow(5,TimeUnit.SECONDS);
		// Window<Person> window = new SizeWindow(10);

		window.addListener(count);

		window.insert(new Person("John", "Doe")); // asynchronous notification
		window.insert(new Person("Bob", "Smith")).join(); // synchronous notification

		window.clear();
	}
}

## Licence

    Copyright (C) 2013 University Lille 1, Inria

    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Library General Public License as published
    by the Free Software Foundation; either version 2 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Library General Public
    License for more details.

    You should have received a copy of the GNU Library General Public License
    along with this library; if not, write to the Free Software Foundation,
    Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301, USA.