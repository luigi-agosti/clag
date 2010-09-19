package novoda.clag.servlet.action;

import static org.junit.Assert.assertEquals;
import novoda.clag.converter.Converter;
import novoda.clag.converter.json.RestProviderConverter;
import novoda.clag.model.MetaEntity;
import novoda.clag.provider.Provider;
import novoda.clag.provider.gae.GaeProvider;
import novoda.clag.servlet.context.GaeRestContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
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
				me.addKey("id", "integer");
				me.add("note", "text");
				gp.add(me);
				return gp;
			}
			@Override
			public Converter getConverter() {
				return new RestProviderConverter();
			}
			@Override
			public String getData() {
				return "[{\"note\":\"test\"}]";
			}
		});
		assertEquals("[{\"note\":\"test\",\"id\":1}]", result);
	}
	
}
