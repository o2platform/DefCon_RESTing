package AlvaroPoc;

import org.restlet.*;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Protocol;  
import org.restlet.routing.Router;
import org.restlet.routing.TemplateRoute;
import org.restlet.routing.Variable;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;


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

    @Override
    public Restlet createInboundRoot() {
        // Create a simple password verifier
        MapVerifier verifier = new MapVerifier();
        verifier.getLocalSecrets().put("scott", "tiger".toCharArray());

        // Create a Guard
        ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext(), ChallengeScheme.HTTP_BASIC, "Tutorial");
        guard.setVerifier(verifier);
               
        final Router router = new Router(getContext()); 
        router.attach("/customer", CustomerRessource.class);

        guard.setNext(router);
        return guard;
    }

}