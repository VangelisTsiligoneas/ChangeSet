/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.exceptions;

/**
 *
 * This external exception is thrown when something is wrong with an external
 * system (Could also be be the file system).
 */
public class ExternalException extends BusinessException {

    public ExternalException(String message) {
        super(message);
    }

    public ExternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
