/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 *
 * This interface defines how a json object is populated
 */
@FunctionalInterface
public interface JsonPopulator {

    void populateNode(JsonNode fromJson, ObjectNode objectNode, String status);
}
