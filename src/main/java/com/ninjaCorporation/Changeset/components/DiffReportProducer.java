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
import com.ninjaCorporation.Changeset.exceptions.BusinessException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import static com.ninjaCorporation.Changeset.components.JsonDiffProducer.*;

/**
 *
 * This class produces the diff reports between two changesets.
 */
@Component
public class DiffReportProducer {

    private static final String ID = "id";

    private static final String TYPE = "type";
    private static final String STATUS = "status";
    private static final String PREVIOUS_VERSION = "previousVersion";
    private static final String VERSION = "version";
    private static final String NAME = "name";
    private static final String REFERENCE = "reference";
    private static final String NODES = "nodes";
    private static final String METADATA = "metadata";
    private static final String KEY = "key";
    private static final String VALUE = "value";

    private static class Status {

        public static final String DELETED = "deleted";

        public static final String MODIFIED = "modified";

        public static final String CREATED = "created";

        public static final String UNMODIFIED = "unmodified";
    }

    private final ObjectMapper mapper = new ObjectMapper();

    private JsonDiffProducer nodeDiffProducer;

    private JsonDiffProducer metadataDiffProducer;

    @PostConstruct
    private void postConstruct() {
        JsonPopulator nodePopulator = (JsonNode fromJson, ObjectNode objectNode, String status) -> {
            populateJson(objectNode, fromJson, Arrays.asList(ID, TYPE, VERSION, VERSION));
            objectNode.put(STATUS, status);
            //            objectNode.get(METADATA).elements().forEachRemaining(it -> ((ObjectNode) it).put(STATUS, status));
        };

        JsonPopulator metadataPopulator = (JsonNode fromJson, ObjectNode objectNode, String status) -> {
            populateJson(objectNode, fromJson, Arrays.asList(ID, TYPE, VERSION, VERSION));
            objectNode.put(STATUS, status);
        };
        nodeDiffProducer = new JsonDiffProducer(mapper, nodePopulator, NODES);
        metadataDiffProducer = new JsonDiffProducer(mapper, metadataPopulator, null);
    }

    public String produceDiffReport(String data1, String data2) {
        try {
            JsonNode json1 = mapper.readTree(data1);
            JsonNode json2 = mapper.readTree(data2);
            ObjectNode createdObjectNode = mapper.createObjectNode();
//        JsonNode idProperty1 = json1.get(ID);
//        JsonNode idProperty2 = json2.get(ID);
            populateJson(createdObjectNode, json1, Arrays.asList(ID, TYPE, VERSION));
            createdObjectNode.put(STATUS, Status.UNMODIFIED);
            createdObjectNode.put(PREVIOUS_VERSION, json1.get(VERSION).get(NAME));

            ObjectNode createdReferenceNode = mapper.createObjectNode();
            JsonNode reference1 = json1.get(REFERENCE);
            populateJson(createdReferenceNode, reference1, Arrays.asList(ID, TYPE, VERSION));
            createdObjectNode.put(REFERENCE, createdReferenceNode);
            createdReferenceNode.put(PREVIOUS_VERSION, reference1.get(VERSION));

            createdReferenceNode.put(STATUS, Status.UNMODIFIED);

            JsonNode nodes1 = reference1.get(NODES);
            JsonNode reference2 = json2.get(REFERENCE);

            Iterator<JsonNode> nodesElements1 = nodes1.elements();
            Iterator<JsonNode> nodesElements2 = reference2.get(NODES).elements();

            ArrayNode diffNodes12 = findDiffsFromNodes(ImmutableList.copyOf(nodesElements1), ImmutableList.copyOf(nodesElements2));
//            ArrayNode diffNodes21 = findDiffsFromNodes(nodesElements2, nodesElements1, Status.CREATED);
            ArrayNode createdNodes = createdReferenceNode.putArray(NODES);
            createdNodes.addAll(diffNodes12);
//            createdNodes.add(diffNodes21);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("C://debug/result.json"), createdObjectNode);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(createdObjectNode);
        } catch (IOException ex) {
            throw new BusinessException("Could not produce diff report.", ex);
        }
    }

    private ArrayNode findDiffsFromNodes(List<JsonNode> nodesElements1, List<JsonNode> nodesElements2) {
        List<JsonNode> commonNodes1 = new ArrayList<>();
        List<JsonNode> commonNodes2 = new ArrayList<>();
        ArrayNode diffNodes = createNonExistedNodes(nodesElements1, nodesElements2, commonNodes1, Status.DELETED);

        diffNodes.addAll(createNonExistedNodes(nodesElements2, nodesElements1, commonNodes2, Status.CREATED));
        diffNodes.addAll(findDiffsFromMetadata(commonNodes1, commonNodes2));
        //for commonNodes, populate them as modified
        return diffNodes;
    }

    private ArrayNode findDiffsFromMetadata(List<JsonNode> commonNodes1, List<JsonNode> commonNodes2) {
        ArrayNode createdNodes = mapper.createArrayNode();
        commonNodes1.forEach(node1 -> {
            List<JsonNode> commonMetadatas1 = new ArrayList<>();
            List<JsonNode> commonMetadatas2 = new ArrayList<>();
            ObjectNode createdNode = mapper.createObjectNode();
            ArrayNode createdMetadatas = mapper.createArrayNode();
            nodeDiffProducer.populateJsonNode(node1, createdNode, Status.MODIFIED);
            createdNode.put(METADATA, createdMetadatas);
            createdNodes.add(createdNode);

            long idValue = node1.get(ID).asLong();
            JsonNode node2 = find(commonNodes2, it -> it.get(ID).asLong() == idValue).get();//it has to be found because it's common

            List<JsonNode> metadatas1 = ImmutableList.copyOf(node1.get(METADATA).elements());
            List<JsonNode> metadatas2 = ImmutableList.copyOf(node2.get(METADATA).elements());

            createdMetadatas = createNotExistedMetadata(metadatas1, metadatas2, commonMetadatas1, Status.DELETED);
            createdMetadatas.addAll(createNotExistedMetadata(metadatas2, metadatas1, commonMetadatas2, Status.CREATED));
            createdMetadatas.addAll(createCommonMetadatas(commonMetadatas1, commonMetadatas2));
        });
        return createdNodes;
    }

    private ArrayNode createNotExistedMetadata(List<JsonNode> fromMetadatas, List<JsonNode> toMetadatas, List<JsonNode> commonMetadatas, String status) {
        return metadataDiffProducer.createNotExistedJsonNodes(fromMetadatas, toMetadatas, commonMetadatas, status,
                (jsonNode1, jsonNode2) -> jsonNode1.get(VERSION).get(KEY).asText().equals(jsonNode2.get(VERSION).get(KEY).asText()));
    }

    private ArrayNode createCommonMetadatas(List<JsonNode> metadatas1, List<JsonNode> metadatas2) {
        ArrayNode createdMetadatas = mapper.createArrayNode();
        metadatas1.forEach(metadata1 -> {
            ObjectNode createdMetadata = mapper.createObjectNode();
            createdMetadatas.add(createdMetadata);

            JsonNode metadata2 = find(metadatas2, it -> it.get(ID).asLong() == metadata1.get(ID).asLong()).get();
            String status = Status.MODIFIED;
            if (metadata1.get(VERSION).get(VALUE).asText().equals(metadata2.get(VERSION).get(VALUE).asText())) {
                status = Status.UNMODIFIED;
            }
            metadataDiffProducer.populateJsonNode(metadata1, createdMetadata, status);

        });
        return createdMetadatas;
    }

    private ArrayNode createNonExistedNodes(List<JsonNode> fromNodeElements, List<JsonNode> toNodeElements, List<JsonNode> commonNodes, String status) {
        return nodeDiffProducer.createNotExistedJsonNodes(fromNodeElements, toNodeElements, commonNodes, status,
                (jsonNode1, jsonNode2) -> jsonNode1.get(ID).asLong() == jsonNode2.get(ID).asLong());
    }
}
