package de.redhat.poc.jdv;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.teiid.deployers.VirtualDatabaseException;
import org.teiid.dqp.internal.datamgr.ConnectorManagerRepository;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.TranslatorException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static de.redhat.poc.jdv.JDBCUtils.executeForList;
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
  }

  @Test
  public void testBaseSetup()
  {
    assertTrue( true );
  }

  @Test
  public void testUserDefinedFunctionCallSingleResult()
  {
    try
    {
      embeddedServer.deployVDB(App.class.getClassLoader().getResourceAsStream("user_defined_functions.vdb"));
      Thread.sleep(1000);
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
    catch (InterruptedException e)
    {
      e.printStackTrace();
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
      List<Object> temperature = executeForList(connection, "SELECT celsiusToFahrenheit(100)", true);
      assertTrue(!temperature.isEmpty());
      assertTrue(temperature.get(0).equals(BigDecimal.valueOf(212.0)));
      System.out.println("Result was: " + temperature.get(0));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      fail();
    }

    try
    {
      connection.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      fail();
    }
  }


  @Test
  public void testUserDefinedFunctionCallListResult()
  {
    // TODO assert that VDB already is there OR create separate one!

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
      List<Object> resultList = executeForList(connection, "SELECT getManyValues(\'SuperDuperId\')", false);
      assertTrue(!resultList.isEmpty());
      System.out.println("Result was: " + resultList.get(0));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      fail();
    }
    try
    {
      connection.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      fail();
    }
    try
    {
      connection.close();
    }
    catch (SQLException e)
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