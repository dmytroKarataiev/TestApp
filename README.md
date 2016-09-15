# REST client for the MoviesDB
![Animation](materials/animation.gif)
A client for a popular Movies Database with MVP architecture and Databinding.

## Requirements
* 2 activities: list with a RecyclerView and a Detail activity.
* Any JSON input from the internet.
* RecyclerView with items, which have images and text.
* Delete an element from the RecyclerView on swipe.
* Elements change color on click.
* SharedTransition from a list to details.
* A Service which starts each 2 minutes and shows an AlertDialog with current time (Current time - HH:mm) and anu imagefrom the internet.
* Databinding for a list of elements.
* Portrait and landscape modes.

## Features
* MVP architecture.

## Libraries
* [Retrofit](http://square.github.io/retrofit/) - a library for all REST-related work.
* [RxJava](https://github.com/ReactiveX/RxAndroid) - the most modern way to manage streams of data and asynctasks.
* [Picasso](http://square.github.io/picasso/) - one of the best image downloading and caching libraries for Android.

License
-------

	The MIT License (MIT)

	Copyright (c) 2016 Dmytro Karataiev

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.