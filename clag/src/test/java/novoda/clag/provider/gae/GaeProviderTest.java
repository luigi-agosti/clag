package novoda.clag.provider.gae;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import novoda.clag.introspector.jdo.JdoIntrospector;
import novoda.clag.introspector.jdo.sample.Story;
import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
import novoda.clag.model.MetaProperty;
import novoda.clag.model.Options;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class GaeProviderTest {

	private LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

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

	private GaeProvider provider = new GaeProvider();

	@Test
	public void shouldGetMetaInformationAboutId() {
		provider.setIntrospector(new JdoIntrospector());
		provider.add(Story.class);

		MetaEntity entity = provider.schema(Story.class.getSimpleName());
		assertNotNull(entity);

		MetaProperty p = entity.getMetaProperty("id");
		assertNotNull(p);
	}

	@Test
	public void shouldGetIdValues() {
		Entity e = new Entity(Story.class.getSimpleName());
		Key key = ds.put(e);
		long longId = key.getId();
		
		provider.setIntrospector(new JdoIntrospector());
		provider.add(Story.class);

		Cursor cursor = provider.query(Story.class.getSimpleName(), null, null,
				null, null);
		
		assertNotNull(cursor);
		assertNotNull(cursor.getRows());
		assertEquals(1, cursor.getRows().size());
		assertEquals(longId, cursor.getRows().get(0).get("id"));
	}
	
	@Test
	public void shouldGetLimitedResult() {
		Entity e = new Entity(Story.class.getSimpleName());
		ds.put(e);
		e = new Entity(Story.class.getSimpleName());
		ds.put(e);
		
		provider.setIntrospector(new JdoIntrospector());
		provider.add(Story.class);

		Options options = new Options();
		options.setLimit(1);
		
		Cursor cursor = provider.query(Story.class.getSimpleName(), null, null,
				null, null, options);
		
		assertNotNull(cursor);
		assertNotNull(cursor.getRows());
		assertEquals(1, cursor.getRows().size());
	}
	
	@Test
	public void shouldGetResultWithCorrectOffset() {
		Entity e = new Entity(Story.class.getSimpleName());
		ds.put(e);
		e = new Entity(Story.class.getSimpleName());
		Key key2 = ds.put(e);
		
		provider.setIntrospector(new JdoIntrospector());
		provider.add(Story.class);

		Options options = new Options();
		options.setLimit(1);
		options.setOffset(1);
		
		Cursor cursor = provider.query(Story.class.getSimpleName(), null, null,
				null, null, options);
		
		assertNotNull(cursor);
		assertNotNull(cursor.getRows());
		assertEquals(1, cursor.getRows().size());
		assertEquals(key2.getId(), cursor.getRows().get(0).get("id"));
	}

}
