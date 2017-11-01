/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.springthyme.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author ayojava
 */
@SuppressWarnings("serial")
@Data
@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(generator = "customerID_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "customerID_GEN", sequenceName = "customerID_SEQ")
    private Long customerID;

    @NotNull
    private String title;

    private String regNo;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String gender;

    @Valid
    @Column
    @Embedded
    private Address addressTemplate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date createDate;
    
    @Transient
    private String fullName;
    
    public String getFullName(){
        return title + " " + firstName + " " + lastName ;
    }

   
}
