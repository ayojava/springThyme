/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.springthyme.service.intf;

import java.util.List;
import org.javasoft.springthyme.entity.Customer;

/**
 *
 * @author ayojava
 */
public interface CustomerServiceIntf {
    
    void saveCustomer(Customer customer);
    
    List<Customer> findAllCustomers();
    
    Customer findCustomerByID(Long custID);
    
    Customer editCustomer(Customer customer);
    
}
