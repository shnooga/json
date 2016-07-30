package com.oogie.jackson;


import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oogie.jackson.affiliate.ProductPackage;
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

    private JsonNode retrieveProductPackages_Sig_Choic_Adv_Ess() {
        List<String> names = new ArrayList<String>();
        names.add("productPackages");
        return instance.findNode(names);
    }

    @Test
    public void findProductPackages() {
        JsonNode node = retrieveProductPackages_Sig_Choic_Adv_Ess();

        assertThat(node, notNullValue());
        assertThat(node.isArray(), is(true));
    }

    @Test
    public void findPackageNode() {
        JsonNode prodPkgNode = retrieveProductPackages_Sig_Choic_Adv_Ess();
        List<JsonNode> nodes = instance.findNodes(prodPkgNode, "name", createNames("Signature", "SignatureChoice", "Advantage", "Essentials"));

        assertThat(nodes, is(not(empty())));
    }

    private List<JsonNode> retrieveProductItems() {
        JsonNode prodPkgNodes = retrieveProductPackages_Sig_Choic_Adv_Ess();
        List<JsonNode> prodItemNodes = new ArrayList<JsonNode>();

        // Use the first prodPkgNode
        for(JsonNode node : prodPkgNodes) {
            prodItemNodes = instance.retrieveProductItems(node, createNames("GenExam", "Lens", "Frame", "Refraction"));
            break;
        }
        return prodItemNodes;
    }

    @Test
    public void findProductItems_GenExam_Lens_Frame_Refraction() {
        List<JsonNode> nodes = retrieveProductItems();
        assertThat(nodes, hasSize(3));
    }

    private List<String> createNames(String ... names) {
        List<String> nameList = new ArrayList<String>();
        nameList.addAll(Arrays.asList(names));
        return nameList;
    }

    private List<JsonNode> retrieveCoverageConstraints() {
        List<JsonNode> prodItemNodes = retrieveProductItems();
        List<JsonNode> constraintNodes = new ArrayList<JsonNode>();

        // Use the first prodPkgNode
        for(JsonNode node : prodItemNodes) {
            prodItemNodes = instance.retrieveCoverageConstraints(node, createNames("VWRKQ", "VWRKQ"));
            break;
        }
        return constraintNodes;
//        assertThat(prodItemNodes, hasSize(3));
    }

    @Test
    public void findCoverageConstraints_VWRK_VWRKQ(){
        List<JsonNode> nodes = retrieveCoverageConstraints();
        assertThat(nodes, hasSize(2));
    }
//    public List<JsonNode> retrieveCoverageConstraints(JsonNode prodItemNode, List<String> providerNetworkNames) {
}
