package de.redhat.poc.jdv;

import org.junit.BeforeClass;
import org.junit.Test;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.ExecutionFactory;
import org.teiid.translator.TranslatorException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Karsten Gresch on 08.02.17.
 */
public class EmbeddedH2Test
{
  private static EmbeddedServer embeddedServer= new EmbeddedServer();

  @BeforeClass
  public static void startServer() throws TranslatorException
  {

    embeddedServer.start(new EmbeddedConfiguration());
    ExecutionFactory executionFactory = new ExecutionFactory();
    executionFactory.start();
    // embeddedServer.addTranslator("translator-java", executionFactory);
    executionFactory.setSupportsDirectQueryProcedure(true);
    // server.deployVDB(App.class.getClassLoader().getResourceAsStream("object_example.vdb"));
    // server.deployVDB(App.class.getClassLoader().getResourceAsStream("java_method.vdb"));

    // Connection connection = server.getDriver().connect("jdbc:teiid:objectExampleVDB", null);
    // Connection connection = server.getDriver().connect("jdbc:teiid:javaVDB", null);

    // execute(connection, "SELECT getPlayersById('dummyId')", true);
    // execute(connection, "SELECT * from Team.Team", true);


  }



  @Test
  public void testBaseSetup()
  {
    assertTrue( true );
  }



}
