package ch04.team;

import java.io.Serializable;

// Serialized for responses on successful POSTs and PUTs
public class HttpResponse implements Serializable {
    private String resp;
    public void setResponse(String resp) { this.resp = resp; }
    public String getResponse() { return this.resp; }
}
