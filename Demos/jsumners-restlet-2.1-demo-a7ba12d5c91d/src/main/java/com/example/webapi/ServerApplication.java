package com.example.webapi;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.example.webapi.auth.FooAuthenticator;
import com.example.webapi.server.FooServerResource;
import com.example.webapi.server.ParrotServerResource;

/**
 * This is the main class that our servlet container will use to drive the
 * web application [API]. It gets instantiated only one time. It is within
 * this class that we define our API's routes.
 */
public class ServerApplication extends Application {
  /**
   * Restlet 2.1 defines the `createInboundRoot` method as the primary entry
   * point for servlet applications. When new requests are received they will
   * be processed through this method.
   */
  @Override
  public Restlet createInboundRoot() {
    System.setProperty("java.net.preferIPv4Stack", "true");

    //
    // Restlet uses Routers to determine how the incoming request should
    // be handled. We are defining a primary router, `mainRouter`, that will
    // handle our primary API routes (that is public and private). Requests
    // are processed in the order in which routes are attached to `mainRouter`.
    //
    Router mainRouter = new Router(this.getContext());
    mainRouter.attach("/foo", FooServerResource.class);

    //
    // The Restlet Authenticator class, of which `FooAuthenticator` is a
    // subclass, is a filter. When a request comes in that doesn't match our
    // public API method "/foo", Restlet will then pass the request on to the
    // next route attached to `mainRouter`. This next route will be through
    // the `FooAuthenticator` filter.
    //
    FooAuthenticator authenticator = new FooAuthenticator(this.getContext());

    //
    // If an incoming request goes through the `FooAuthenticator` filter, and
    // is determined to be allowed through that filter, then the filter will
    // pass the request on to whatever router (or another filter possibly) is
    // designated as the "next Restlet". Our designated next Restlet will be
    // `securedRouter`.
    //
    Router securedRouter = new Router(this.getContext());
    securedRouter.attach("/parrot", ParrotServerResource.class);
    securedRouter.attach("/parrot/{says}", ParrotServerResource.class);
    authenticator.setNext(securedRouter);

    //
    // Now that we have configured the filter and the router it will pass on to,
    // we can attach the filter to the path we wish to secure.
    //
    mainRouter.attach("/secured/{apikey}", authenticator);

    //
    // Since the Router class is a subclass of the Restlet class, we can simply
    // return our `mainRouter` as the Restlet for this Application.
    //
    return mainRouter;
  }
}