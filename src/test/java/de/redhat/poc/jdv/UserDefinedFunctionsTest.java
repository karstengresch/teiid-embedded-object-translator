package de.redhat.poc.jdv;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.teiid.deployers.VirtualDatabaseException;
import org.teiid.dqp.internal.datamgr.ConnectorManagerRepository;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.ExecutionFactory;
import org.teiid.translator.TranslatorException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static de.redhat.poc.jdv.JDBCUtils.executeObject;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

  {
    // Add VDB => shall contain void method
    try
    {
      embeddedServer.deployVDB(App.class.getClassLoader().getResourceAsStream("user_defined_functions.vdb"));
    }
    catch (VirtualDatabaseException e)
    {
      e.printStackTrace();
      fail();
    }
    catch (ConnectorManagerRepository.ConnectorManagerException e)
    {
      e.printStackTrace();
      fail();
    }
    catch (TranslatorException e)
    {
      e.printStackTrace();
      fail();
    }
    catch (IOException e)
    {
      e.printStackTrace();
      fail();
    }

    Connection connection = null;
    try
    {
      connection = embeddedServer.getDriver().connect("jdbc:teiid:TemperatureVDB", null);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      fail();
    }
    try
    {
      List<Object> temperature = executeObject(connection, "SELECT celsiusToFahrenheit(100)", false);
      assertTrue(!temperature.isEmpty());
      assertTrue(temperature.get(0).equals(BigDecimal.valueOf(212.0)));


    }
    catch (Exception e) // doesn't catch the exception!
    {
      e.printStackTrace();
      fail();
    }




  }



  @AfterClass
  public static void stopServer() {
    embeddedServer.stop();
  }
}