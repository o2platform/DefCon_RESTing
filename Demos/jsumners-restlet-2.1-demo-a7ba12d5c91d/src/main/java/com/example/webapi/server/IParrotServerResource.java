package com.example.webapi.server;

import org.restlet.resource.Get;
import org.restlet.resource.Put;

import com.example.webapi.common.Parrot;

/**
 * By defining and interface we accomplish two things:
 *
 * 1. We define what sort of REST method each object method is without
 * having to do so in the implementation.
 * 2. We explicitly define the structure of the API.
 *
 * With this API we show what is possible with both GET and PUT methods via the
 * Restlet API.
 */
public interface IParrotServerResource {
  //
  // When the client requests the "/secured/{apikey}/parrot" and
  // "/secured/{apikey}/parrot/{says}" API paths with at GET request, this is
  // the method that will be invoked.
  //
  @Get
  public Parrot parrot();

  //
  // When the client requests the "/secured/{apikey}/parrot" API path with a PUT
  // request, this is the method will be called. Notice that this path can also
  // be served with a GET request that will be processed by the `parrot()`
  // method.
  //
  @Put
  public void say(String words);
}