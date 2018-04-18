package com.blackducksoftware.integration.hub.detect.bomtool;

import com.blackducksoftware.integration.hub.bdio.graph.DependencyGraph;
import com.blackducksoftware.integration.hub.bdio.model.externalid.ExternalId;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class YarnBomToolTest {
    private YarnBomTool yarnBomTool;
    private DependencyGraph dependencyGraph;
    private List<String> testLines;

    @Before
    public void setup() {
        yarnBomTool = new YarnBomTool();
    }

    @Test
    public void testThatYarnListLineAtBeginningIsIgnored() {
        testLines = new ArrayList<>();
        testLines.add("yarn list v1.5.1");
        testLines.add("├─ abab@1.0.4");

        dependencyGraph = yarnBomTool.extractGraphFromYarnListFile(testLines);

        List<ExternalId> tempList = new ArrayList<>(dependencyGraph.getRootDependencyExternalIds());

        assertNotNull(tempList.get(1));
        assertEquals(2, tempList.size());
    }

    @Test
    public void testThatYarnListWithOnlyTopLevelDependenciesIsParsedCorrectly() {
        testLines = new ArrayList<>();
        testLines.add("├─ esprima@3.1.3");
        testLines.add("└─ extsprintf@1.3.0");

        dependencyGraph = yarnBomTool.extractGraphFromYarnListFile(testLines);

        List<ExternalId> tempList = new ArrayList<>(dependencyGraph.getRootDependencyExternalIds());

        assertListContainsDependency("esprima", tempList);
        assertListContainsDependency("extsprintf", tempList);
    }


    @Test
    public void testThatYarnListWithGrandchildIsParsedCorrectly() {
        testLines = new ArrayList<>();
        testLines.add("├─ yargs-parser@4.2.1");
        testLines.add("│  └─ camelcase@^3.0.0");

        dependencyGraph = yarnBomTool.extractGraphFromYarnListFile(testLines);

        List<ExternalId> tempList = new ArrayList<>(dependencyGraph.getRootDependencyExternalIds());
        List<ExternalId> kidsList = new ArrayList<>();
        for (int i = 0; i < tempList.size(); i++) {
            if ("yargs-parser".equals(tempList.get(i).name))
                kidsList = new ArrayList<>(dependencyGraph.getChildrenExternalIdsForParent(tempList.get(i)));
        }

        assertListContainsDependency("yargs-parser", tempList);
        assertListContainsDependency("camelcase", kidsList);
    }

    @Test
    public void testThatYarnListWithGreatGrandchildrenIsParsedCorrectly() {
        testLines = new ArrayList<>();
        testLines.add("├─ yargs-parser@4.2.1");
        testLines.add("│  └─ camelcase@^3.0.0");
        testLines.add("│  │  └─ ms@0.7.2");

        dependencyGraph = yarnBomTool.extractGraphFromYarnListFile(testLines);

        List<ExternalId> tempList = new ArrayList<>(dependencyGraph.getRootDependencyExternalIds());
        List<ExternalId> kidsList = new ArrayList<>();
        for (int i = 0; i < tempList.size(); i++) {
            if ("yargs-parser".equals(tempList.get(i).name))
                kidsList = new ArrayList<>(dependencyGraph.getChildrenExternalIdsForParent(tempList.get(i)));
        }

        assertListContainsDependency("yargs-parser", tempList);
        assertListContainsDependency("camelcase", kidsList);
        assertListContainsDependency("ms", kidsList);
    }

    private void assertListContainsDependency(String dep, List<ExternalId> list) {
        System.out.println(dep);
        for (int i = 0; i < list.size(); i++) {
            if (dep.equals(list.get(i).name)) {
                assertTrue(true);
                return;
            }
        }
        fail();
    }

    @Test
    public void testThatYarnListRegexParsesTheCorrectText() {
        String input = "│  │  ├─ engine.io-client@~1.8.4";
        assertEquals("engine.io-client@~1.8.4", yarnBomTool.grabFuzzyName(input));

        input = "│  ├─ test-fixture@PolymerElements/test-fixture";
        assertEquals("test-fixture@PolymerElements/test-fixture", yarnBomTool.grabFuzzyName(input));

        input = "│  │  ├─ tough-cookie@>=0.12.0";
        assertEquals("tough-cookie@>=0.12.0", yarnBomTool.grabFuzzyName(input));

        input = "│  │  ├─ cryptiles@2.x.x";
        assertEquals("cryptiles@2.x.x", yarnBomTool.grabFuzzyName(input));

        input = "│  │  ├─ asn1@0.2.3";
        assertEquals("asn1@0.2.3", yarnBomTool.grabFuzzyName(input));

        input = "│  ├─ cssom@>= 0.3.2 < 0.4.0";
        assertEquals("cssom@>= 0.3.2 < 0.4.0", yarnBomTool.grabFuzzyName(input));

    }
}
