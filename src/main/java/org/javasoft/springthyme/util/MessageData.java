/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.springthyme.util;

import lombok.Getter;

/**
 *
 * @author ayojava
 */
public class MessageData {

    //Name of the flash attribute.
    public static final String MESSAGE_ATTRIBUTE = "message";

    /**
     * The type of the message to be displayed. The type is used to show message
     * in a different style.
     */
    public static enum Type {
        DANGER, WARNING, INFO, SUCCESS;
    }

    @Getter
    private final String message;
    
    @Getter
    private final Type type;
    
    @Getter
    private final Object[] args;

    public MessageData(String message, Type type) {
        this.message = message;
        this.type = type;
        this.args = null;
    }

    public MessageData(String message, Type type, Object... args) {
        this.message = message;
        this.type = type;
        this.args = args;
    }
}
