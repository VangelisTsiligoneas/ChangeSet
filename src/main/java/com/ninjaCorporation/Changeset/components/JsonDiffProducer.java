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
    
//    private static final String STATUS = "status";
    
    private final ObjectMapper mapper;
    
    private final JsonPopulator populator;
    
    private final String childrenNodeName;

    public JsonDiffProducer(ObjectMapper mapper, JsonPopulator populator, String childrenNodeName) {
        this.mapper = mapper;
        this.populator = populator;
        this.childrenNodeName = childrenNodeName;
    }
    
    public ArrayNode createNotExistedJsonNodes(List<JsonNode> fromMetadatas, List<JsonNode> toMetadatas, List<JsonNode> commonMetadatas, String status, BiPredicate<JsonNode, JsonNode> condition){
        ArrayNode createdNodes = mapper.createArrayNode();

        fromMetadatas.forEach(jsonNode1 -> {
            Optional<JsonNode> node2 = find(toMetadatas, it -> condition.test(jsonNode1, it)); //find diff of Node

            if (node2.isPresent()) {
                if (commonMetadatas != null) {
                    commonMetadatas.add(jsonNode1);
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
    
    public void populateJsonNodesAsNotExisted(List<JsonNode> nodes, ArrayNode createdNodes, String status) {
        nodes.forEach(node -> {
            populateJsonNodeAsNotExisted(node, createdNodes, status);
        });
    }

    public void populateJsonNode(JsonNode fromJson, ObjectNode objectNode, String status) {
        populator.populateNode(fromJson, objectNode, status);
    }

    public static void populateJson(ObjectNode objectNode, JsonNode fromJson, List<String> properties) {
        properties.forEach(propertyName -> objectNode.put(propertyName, fromJson.get(propertyName)));
    }


    public static Optional<JsonNode> find(List<JsonNode> list, Predicate<JsonNode> predicate) {
        return list.stream().filter(predicate).findFirst();
    }
}
