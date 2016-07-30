package com.oogie.jackson;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oogie.jackson.affiliate.ProductItem;

/**
 * Created by hueyng on 7/29/2016.
 */
public class FeeScheduleJSon {
    private File jsonFile;
    public FeeScheduleJSon(File jsonFile) {
        this.jsonFile = jsonFile;
    }


    public static void main(String[] args) {
        FeeScheduleJSon instance = new FeeScheduleJSon(new File("./src/main/resources/26674926600.json"));

        instance.run();
    }

    public void run(){

    }

    public JsonNode findNode(List<String> nodeNames) {
        JsonNode node = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonFile);

            for(String name : nodeNames) {
                node = root.path(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return node;
    }

    public List<JsonNode> retrieveProductItems(JsonNode prodPkgNode, List<String> prodCatalogKeyNames) {
        List<JsonNode> nodes = new ArrayList<JsonNode>();
        JsonNode itemNodes = prodPkgNode.path("productItems");

        if(!itemNodes.isArray())
            return nodes;
        for(JsonNode node : itemNodes) {
            for(String name : prodCatalogKeyNames) {
                if(node.path("productCatalogKey").path("name").asText().equals(name))
                    nodes.add(node);
            }
        }
        return nodes;
    }

    public List<JsonNode> retrieveCoverageConstraints(JsonNode prodItemNode, List<String> constraintNames) {
        List<JsonNode> nodes = new ArrayList<JsonNode>();
        JsonNode constraintNodes = prodItemNode.path("coverageConstraints");

        if(!constraintNodes.isArray())
            return nodes;
        for(JsonNode node : constraintNodes) {
           for(String name : constraintNames) {
               if(node.path("providerNetwork").path("networkId").asText().equals(name))
                   nodes.add(node);
           }
        }
        return nodes;
    }

    /**
     * Given a node composed of an array; pick out the one with desired Key values.
     * @param parentNode
     * @param key
     * @param values
     * @return may return an empty list but never a null
     */
    public List<JsonNode> findNodes(JsonNode parentNode, String key, List<String> values) {
        List<JsonNode> foundNodes = new ArrayList<JsonNode>();
        try {
            if (parentNode.isArray()) {
                for (JsonNode node : parentNode) {
                    JsonNode keyNode = node.path(key);
                    if (keyNode == null)
                        continue;
                    for (String name : values) {
                        if(keyNode.asText().equals(name)) {
                            foundNodes.add(keyNode);
                            break;
                        }
                    }
                }
            } else {
//            ProductPackage newFoo = mapper.readValue(jsonNode, ProductPackage.class);
//            nodes.add()
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return foundNodes;
    }
}
