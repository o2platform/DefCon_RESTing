package ch01.soap;

import java.util.Date;
import java.util.Iterator;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.Node;
import javax.xml.soap.Name;

public class DemoSoap {
    private static final String LocalName = "TimeRequest";
    private static final String Namespace = "http://ch01/mysoap/";
    private static final String NamespacePrefix = "ms";

    private ByteArrayOutputStream out;
    private ByteArrayInputStream in;

    public static void main(String[ ] args) {
       new DemoSoap().request();
    }

    private void request() {
       try {
         // Build a SOAP message to send to an output stream.
         SOAPMessage msg = create_soap_message();
   
         // Inject the appropriate information into the message. 
         // In this case, only the (optional) message header is used
         // and the body is empty.
         SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
         SOAPHeader hdr = env.getHeader();
  
         // Add an element to the SOAP header. 
         Name lookup_name = create_qname(msg);
         hdr.addHeaderElement(lookup_name).addTextNode("time_request");

         // Simulate sending the SOAP message to a remote system by
         // writing it to a ByteArrayOutputStream.
         out = new ByteArrayOutputStream();
         msg.writeTo(out);

         trace("The sent SOAP message:", msg);

         SOAPMessage response = process_request();
         extract_contents_and_print(response);
       }
       catch(SOAPException e) { System.err.println(e); }
       catch(IOException e) { System.err.println(e); }
    }

    private SOAPMessage process_request() {
       process_incoming_soap();
       coordinate_streams();
       return create_soap_message(in);
    }
       
    private void process_incoming_soap() {
       try {
         // Copy output stream to input stream to simulate
         // coordinated streams over a network connection.
         coordinate_streams();

         // Create the "received" SOAP message from the
         // input stream.
         SOAPMessage msg = create_soap_message(in);

         // Inspect the SOAP header for the keyword 'time_request' 
         // and process the request if the keyword occurs.
         Name lookup_name = create_qname(msg);

         SOAPHeader header = msg.getSOAPHeader();
         Iterator it = header.getChildElements(lookup_name);
         Node next = (Node) it.next();
         String value = (next == null) ? "Error!" : next.getValue();

         // If SOAP message contains request for the time, create a
         // new SOAP message with the current time in the body.
         if (value.toLowerCase().contains("time_request")) {

           // Extract the body and add the current time as an element.
           String now = new Date().toString();
           SOAPBody body = msg.getSOAPBody();
           body.addBodyElement(lookup_name).addTextNode(now);
           msg.saveChanges();

           // Write to the output stream.
           msg.writeTo(out);
           trace("The received/processed SOAP message:", msg);
         }
       }
       catch(SOAPException e) { System.err.println(e); }
       catch(IOException e) { System.err.println(e); }
    }
    
    private void extract_contents_and_print(SOAPMessage msg) {
       try {
         SOAPBody body = msg.getSOAPBody();

         Name lookup_name = create_qname(msg);
         Iterator it = body.getChildElements(lookup_name);
         Node next = (Node) it.next();
    
         String value = (next == null) ? "Error!" : next.getValue();
         System.out.println("\n\nReturned from server: " + value);
       }
       catch(SOAPException e) { System.err.println(e); }
    }

    private SOAPMessage create_soap_message() {
       SOAPMessage msg = null;
       try {
         MessageFactory mf = MessageFactory.newInstance();
         msg = mf.createMessage();
       }
       catch(SOAPException e) { System.err.println(e); }
       return msg;
    }

    private SOAPMessage create_soap_message(InputStream in) {
       SOAPMessage msg = null;
       try {
         MessageFactory mf = MessageFactory.newInstance();
         msg = mf.createMessage(null, // ignore MIME headers
                                in);  // stream source
       }
       catch(SOAPException e) { System.err.println(e); }
       catch(IOException e) { System.err.println(e); }
       return msg;
    }

    private Name create_qname(SOAPMessage msg) {
       Name name = null;
       try {
         SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
         name = env.createName(LocalName, 
         NamespacePrefix, 
         Namespace);
       }
       catch(SOAPException e) { System.err.println(e); }
       return name;
    }

    private void trace(String s, SOAPMessage m) {
       System.out.println("\n");
       System.out.println(s);
       try {
         m.writeTo(System.out);
       }
       catch(SOAPException e) { System.err.println(e); }
       catch(IOException e) { System.err.println(e); }
    }

    private void coordinate_streams() {
       in = new ByteArrayInputStream(out.toByteArray());
       out.reset();
    }
}   
