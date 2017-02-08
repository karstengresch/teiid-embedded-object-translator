package de.redhat.poc.jdv;

import org.junit.BeforeClass;
import org.junit.Test;
import org.teiid.deployers.VirtualDatabaseException;
import org.teiid.dqp.internal.datamgr.ConnectorManagerRepository;
import org.teiid.runtime.EmbeddedConfiguration;
import org.teiid.runtime.EmbeddedServer;
import org.teiid.translator.TranslatorException;
import org.teiid.translator.jdbc.h2.H2ExecutionFactory;

import javax.resource.ResourceException;
import javax.sql.DataSource;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Karsten Gresch on 08.02.17.
 */
public class EmbeddedH2Test
{
  private static EmbeddedServer embeddedServer= new EmbeddedServer();

  @BeforeClass
  public static void startServer() throws TranslatorException, ConnectorManagerRepository.ConnectorManagerException, VirtualDatabaseException, IOException, ResourceException
  {

    embeddedServer.start(new EmbeddedConfiguration());
    H2ExecutionFactory h2ExecutionFactory = new H2ExecutionFactory();
    h2ExecutionFactory.setSupportsDirectQueryProcedure(true);
    h2ExecutionFactory.start();
    h2ExecutionFactory.setSupportsDirectQueryProcedure(true);
    embeddedServer.deployVDB(App.class.getClassLoader().getResourceAsStream("h2_models.vdb"));
    embeddedServer.addTranslator("translator-h2", h2ExecutionFactory);

    DataSource dataSource = EmbeddedHelper.newDataSource("org.h2.Driver", "jdbc:h2:mem://localhost/~/person", "sa", "sa");
    embeddedServer.addConnectionFactory("java:/person-ds", dataSource);

  }



  @Test
  public void testBaseSetup()
  {
    assertTrue( true );
  }



}
