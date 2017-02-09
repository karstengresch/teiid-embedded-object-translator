package de.redhat.poc.jdv;

import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;

import java.sql.Connection;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static de.redhat.poc.jdv.JDBCUtils.execute;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Starting Teiid Embedded..." );
        TeamObject theTestObject = preparePlayers();
        Map<String, Object> testMap = new LinkedHashMap<String, Object>();
        testMap.put("teamObjectKey", theTestObject);

        // "durch die Brust ins Auge" (German de-facto-proverb)
        // DefaultCacheManager defaultCacheManager = new DefaultCacheManager();
        // defaultCacheManager.defineConfiguration("Team", new ConfigurationBuilder().simpleCache(true).build());
        // Cache<String, Object> cache = defaultCacheManager.getCache();
        // cache.put("Team", theTestObject);

        // Teiid stuff
        EmbeddedServer embeddedServer = new EmbeddedServer();
        embeddedServer.start(new EmbeddedConfiguration());

        // ExecutionFactory executionFactory = new ExecutionFactory();
        // executionFactory.start();
        // embeddedServer.addTranslator("map-cache", executionFactory);
        // executionFactory.setSupportsDirectQueryProcedure(true);
        // ExecutionFactory simpleMapCacheExecutionFactory = new SimpleMapCacheExecutionFactory();
        // simpleMapCacheExecutionFactory.start();
        // embeddedServer.addTranslator("map-cache", simpleMapCacheExecutionFactory);
        // simpleMapCacheExecutionFactory.setSupportsDirectQueryProcedure(true);
        // ClassRegistry classRegistry = new ClassRegistry();
        // classRegistry.registerClass(TeamObject.class);



        embeddedServer.deployVDB(App.class.getClassLoader().getResourceAsStream("object_example.vdb"));
        // server.deployVDB(App.class.getClassLoader().getResourceAsStream("java_method.vdb"));
        Connection connection = embeddedServer.getDriver().connect("jdbc:teiid:objectExampleVDB", null);
        // Connection connection = server.getDriver().connect("jdbc:teiid:javaVDB", null);

        // execute(connection, "SELECT getPlayersById('dummyId')", true);
        execute(connection, "SELECT * from TeamView.Players", true);
        embeddedServer.stop();

        // defaultCacheManager.stop();

    }

    private static TeamObject preparePlayers() {
      TeamObject teamObject = new TeamObject();
      teamObject.setTeamName("Poets");
      List<String> players = Arrays.asList("Paul Celan", "Pablo Neruda", "Ingeborg Bachmann", "Thomas Kling");
      teamObject.setPlayers(players);
      return teamObject;

    }


}
