/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Vangelis
 */
public class TimeUtils {

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(new Date().getTime());
    }
}
