package org.pwntester.springserver;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: alvms
 * Date: 4/7/13
 * Time: 8:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ContactRepository extends JpaRepository<ContactImpl, Long> {


}
