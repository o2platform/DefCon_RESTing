package com.testServlet;

import org.restlet.*;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;

public class TestServletApplication extends Application {

    public static void main(String[] args) throws Exception {
        // Create a component
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8182);
        component.getClients().add(Protocol.FILE);

        TestServletApplication application = new TestServletApplication(
                component.getContext());

        // Attach the application to the component and start it
        component.getDefaultHost().attach("", application);
        component.start();
    }

    public TestServletApplication() {
        super();
    }

    public TestServletApplication(Context context) {
        super(context);
    }

    @Override
    public Restlet createInboundRoot() {
        // Create a simple password verifier
        MapVerifier verifier = new MapVerifier();
        verifier.getLocalSecrets().put("scott", "tiger".toCharArray());

        // Create a Guard
        //ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext(), ChallengeScheme.HTTP_BASIC, "Tutorial");
        //guard.setVerifier(verifier);

        final Router router = new Router(getContext());
        router.attach("/customer", CustomerRessource.class);
        return router;
        //guard.setNext(router);
        //return guard;
    }
}
