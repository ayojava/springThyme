/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.springthyme.repository;

import org.javasoft.springthyme.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ayojava
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>{
    
}
