package com.testServlet;

import org.restlet.*;
import org.restlet.data.Protocol;


public class ServerRestlets extends Application{


    public static void main(String[] args) throws Exception {
        // Create a component
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8182); 

        // Create an application
        Application application = new ServerRestlets();

        // Attach the application to the component and start it
        component.getDefaultHost().attachDefault(application);
        component.start();
    }



}