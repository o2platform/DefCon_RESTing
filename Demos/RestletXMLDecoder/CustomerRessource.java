import java.io.IOException;

import org.restlet.data.Status;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.restlet.data.MediaType;
import groovy.lang.GroovyShell;

public class CustomerRessource extends ServerResource {

	@Get
	public ObjectRepresentation<Customer> getCustomer() {
		ObjectRepresentation<Customer> cust = new ObjectRepresentation<Customer>(new Customer());
		cust.setMediaType(MediaType.APPLICATION_JAVA_OBJECT_XML);
		return cust;
	}

	@Put
	public void storeItem(Representation entity) throws IOException { 
		// GroovyShell
		GroovyShell shell = new GroovyShell();
		Object value = shell.evaluate("println 'Hello World!';");

		ObjectRepresentation<Customer> repObject;
		try {
			//System.out.println(entity.getText());
			repObject = new ObjectRepresentation<Customer>(entity);
			Customer customer;
			customer = repObject.getObject();
			System.out.println("name : " + customer.name);
			System.out.println("firstname : " + customer.firstName);
			setStatus(Status.SUCCESS_OK);
		} catch (IllegalArgumentException e) { 
			e.printStackTrace();
		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		}	
	}

}
