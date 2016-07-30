package com.oogie.jackson;


import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hueyng on 7/29/2016.
 */
public class FeeScheduleJsonTest {
    private static File jsonFile;
    private FeeScheduleJSon instance;

    @BeforeClass
    public static void loadMyResources() {
        jsonFile = new File("./src/main/resources/26674926600.json");
    }

    @AfterClass
    public static void releaseResources() {
        jsonFile = null;
    }

    @Before
    public void init() {
        instance = new FeeScheduleJSon(jsonFile);
    }

    private JsonNode retriveProductPackages() {
        return instance.findNode(createNames("productPackages"));
    }

    @Test
    public void findProductPackages() {
        JsonNode node = retriveProductPackages();

        assertThat(node, notNullValue());
        assertThat(node.isArray(), is(true));
    }

    private List<JsonNode> retrievePackage_Signature_Choic_Advtg_Essentl() {
        JsonNode prodPkgNode = retriveProductPackages();
        return instance.findNodes(prodPkgNode, "name", createNames("Signature", "SignatureChoice", "Advantage", "Essentials"));
    }

    @Test
    public void findPackageNode() {
        List<JsonNode> nodes = retrievePackage_Signature_Choic_Advtg_Essentl();
        assertThat(nodes, is(not(empty())));
    }

    private List<JsonNode> retrieveProductItems() {
        JsonNode prodPkgNodes = retriveProductPackages();
        List<JsonNode> prodItemNodes = new ArrayList<JsonNode>();

        for(JsonNode node : prodPkgNodes) {
            // Check the 1st ProdPkgNode
            prodItemNodes = instance.retrieveProductItems(node, createNames("GenExam", "Lens", "Frame", "Refraction"));
            break;
        }
        return prodItemNodes;
    }

    @Test
    public void findProductItems_GenExam_Lens_Frame_Refraction() {
        List<JsonNode> nodes = retrieveProductItems();
        assertThat(nodes, hasSize(4));
    }

    private List<String> createNames(String ... names) {
        List<String> nameList = new ArrayList<String>();
        nameList.addAll(Arrays.asList(names));
        return nameList;
    }

    private List<JsonNode> retrieveCoverageConstraints() {
        List<JsonNode> prodItemNodes = retrieveProductItems();
        List<JsonNode> constraintNodes = new ArrayList<JsonNode>();

        for(JsonNode node : prodItemNodes) {
            //Use the first Product Item
            constraintNodes = instance.retrieveCoverageConstraints(node, createNames("VWRK", "VWRKQ"));
            break;
        }
        return constraintNodes;
    }

    @Test
    public void findCoverageConstraints_VWRK_VWRKQ(){
        List<JsonNode> nodes = retrieveCoverageConstraints();
        assertThat(nodes, hasSize(2));
    }
//    public List<JsonNode> retrieveCoverageConstraints(JsonNode prodItemNode, List<String> providerNetworkNames) {
}
