package com.example.webapi.common;

/**
 * This class shows that we can be explicit with the definition of object
 * properties and they will still be serialized correctly. That is, we can
 * define getters and setters for the properties we want the calling client to
 * "see" and the Restlet ConverterService will do the right thing.
 */
public class Parrot {
  private String speech;

  public Parrot() {
    this.setSpeech("<mute>");
  }

  public void setSpeech(String speech) {
    this.speech = speech;
  }

  public String getSpeech() {
    return this.speech;
  }

  public String toString() {
    return this.getSpeech();
  }
}