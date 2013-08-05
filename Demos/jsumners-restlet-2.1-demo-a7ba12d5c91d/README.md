# A Restlet 2.1 Servlet Example #

This repository demonstrates how to write a [RESTful web API](https://en.wikipedia.org/wiki/Representational_state_transfer#RESTful_web_services) using the [Restlet](http://restlet.org/) API. The API defined in this example includes both public and private paths.

This demonstration is only a starting point. It only outputs [JSON](http://json.org) on successful requests. Requests that result in an error return HTML with an appropriate status code. Overriding the error responses with custom representations is not shown.

**Note:** the "tutorial" is primarily in the comments of the code. This README only gives a brief overview to provide a sort of roadmap to the source code.

Don't be scared off by the line numbers mentioned in this overview. Most of the code in this tutorial is comments.

## API Introduction ##

The Restlet API revolves around a few basic objects:

1. `Router` objects to determine API paths
2. `Resource` objects to define API resources
3. `Representation` objects to define API responses and (incoming) messages
4. An `Application` object to manage it all

Thus, the starting point of a Restlet based API is defining an `Application` object.

## The `Application` Object ##

The Restlet [Appliation](http://restlet.org/learn/javadocs/2.1/jee/api/org/restlet/Application.html) class defines the controlling object of your API. It manages all incoming [requests](http://restlet.org/learn/javadocs/2.1/jee/api/org/restlet/Request.html) and outgoing [responses](http://restlet.org/learn/javadocs/2.1/jee/api/org/restlet/Response.html), and determines how they should be handled.

Therefore, the primary object in this tutorial is the [com.example.webapi.ServerApplication](https://bitbucket.org/jsumners/restlet-2.1-demo/src/tip/src/main/java/com/example/webapi/ServerApplication.java) object. This object does three things:

1. Creates a primary [Router](http://restlet.org/learn/javadocs/2.1/jee/api/org/restlet/routing/Router.html) object to handle all incoming requests
2. Creates a [Filter](http://restlet.org/learn/javadocs/2.1/jee/api/org/restlet/routing/Filter.html) to validate requests to the "secure" portion of the API
3. Creates a secondary Router to handle the secure API requests

## The `Router` Object ##

The [Router](http://restlet.org/learn/javadocs/2.1/jee/api/org/restlet/routing/Router.html) class processes requests and maps them to resources attached to the router. A resource can be a [Restlet](http://restlet.org/learn/javadocs/2.1/jee/api/org/restlet/Restlet.html) or a [ServerResource](http://restlet.org/learn/javadocs/2.1/jee/api/org/restlet/resource/ServerResource.html).

On [line 33](https://bitbucket.org/jsumners/restlet-2.1-demo/src/87e49b0e0a5abae59c089421740f5a3157c934a8/src/main/java/com/example/webapi/ServerApplication.java?at=default#cl-33) of our `ServerApplication` we attach a `ServerResource` to the primary router of our application.

On [line 60](https://bitbucket.org/jsumners/restlet-2.1-demo/src/87e49b0e0a5abae59c089421740f5a3157c934a8/src/main/java/com/example/webapi/ServerApplication.java?at=default#cl-60) of our `ServerApplication` we attach a `Restlet`.

Notice that on [line 53](https://bitbucket.org/jsumners/restlet-2.1-demo/src/87e49b0e0a5abae59c089421740f5a3157c934a8/src/main/java/com/example/webapi/ServerApplication.java?at=default#cl-53) of our `ServerApplication` we define a path with a word surrounded by curly brackets -- `"/parrot/{says}"`. This defines a token that will be parsed by the router and added to the attributes map of the attached `ServerResource`. This allows us to define variables in the URI that can be used by the methods that comprise our API.

## The `ServerResource` Object ##

A [ServerResource](http://restlet.org/learn/javadocs/2.1/jee/api/org/restlet/resource/ServerResource.html) is an object that will be instantiated when an incoming request matches a route that is attached to it. This object can match any type of incoming request: `GET`, `PUT`, `DELETE`, `OPTIONS`, `HEAD`, or `POST`.

You can either override the named method (e.g. `protected Representation get()`) or use an annotation to define the handler for the incoming method. The former requires also overriding the `getVariants()` method, and is more complicated. Instead, we use the second option and use annotations like on [line 27](https://bitbucket.org/jsumners/restlet-2.1-demo/src/87e49b0e0a5a/src/main/java/com/example/webapi/server/FooServerResource.java?at=default#cl-27) of `com.example.webapi.server.FooServerResource`.

Notice that our `FooServerResource` returns a POJO (Plain Old Java Object) instead of something more exotic like a [Representation](http://restlet.org/learn/javadocs/2.1/jee/api/org/restlet/representation/Representation.html). By returning a POJO, we are taking advantage of a powerful feature of the Restlet API -- the [ConverterService](http://restlet.org/learn/javadocs/2.1/jee/api/org/restlet/service/ConverterService.html).

### The `ConverterService` (Aside) ###

The `Application` object has several services attached to it. One of those services is the `ConverterService`. Whenever part of the Restlet API is expecting a `Representation` but receives something else instead, like a POJO, it tries to use the `ConverterService` to convert it to the necessary object/representation.

The `ConverterService` doesn't just guess at how it should be converting. The annotations and object types in your program tell it what to do.

In our demonstration, we specify that our output should be converted to JSON via the [org.restlet.ext.jackson](http://restlet.org/learn/javadocs/2.1/jee/ext/org/restlet/ext/jackson/package-frame.html) extension.

## Running The Application ##

As mentioned earlier, the `Application` object is the central object of our web application. As a result, we only need to tell our [servlet container](https://en.wikipedia.org/wiki/Web_container) what class to instantiate in order to load our application. This is done in the servlet definition starting on [line 14](https://bitbucket.org/jsumners/restlet-2.1-demo/src/87e49b0e0a5a/src/main/webapp/WEB-INF/web.xml?at=default#cl-14) of our [web.xml](https://bitbucket.org/jsumners/restlet-2.1-demo/src/tip/src/main/webapp/WEB-INF/web.xml).

That's all there is to it. Also, you should notice that we only wrote 32 lines (including comments) of XML in the whole application. And that's just the web.xml file! Okay, so, there is the [Maven](http://maven.apache.org/) [pom.xml](https://bitbucket.org/jsumners/restlet-2.1-demo/src/tip/pom.xml), but that isn't part of the application (just the project).

## The URLs Available ##

The URLs available in the demonstration application are as follows:

1. `/foo`
2. `/secured/{apikey}/parrot`
3. `/secured/{apikey}/parrot/{says}`

The available API keys are: "`abc123`" and "`foobar`".

You can issue a `GET` or `PUT` request to (2). If you issue a `PUT` request, the body of the request should contain the words you want the parrot to "say".

## Conclusion ##

The Restlet API is very simple, but very powerful. It's also very well designed, unlike most Java APIs. It does suffer the common Java problem of severly lacking documenation (thus this tutorial). But once you get a grasp on the concepts of the API, it really isn't that hard to figure out with the documentation that is available.

The [Restlet In Action](http://www.manning.com/louvel/) book is what the web documentation should be. It covers many more topics than this tutorial, and it does so in a consice, easy to read, manner. So, if you want more detail on this API, you should pick up a copy of the book.

Finally, if you think there is any portion of this tutorial that could use improvement, please consider making the improvement and contributing it. The appropriate method for doing so would be to [fork the project](https://bitbucket.org/jsumners/restlet-2.1-demo/fork) and then submit a [pull request](https://bitbucket.org/jsumners/restlet-2.1-demo/pull-request/new) with your changes.