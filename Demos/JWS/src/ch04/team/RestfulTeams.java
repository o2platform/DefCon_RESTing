package ch04.team;

import javax.xml.ws.Provider;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.annotation.Resource;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPException;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.http.HTTPBinding;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.net.URI;
import java.net.URISyntaxException;
import org.w3c.dom.NodeList;

// A generic service provider rather than a SOAP-based service.
@WebServiceProvider

// Two ServiceModes: PAYLOAD, the default, signals that the service
// needs access to only the message payload, whereas MESSAGE signals
// that the service needs access to the entire message.
@ServiceMode(value = javax.xml.ws.Service.Mode.MESSAGE)

// Raw XML over HTTP
@BindingType(value = HTTPBinding.HTTP_BINDING)
public class RestfulTeams implements Provider<Source> {
    @Resource
    protected WebServiceContext ws_ctx; // dependency injection

    private Map<String, Team> team_map; // for easy lookups
    private List<Team> teams;           // serialized/deserialized
    private byte[ ] team_bytes;         // from the persistence file

    private static final String file_name = "teams.ser";
    private static final String put_post_key = "Cargo";

    public RestfulTeams() {
	read_teams_from_file();
	deserialize();
    }

    // Implementation of the Provider interface method: this
    // method handles incoming requests and generates the
    // outgoing response.
    public Source invoke(Source request) {
	if (ws_ctx == null)
	    throw new RuntimeException("Injection failed on ws_ctx.");

	// Grab the message context and extract the request verb.
	MessageContext msg_ctx = ws_ctx.getMessageContext();
	String http_verb = (String) 
	    msg_ctx.get(MessageContext.HTTP_REQUEST_METHOD);
	http_verb = http_verb.trim().toUpperCase();
	
	// Act on the verb.
	if      (http_verb.equals("GET"))    return doGet(msg_ctx);
	else if (http_verb.equals("POST"))   return doPost(msg_ctx);
	else if (http_verb.equals("PUT"))    return doPut(msg_ctx);
	else if (http_verb.equals("DELETE")) return doDelete(msg_ctx);
	else throw new HTTPException(405); // bad verb exception
    }
	
    private Source doGet(MessageContext msg_ctx) {
	// Parse the query string.
	String query_string = (String) 
	    msg_ctx.get(MessageContext.QUERY_STRING);
	
	// Get all teams.
	if (query_string == null) 
	    // Respond with list of all teams
	    return new StreamSource(new ByteArrayInputStream(team_bytes));
	// Get a named team.
	else {  
	    String name = get_name_from_qs(query_string);

	    // Check if named team exists.
	    Team team = team_map.get(name);
	    if (team == null)
		throw new HTTPException(404); // not found

	    // Respond with named team.
	    ByteArrayInputStream stream = encode_to_stream(team);
	    return new StreamSource(stream);
	}
    } 

    private Source doPost(MessageContext msg_ctx) {
	// Extract the POST request from the message.
	Map<String, List> request = (Map<String, List>)
	    msg_ctx.get(MessageContext.HTTP_REQUEST_HEADERS);

	List<String> cargo = request.get(put_post_key);
	if (cargo == null)
	    throw new HTTPException(400); // bad request

	ByteArrayInputStream xml_stream = list_to_bytes(cargo);
	String team_name = null;

	try {
	    // Set up the XPath object to search for the XML elements.
	    DOMResult dom = get_dom(xml_stream);
	    XPath xp = get_xpath();

	    // Extract the team's name.
	    team_name = xp.evaluate("/create_team/name", dom.getNode());

	    // Trying to create an already existing team is not allowed.
	    if (team_map.containsKey(team_name.trim()))
		throw new HTTPException(400); // bad request

	    // Extract the players, names and nicknames
	    List<Player> team_players = new ArrayList<Player>();
	    NodeList players = (NodeList) 
		xp.evaluate("player", 
			    dom.getNode(), 
			    XPathConstants.NODESET);
	    
	    for (int i = 1; i <= players.getLength(); i++) {
		String name = xp.evaluate("name", dom.getNode());
		String nickname = xp.evaluate("nickname", dom.getNode());
		Player player = new Player(name, nickname);
		team_players.add(player);
	    }
	    
	    // Update the team list and map, persist the new list.
	    Team team = new Team(team_name, team_players);
	    teams.add(team);
	    team_map.put(team_name, team);
	    serialize(); 
	}
	catch(XPathExpressionException e) { 
	    throw new HTTPException(400);   // bad request
	}

	// Send back a confirmation that the team has been created.
	return send_response("Team " + team_name + " created.");
    }
    
    private Source doPut(MessageContext msg_ctx) {
       	// Extract the PUT request from the message.
	Map<String, List> request = (Map<String, List>)
	    msg_ctx.get(MessageContext.HTTP_REQUEST_HEADERS);

	List<String> cargo = request.get(put_post_key);
	if (cargo == null)
	    throw new HTTPException(400); // bad request

	ByteArrayInputStream xml_stream = list_to_bytes(cargo);
	String team_name = null;
	String new_name = null;

	try {
	    // Set up the XPath object to search for the XML elements.
	    // XPath works on a DOM (Document Object Model), basically
	    // an XML tree.
	    DOMResult dom = get_dom(xml_stream);
	    XPath xp = get_xpath();

	    // Extract the team's current and new names.
	    team_name = xp.evaluate("/change_team_name/name", dom.getNode());
	    if (team_name == null ||
		!team_map.containsKey(team_name = team_name.trim()))
		throw new HTTPException(404); // no such team

	    new_name = xp.evaluate("/change_team_name/new", dom.getNode());
	    if (new_name == null)
		throw new HTTPException(400); // bad request

	    // Update the team list and map, persist the new list.
	    Team team = team_map.get(team_name);
	    team.setName(new_name.trim());
	    serialize(); 
	}
	catch(XPathExpressionException e) { 
	    throw new HTTPException(400);   // bad request
	}

	// Send back a confirmation that the team has been updated.
	return send_response("Team " + team_name + " changed to " + new_name);
    }
    
    private Source doDelete(MessageContext msg_ctx) {
	String query_string = (String) 
	    msg_ctx.get(MessageContext.QUERY_STRING);
	
	// Disallow the deletion of all teams at once.
        // This must be done one at a time.
	if (query_string == null) 
	    throw new HTTPException(403);     // illegal operation
	else {
	    String name = get_name_from_qs(query_string);
	    if (!team_map.containsKey(name))
		throw new HTTPException(404); // not found

	    // Remove team from list and map.
	    team_map.remove(name);
	    teams.remove(name);
	    serialize();

	    // Send confirmation back to requester.
	    return send_response(name + " deleted");
	}
    }

    private ByteArrayInputStream encode_to_stream(Object obj) {
	// Serialize object to XML and return
	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	XMLEncoder enc = new XMLEncoder(stream);
	enc.writeObject(obj);
	enc.close();
	return new ByteArrayInputStream(stream.toByteArray());
    }

    private String get_name_from_qs(String qs) {
	String[ ] parts = qs.split("=");

	// Check if query string has form: name=<team name>
	if (!parts[0].equalsIgnoreCase("name"))
	    throw new HTTPException(400); // bad request
	return parts[1].trim();
    }

    private DOMResult get_dom(ByteArrayInputStream xml_stream) {
	DOMResult dom = null;
	try {
	    // Set up the XPath object to search for the XML elements.
	    dom = new DOMResult(); // an XML DOM-compliant tree
	    Transformer trans = 
		TransformerFactory.newInstance().newTransformer();
	    trans.transform(new StreamSource(xml_stream), dom);
	}
	catch(TransformerConfigurationException e) {
	    throw new HTTPException(500);  // internal server error
	}
	catch(TransformerException e) {
	    throw new HTTPException(500);  // internal server error
	}
	return dom;
    }

    private StreamSource send_response(String msg) {
	HttpResponse response = new HttpResponse();
	response.setResponse(msg);
	ByteArrayInputStream stream = encode_to_stream(response);
	return new StreamSource(stream);
    }

    private XPath get_xpath() {
	XPath xp = null;
	try {
	    URI ns_URI = new URI("create_team");
	    XPathFactory xpf = XPathFactory.newInstance();
	    xp = xpf.newXPath();
	    xp.setNamespaceContext(new NSResolver("", ns_URI.toString()));
	}
	catch(URISyntaxException e) {
	    throw new HTTPException(500);   // internal server error
	}
	return xp;
    }

    private void read_teams_from_file() {
	try {
	    String cwd = System.getProperty ("user.dir");
	    String sep = System.getProperty ("file.separator");
	    String path = 
		cwd + sep + "ch04" + sep + "team" + sep + file_name;
	    int len = (int) new File(path).length();
	    team_bytes = new byte[len];
	    new FileInputStream(path).read(team_bytes);
	}
	catch(IOException e) { System.err.println(e); }
    }

    private ByteArrayInputStream list_to_bytes(List<String> list) {
	String xml = "";
	for (String next : list) xml += next.trim();
	return new ByteArrayInputStream(xml.getBytes());
    }
    
    private void deserialize() {
	// Deserialize the bytes into a list of teams
	XMLDecoder dec = 
	    new XMLDecoder(new ByteArrayInputStream(team_bytes));
	teams = (List<Team>) dec.readObject();

	// Create a map for quick lookups of teams.
	team_map = new HashMap<String, Team>();
	for (Team team : teams) 
	    team_map.put(team.getName(), team);
    }

    private void serialize() {
	try {     
	    String path = get_file_path();
            BufferedOutputStream out = 
		new BufferedOutputStream(new FileOutputStream(path));
	    XMLEncoder enc = new XMLEncoder(out);
	    enc.writeObject(teams);
            enc.close();
	}
        catch(IOException e) { System.err.println(e); }
    }

    private String get_file_path() {
	String cwd = System.getProperty ("user.dir");
	String sep = System.getProperty ("file.separator");
	return cwd + sep + "ch04" + sep + "team" + sep + file_name;
    }
}

