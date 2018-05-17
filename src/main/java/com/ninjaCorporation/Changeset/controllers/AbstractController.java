/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.controllers;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Vangelis
 */
public abstract class AbstractController {
    
    private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);
    
    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletRequest request){
        LOG.error(String.format("An error occurred while", request.getServletPath()), ex);
    }    
}
