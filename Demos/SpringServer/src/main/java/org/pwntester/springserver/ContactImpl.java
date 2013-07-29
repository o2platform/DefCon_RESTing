package org.pwntester.springserver;

import javax.persistence.*;


/**
 * Created with IntelliJ IDEA.
 * User: alvms
 * Date: 4/7/13
 * Time: 8:04 PM
 * To change this template use File | Settings | File Templates.
 */


@Entity(name = "contact")
public class ContactImpl implements Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private String firstName;

    @Basic
    private String lastName;

    @Basic
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}