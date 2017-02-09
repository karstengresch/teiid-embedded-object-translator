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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static de.redhat.poc.jdv.JDBCUtils.executeForList;
import static de.redhat.poc.jdv.JDBCUtils.executeForMap;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Unit test the Java translator.
 */
public class ObjectTableTest
{
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */

  private static EmbeddedServer embeddedServer = new EmbeddedServer();

  @BeforeClass
  public static void startServer() throws TranslatorException
  {
    embeddedServer.start(new EmbeddedConfiguration());
  }

  @Test
  public void testBaseSetup()
  {
    assertTrue(true);
  }

  @Test
  public void testObjectMapAndList()
  {
    try
    {
      embeddedServer.deployVDB(App.class.getClassLoader().getResourceAsStream("object-vdb.xml"));
      Thread.sleep(1000);
    }
    catch (VirtualDatabaseException e)
    {
      e.printStackTrace();
      fail();
    }
    catch (ConnectorManagerRepository.ConnectorManagerException e)
    {
      fail("ConnectorManagerException occured: " + e.getMessage());
    }
    catch (TranslatorException e)
    {
      fail("TranslatorException occured: " + e.getMessage());
    }
    catch (IOException e)
    {
      fail("IOException occured: " + e.getMessage());
    }
    catch (InterruptedException e)
    {
      fail("InterruptedException occured: " + e.getMessage());
    }

    Connection connection = null;

    try
    {
      connection = embeddedServer.getDriver().connect("jdbc:teiid:ObjectExampleVDB", null);
      Map<Object, Object> team = executeForMap(connection, "SELECT * from Team", false);
      System.out.println("Result was: " + team.toString());
      List<Object> players = executeForList(connection, "SELECT * from Player", true);
      System.out.println("Result was: " + players.toString());
      connection.close();
    }
    catch (SQLException e)
    {
      fail("SQLException occured: " + e.getMessage());
    }
  }

  @AfterClass
  public static void stopServer()
  {
    embeddedServer.stop();
  }
}