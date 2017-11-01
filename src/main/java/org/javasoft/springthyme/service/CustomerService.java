/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.springthyme.service;

import java.util.List;
import org.javasoft.springthyme.entity.Customer;
import org.javasoft.springthyme.repository.CustomerRepository;
import org.javasoft.springthyme.service.intf.CustomerServiceIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ayojava
 */
@Service
@Transactional
public class CustomerService implements CustomerServiceIntf {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @Override
    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }
    
    @Override
    public List<Customer> findAllCustomers(){
        return (List<Customer>) customerRepository.findAll();
    }
    
    @Override
    public Customer findCustomerByID(Long custID){
        return customerRepository.findOne(custID);
    }

    @Override
    public Customer editCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    
}
