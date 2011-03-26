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
import org.junit.Ignore;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class QueryTest {

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
	
	@Ignore
	@Test
	public void shouldGetSomeData() {
		Query action = new Query();
		String result = action.execute(new GaeRestContext() {
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
			
		});
		assertEquals("[{\"shared\":true,\"distance\":12,\"date\":123123,\"cost\":1200,\"note\":\"test\",\"id\":1}]", result);
	}

}
