package com.example.webapi.server;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.example.webapi.common.Foo;

/**
 * This class defines the object that will handle requests to our "public" API
 * path "/foo". There are two things to take note of here:
 *
 * 1. We are able to designate what sort of REST request method a method of
 * this object will be used for and what sort of response body it will
 * generate.
 * 2. We can return plain old Java objects as the "response".
 */
public class FooServerResource extends ServerResource {
  //
  // The annotation tells the Restlet API that the `json()` method (it could be
  // named anything) will handle incoming GET requests. It further tells the
  // Restlet API to generate an 'application/json' response body (or, in the
  // parlance of the Restlet API, a JsonRepresentation).
  //
  // Thus, the `Foo` object that is returned from the `json()` method will be
  // processed by the Restlet API and serialized into JSON. This JSON will then
  // be sent on to the client that requested "/foo" from our web API.
  @Get("json")
  public Foo json() {
    return new Foo();
  }
}