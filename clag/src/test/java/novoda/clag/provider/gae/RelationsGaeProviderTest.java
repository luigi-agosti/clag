package novoda.clag.provider.gae;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import novoda.clag.introspector.jdo.JdoIntrospector;
import novoda.clag.introspector.jdo.sample.Page;
import novoda.clag.introspector.jdo.sample.Story;
import novoda.clag.model.Cursor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class RelationsGaeProviderTest {

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
	public void shouldGetPageWithStories() {
		Entity e = new Entity(Page.class.getSimpleName());
		e.setProperty("title", "page title");
		Key key = ds.put(e);
		long pageId = key.getId();
		e = new Entity(Story.class.getSimpleName());
		e.setProperty("pageId", pageId);
		key = ds.put(e);
		long storyId = key.getId();
		
		provider.setIntrospector(new JdoIntrospector());
		provider.add(Story.class);

		Cursor cursor = provider.query(Page.class.getSimpleName(), null, null,
				null, null);
		
		assertNotNull(cursor);
		assertNotNull(cursor.getRows());
		assertEquals(1, cursor.getRows().size());
		Map<String, Object> row = cursor.getRows().get(0);
		assertEquals(pageId, row.get("id"));
		Cursor c = (Cursor)row.get("Story");
		assertNotNull(c);
		c = (Cursor)row.get("Page");
		assertNotNull(c);
	}
	
}
