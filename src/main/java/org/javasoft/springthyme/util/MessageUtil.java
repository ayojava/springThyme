/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.springthyme.util;

import static org.javasoft.springthyme.util.MessageData.MESSAGE_ATTRIBUTE;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author ayojava
 * https://github.com/thymeleaf/thymeleafexamples-layouts
 */
public class MessageUtil {
    
    public static void addRedirectSuccessAttribute(RedirectAttributes ra, String message, Object... args) {
        addRedirectAttribute(ra, message, MessageData.Type.SUCCESS, args);
    }

    public static void addRedirectErrorAttribute(RedirectAttributes ra, String message, Object... args) {
        addRedirectAttribute(ra, message, MessageData.Type.DANGER, args);
    }

    public static void addRedirectInfoAttribute(RedirectAttributes ra, String message, Object... args) {
        addRedirectAttribute(ra, message, MessageData.Type.INFO, args);
    }

    public static void addRedirectWarningAttribute(RedirectAttributes ra, String message, Object... args) {
        addRedirectAttribute(ra, message, MessageData.Type.WARNING, args);
    }

    private static void addRedirectAttribute(RedirectAttributes ra, String message, MessageData.Type type, Object... args) {
        ra.addFlashAttribute(MESSAGE_ATTRIBUTE, new MessageData(message, type, args));
    }

    public static void addSuccessAttribute(Model model, String message, Object... args) {
        addAttribute(model, message, MessageData.Type.SUCCESS, args);
    }

    public static void addErrorAttribute(Model model, String message, Object... args) {
        addAttribute(model, message, MessageData.Type.DANGER, args);
    }

    public static void addInfoAttribute(Model model, String message, Object... args) {
        addAttribute(model, message, MessageData.Type.INFO, args);
    }

    public static void addWarningAttribute(Model model, String message, Object... args) {
        addAttribute(model, message, MessageData.Type.WARNING, args);
    }

    private static void addAttribute(Model model, String message, MessageData.Type type, Object... args) {
        model.addAttribute(MESSAGE_ATTRIBUTE, new MessageData(message, type, args));
}
}
