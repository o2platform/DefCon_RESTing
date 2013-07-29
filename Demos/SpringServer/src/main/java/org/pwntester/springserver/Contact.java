package org.pwntester.springserver;

/**
 * Created with IntelliJ IDEA.
 * User: alvms
 * Date: 4/7/13
 * Time: 8:03 PM
 * To change this template use File | Settings | File Templates.
 */

public interface Contact {

    public Long getId();
    public void setId(Long id);
    public String getFirstName();
    public void setFirstName(String name);
    public String getLastName();
    public void setLastName(String lastName);
    public String getEmail();
    public void setEmail(String email);
}
