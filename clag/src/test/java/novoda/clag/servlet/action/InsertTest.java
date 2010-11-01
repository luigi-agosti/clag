package novoda.clag.servlet.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import novoda.clag.converter.Converter;
import novoda.clag.converter.json.RestProviderConverter;
import novoda.clag.converter.json.SqliteJsonConverter;
import novoda.clag.model.MetaEntity;
import novoda.clag.provider.Provider;
import novoda.clag.provider.gae.GaeProvider;
import novoda.clag.servlet.context.GaeRestContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class InsertTest {

	private LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalServiceTestConfig[] {new LocalDatastoreServiceTestConfig()});

	protected DatastoreService ds = DatastoreServiceFactory
			.getDatastoreService();

	@Before
	public void setUp() {
	    helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}
	
	@Test
	public void shouldInsertSomeData() {
		Insert insertAction = new Insert();
		String result = insertAction.execute(new GaeRestContext() {
			@Override
			public String getName() {
				return "Run";
			}
			@Override
			public Provider getProvider() {
				GaeProvider gp = new GaeProvider();
				MetaEntity me = new MetaEntity("Run", "Run");
				me.addKey("id", Integer.class);
				me.add("note", String.class);
				me.add("date", Date.class);
				me.add("cost", Integer.class);
				me.add("shared", Boolean.class);
				me.add("distance", Long.class);
				gp.add(me);
				return gp;
			}
			@Override
			public Converter getConverter() {
				return new RestProviderConverter();
			}
			@Override
			public String getData() {
				return "[{\"note\":\"test\",\"date\":\"123123\",\"cost\":\"1200\",\"shared\":true,\"distance\":12}]";
			}
		});
		assertEquals("[{\"shared\":true,\"distance\":12,\"date\":123123,\"cost\":1200,\"note\":\"test\",\"id\":1}]", result);
	}
	
	@Test
	public void shouldInsertSomeDataWithSqliteJsonConverter() {
		Insert insertAction = new Insert();
		String result = insertAction.execute(new GaeRestContext() {
			@Override
			public String getName() {
				return "Run";
			}
			@Override
			public Provider getProvider() {
				GaeProvider gp = new GaeProvider();
				MetaEntity me = new MetaEntity("Run", "Run");
				me.addKey("id", Integer.class);
				me.add("note", String.class);
				me.add("date", Date.class);
				me.add("cost", Integer.class);
				me.add("shared", Boolean.class);
				me.add("distance", Long.class);
				gp.add(me);
				return gp;
			}
			@Override
			public Converter getConverter() {
				return new SqliteJsonConverter();
			}
			@Override
			public String getData() {
				return "[{\"note\":\"test\",\"date\":\"123123\",\"cost\":\"1200\",\"shared\":true,\"distance\":12,\"_id\":10}]";
			}
		});
		assertEquals("[{\"id\":1,\"_id\":10}]", result);
	}

	@Test
	public void shouldInsertSomeDataRealCaseFailing() throws EntityNotFoundException {
		Insert insertAction = new Insert();
		String result = insertAction.execute(new GaeRestContext() {
			@Override
			public String getName() {
				return "Run";
			}
			@Override
			public Provider getProvider() {
				GaeProvider gp = new GaeProvider();
				MetaEntity me = new MetaEntity("Run", "Run");
				me.addKey("id", Integer.class);
				me.add("note", String.class);
				me.add("date", Date.class);
				me.add("cost", Integer.class);
				gp.add(me);
				return gp;
			}
			@Override
			public Converter getConverter() {
				return new RestProviderConverter();
			}
			@Override
			public String getData() {
				return "[{\"note\":\"submit\"}]";
			}
		});
		assertEquals("[{\"note\":\"submit\",\"id\":1}]", result);
		
		Entity e = ds.get(KeyFactory.createKey("Run", new Long(1)));
		assertNotNull(e);
		assertEquals("submit", e.getProperty("note"));
		
	}
	
}
