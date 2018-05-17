/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.utils;

import com.ninjaCorporation.Changeset.exceptions.ExternalException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author Vangelis
 */
public class ResourceUtils {

    public static String getContent(String resourcePath) {
        InputStream inputStream = getInputStream(resourcePath);
        return getContent(inputStream);
    }
    
    public static String getContent(InputStream inputStream){
        try {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        } catch (IOException ex) {
            throw new ExternalException("Could not get content from stream.", ex);
        }
    }
    
    public static InputStream getInputStream(String resourcePath){
        try {
            return new ClassPathResource(resourcePath).getInputStream();
        } catch (IOException ex) {
            throw new ExternalException(String.format("Could not get stream from path: %s.", resourcePath), ex);
        }
    }
}
