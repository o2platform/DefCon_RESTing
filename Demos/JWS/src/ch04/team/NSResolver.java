package ch04.team;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

public class NSResolver implements NamespaceContext {
    private Map<String, String> prefix_to_uri;
    private Map<String, String> uri_to_prefix;

    public NSResolver() {
        if (prefix_to_uri == null)
            prefix_to_uri = new HashMap<String, String>();
        if (uri_to_prefix == null)
            uri_to_prefix = new HashMap<String, String>();
    }

    public NSResolver(String prefix, String uri) {
        this();
        prefix_to_uri.put(prefix, uri);
        uri_to_prefix.put(uri, prefix);
    }

    public String getNamespaceURI(String prefix) {
        return prefix_to_uri.get(prefix);
    }
    public String getPrefix(String uri) {
        return uri_to_prefix.get(uri);
    }
    public Iterator getPrefixes(String uri) {
        return uri_to_prefix.keySet().iterator();
    }
}
