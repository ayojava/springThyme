/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.springthyme.entity;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author ayojava
 */
@Data
@NoArgsConstructor
@Embeddable
public class Address {

    @NotNull
    @ColumnTransformer(write = "UPPER(?)")
    private String street;

    @NotNull
    @ColumnTransformer(write = "UPPER(?)")
    private String city;

    @NotNull
    private String country;
    
    private String zipCode;
    
    private String contactPhoneNo;

    @Email(message = "{email.invalid}")
    @ColumnTransformer(write = "UPPER(?)")
    private String email;

    @Transient
    private String fullAddress;

    public String getFullAddress() {
        return street + "   " + city;
    }

}
