package com.neo4j.example.springdataneo4jintroapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.neo4j.configuration.GraphDatabaseSettings.DEFAULT_DATABASE_NAME;

/**
 * see: https://neo4j.com/docs/java-reference/current/java-embedded/include-neo4j/
 */
public class EmbeddedExampleTest {

    static GraphDatabaseService graphDb;

    @BeforeAll
    static void setup() {
        System.out.println("Hello World");
        DatabaseManagementService managementService =
                new DatabaseManagementServiceBuilder(Paths.get("embedded_neo4j_database"))
                        .loadPropertiesFromFile(Paths.get("neo4j.conf"))
                        .build();
        graphDb = managementService.database(DEFAULT_DATABASE_NAME);
        registerShutdownHook(managementService);
        System.out.println("Finished Setting Up Embedded Neo4j Database");
    }

    @Test
    public void test() {
        Transaction transaction = graphDb.beginTx();

        Result result = transaction.execute("CREATE (n:Person {name:'Marcus Chiu', age:25}) RETURN n");
        System.out.println(result.resultAsString());

        ResourceIterator<Node> nodes = transaction.findNodes(Label.label("Person"), "name", "Marcus Chiu");
        List<Node> nodeList = nodes.stream().collect(Collectors.toList());
        for (Node n : nodeList) {
            Map<String, Object> properties = n.getProperties("name", "age");
            System.out.println("node id: " + n.getId() + " properties: " + properties.toString());
        }

        transaction.commit();
        transaction.close();
    }

    private static void registerShutdownHook(final DatabaseManagementService managementService) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                managementService.shutdown();
            }
        });
    }
}
