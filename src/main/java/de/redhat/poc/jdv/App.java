package de.redhat.poc.jdv;

import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.ExecutionFactory;

import java.sql.Connection;
import java.util.*;

import static de.redhat.poc.jdv.JDBCUtils.execute;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        TeamObject theTestObject = preparePlayers();
        Map<String, Object> testMap = new LinkedHashMap<String, Object>();
        testMap.put("teamObjectKey", theTestObject);

        // Teiid stuff
        EmbeddedServer server = new EmbeddedServer();
        server.start(new EmbeddedConfiguration());
        ExecutionFactory executionFactory = new ExecutionFactory();
        executionFactory.start();
        server.addTranslator("map-cache", executionFactory);
        executionFactory.setSupportsDirectQueryProcedure(true);
        server.deployVDB(App.class.getClassLoader().getResourceAsStream("object_example.vdb"));

        Connection c = server.getDriver().connect("jdbc:teiid:objectExampleVDB", null);

        // execute(c, "SELECT performRuleOnData('org.teiid.example.drools.Message', 'Hello World', 0)", true);
        execute(c, "SELECT * from Team.Team", true);
        server.stop();

    }


    private static TeamObject preparePlayers() {
      TeamObject teamObject = new TeamObject();
      teamObject.setTeamName("Poets");
      List<String> players = Arrays.asList("Paul Celan", "Pablo Neruda", "Ingeborg Bachmann", "Thomas Kling");
      teamObject.setPlayers(players);
      return teamObject;

    }


}
