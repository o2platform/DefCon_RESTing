package org.pwntester.springserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.tests.Exploit;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public final Contact get( @PathVariable( "id" ) final Long contactId ){
        System.out.println("get");
        return contactRepository.findOne(contactId);
    }

    @RequestMapping( method = RequestMethod.POST )
    @ResponseStatus( HttpStatus.CREATED )
    @ResponseBody
    public final String create( @RequestBody final Contact contact ){
        System.out.println("Contact name: " + contact.getFirstName());
        contactRepository.save((ContactImpl) contact);
        return "OK";
    }

    @RequestMapping( value = "/exploit", method = RequestMethod.POST )
    @ResponseStatus( HttpStatus.CREATED )
    @ResponseBody
    public final String create( @RequestBody final Exploit exploit ){
        //System.out.println("Contact name: " + explot.setValue(););
        return exploit.GetValue();
    }


}