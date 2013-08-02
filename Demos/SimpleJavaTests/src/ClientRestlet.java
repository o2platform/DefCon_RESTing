import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Status;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.data.MediaType;

public class ClientRestlet { 
	public static void main(String[] args) throws Exception {
		// Prepare the request
		ClientResource resource = new ClientResource( "http://localhost.:8182/customer");

		// Add the client authentication to the call
		ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
		ChallengeResponse authentication = new ChallengeResponse(scheme, "scott", "tiger");
		resource.setChallengeResponse(authentication);

		// Send the HTTP GET request
		resource.get(MediaType.APPLICATION_JAVA_OBJECT_XML);
		if (checkStatus(resource)) {
			ObjectRepresentation<Customer> repObject = new ObjectRepresentation<Customer>(resource.getResponseEntity());
			Customer customer = repObject.getObject();
			System.out.println("name .. : " + customer.name);
			System.out.println("firstname : " + customer.firstName);

			// Send a modified version of the object to the server
			customer.name = "Doe";
			customer.firstName = "John";
			resource.put(repObject, MediaType.APPLICATION_JAVA_OBJECT_XML);
			checkStatus(resource);
		}
	}

	/**
	 * @param resource
	 */
	protected static boolean checkStatus(ClientResource resource) {
		if (resource.getStatus().isSuccess()) {
			System.out.println("OK client side");
			return true;}
		if (resource.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED)) {
			// Unauthorized access
			System.out.println("Access authorized by the server, check your credentials");
		} else {
			// Unexpected status
			System.out.println("An unexpected status was returned: " + resource.getStatus());
		}
		return false;
	}

}