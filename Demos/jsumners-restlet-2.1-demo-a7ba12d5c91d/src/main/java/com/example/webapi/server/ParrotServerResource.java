package com.example.webapi.server;

import org.json.JSONException;
import org.json.JSONObject;

import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.ServerResource;

import com.example.webapi.common.Parrot;

/**
 * This class defines the "parrot" portion of our "secured" API. It handles
 * both GET and PUT requests. See the documentation in
 * IParrotServerResource.java for more details than are outlined here.
 */
public class ParrotServerResource
  extends ServerResource
  implements IParrotServerResource
{
  private Parrot parrot;

  //
  // The `doInit()` method is invoked after the Restlet API has instantiated an
  // instance of any Resource class. We can use this method to setup our object
  // if we need to access data defined by the Restlet API. For example, if we
  // want to access a URI variable via `this.getAttribute()` we would need to
  // use the `doInit()` method. Restlet attributes are not available in a
  // normal object constructor.
  //
  @Override
  public void doInit() {
    this.parrot = new Parrot();
  }

  //
  // A simple GET method.
  //
  @Override
  public Parrot parrot() {
    String words = (String)this.getAttribute("says");

    if (words != null) {
      this.parrot.setSpeech(words);
    }

    return parrot;
  }

  //
  // This is the method that handles PUT requests. Notice that we are able to
  // modify the response that will be sent back to the client. In particular,
  // notice that we defined the response body by setting the response "entity"
  // to a Restlet Representation object. In this case, we are using a
  // JsonRepresentation.
  //
  @Override
  public void say(String words) {
    this.parrot.setSpeech(words);

    JsonRepresentation jsonRepresentation = null;
    JSONObject jsonObject = new JSONObject();

    try {
      jsonObject.put("Parrot", this.parrot);
      jsonRepresentation = new JsonRepresentation(jsonObject);
    } catch(JSONException jsonEx) {
      System.out.println(jsonEx.toString());
    }

    Response response = this.getResponse();
    response.setStatus(Status.SUCCESS_CREATED);
    response.setEntity(jsonRepresentation);
  }
}