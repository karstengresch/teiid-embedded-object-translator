package de.redhat.poc.jdv;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.ExecutionFactory;
import org.teiid.translator.TranslatorException;

import java.sql.Connection;

import static de.redhat.poc.jdv.JDBCUtils.execute;
import static org.junit.Assert.assertTrue;

/**
 * Unit test the Java translator.
 */
public class UserDefinedFunctionsTest
{
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */

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

  @Test
  public void testUserDefinedFunctionCall()
      throws Exception
  {
    // Add VDB => shall contain void method
    embeddedServer.deployVDB(App.class.getClassLoader().getResourceAsStream("user_defined_functions.vdb"));
    Connection connection = embeddedServer.getDriver().connect("jdbc:teiid:objectExampleVDB", null);
    execute(connection, "SELECT * from TeamView.Players", true);

    assertTrue( true );
  }



  @AfterClass
  public static void stopServer() {
    embeddedServer.stop();
  }
}