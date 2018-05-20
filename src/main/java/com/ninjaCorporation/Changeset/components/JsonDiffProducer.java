/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninjaCorporation.Changeset.components;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 *
 * This class procuces diff reports between two jsons
 */
public class JsonDiffProducer {
    
    private final ObjectMapper mapper;

    private final JsonPopulator populator;

    private final String childrenNodeName;

    public JsonDiffProducer(ObjectMapper mapper, JsonPopulator populator, String childrenNodeName) {
        this.mapper = mapper;
        this.populator = populator;
        this.childrenNodeName = childrenNodeName;
    }

    /**
     * This method created a json array with the object nodes that are deleted
     * of created
     *
     * @param fromNodes the list of nodes from the first json
     * @param toNodes fromNodes the list of nodes from the second json
     * @param commonNodes the list of nodes that will be filled with json nodes
     * that exist is both json objects.
     * @param status the status that will characterise the non existed nodes
     * (created of deleted usually)l
     * @param condition the condition that will determine if a node is
     * considered as not existed.
     * @return the json array with the non existed nodes.
     */
    public ArrayNode createNotExistedJsonNodes(List<JsonNode> fromNodes, List<JsonNode> toNodes, List<JsonNode> commonNodes, String status, BiPredicate<JsonNode, JsonNode> condition) {
        ArrayNode createdNodes = mapper.createArrayNode();

        fromNodes.forEach(jsonNode1 -> {
            Optional<JsonNode> node2 = find(toNodes, it -> condition.test(jsonNode1, it)); //find diff of Node

            if (node2.isPresent()) {
                if (commonNodes != null) {
                    commonNodes.add(jsonNode1);
                }
            } else { // the Node does not exist
                populateJsonNodeAsNotExisted(jsonNode1, createdNodes, status);
            }
        });
        return createdNodes;
    }

    public void populateJsonNodeAsNotExisted(JsonNode node, ArrayNode createdNodes, String status) {
        ObjectNode createdNode = mapper.createObjectNode();
        createdNodes.add(createdNode);
        populateJsonNode(node, createdNode, status);
        populateChildren(node, createdNode, status);
    }

    /**
     * This method populates a json node with children nodes if any.
     *
     * @param node the json node that may has children.
     * @param createdNode the json node that has to be filled with children
     * nodes
     * @param status values: created or deleted.
     */
    public void populateChildren(JsonNode node, ObjectNode createdNode, String status) {
        JsonNode nodes = node.get(childrenNodeName);
        if (nodes != null) {// has children nodes
            ArrayNode createdNodes = mapper.createArrayNode();
            createdNode.put(childrenNodeName, createdNodes);//create an array for the added node that waits to be filled with children
            populateJsonNodesAsNotExisted(ImmutableList.copyOf(nodes.elements()), createdNodes, status);
        }
    }

    /**
     * This method populates a list of json nodes.
     *
     * @param nodes a list of json objects
     * @param createdNodes the list of json nodes that will be filled.
     * @param status the status that the created nodes will have.
     */
    public void populateJsonNodesAsNotExisted(List<JsonNode> nodes, ArrayNode createdNodes, String status) {
        nodes.forEach(node -> {
            populateJsonNodeAsNotExisted(node, createdNodes, status);
        });
    }

    /**
     * This method populates a json object with properties from an other json
     * object.
     *
     * @param fromJson the json object that will be read.
     * @param objectNode the json object that will be populated.
     * @param status the status that the created object will have
     */
    public void populateJsonNode(JsonNode fromJson, ObjectNode objectNode, String status) {
        populator.populateNode(fromJson, objectNode, status);
    }

    /**
     * This method populates a json object with some properties from an other
     * json object.
     *
     * @param objectNode the json object that will be populated.
     * @param fromJson the json object that will be read.
     * @param properties the list of properties that will be transfered.
     */
    public static void populateJson(ObjectNode objectNode, JsonNode fromJson, List<String> properties) {
        properties.forEach(propertyName -> objectNode.put(propertyName, fromJson.get(propertyName)));
    }

    /**
     * This method finds an item from a list of {@link JsonNode}
     *
     * @param list the list of {@link JsonNode}.
     * @param predicate the condition uppon which the item will be found
     * @return an optional instance of {@link JsonNode}
     */
    public static Optional<JsonNode> find(List<JsonNode> list, Predicate<JsonNode> predicate) {
        return list.stream().filter(predicate).findFirst();
    }
}
