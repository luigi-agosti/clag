package novoda.clag.provider.gae;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import novoda.clag.introspector.jdo.JdoIntrospector;
import novoda.clag.introspector.jdo.sample.Page;
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
	
	@Test
	public void shouldGetListProperty() {
		Entity e = new Entity(Page.class.getSimpleName());
		e.setProperty("groupIds", Arrays.asList("1", "2"));
		ds.put(e);
		
		provider.setIntrospector(new JdoIntrospector());
		provider.add(Page.class);

		Options options = new Options();
		options.setLimit(1);
		
		Cursor cursor = provider.query(Page.class.getSimpleName(), null, null,
				null, null, options);
		
		assertNotNull(cursor);
		assertNotNull(cursor.getRows());
		assertEquals(1, cursor.getRows().size());
		
		Map<String, Object> properties = cursor.getRows().get(0);
		assertNotNull(properties);
		assertTrue(properties.containsKey("groupIds"));
		assertEquals(Arrays.asList("1", "2"), properties.get("groupIds"));
	}
	
	@Test
	public void shouldInsertACursor() {
		MetaEntity me = new MetaEntity("Example", "Example");
		me.addKey("id", MetaEntity.Type.INTEGER);
		me.add("textProperty", MetaEntity.Type.STRING);
		me.add("integerProperty", MetaEntity.Type.INTEGER);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("textProperty", "test");
		
		Cursor cursor = new Cursor(me.getName());
		cursor.addRow(map);
			
		Cursor result = provider.insert(me.getName(), cursor, me);
		
		assertNotNull(result);
		assertEquals(1, result.getRows().size());
		Map<String, Object> row = result.getRows().get(0);  
		assertTrue(row.containsKey("textProperty"));
		assertEquals("test", row.get("textProperty"));
		assertTrue(row.containsKey("id"));
		assertEquals(Long.valueOf(1), row.get("id"));
	}

}
