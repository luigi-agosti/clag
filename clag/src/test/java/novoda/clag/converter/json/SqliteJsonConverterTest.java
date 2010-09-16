package novoda.clag.converter.json;

import static org.junit.Assert.assertEquals;
import novoda.clag.converter.Converter;
import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
import novoda.clag.model.MetaProperty;
import novoda.clag.model.MetaEntity.OnConflictPolicy;
import novoda.clag.provider.Provider;
import novoda.clag.provider.gae.GaeProvider;
import novoda.clag.servlet.context.Context;
import novoda.clag.servlet.context.GaeRestContext;
import novoda.clag.servlet.context.ServiceInfo;

import org.junit.Test;

/**
 * @author luigi.agosti
 */
public class SqliteJsonConverterTest {

	private Converter converter = new SqliteJsonConverter();
	private Context context = new GaeRestContext();

	@Test
	public void convertMetaDataSet() {
		String result = converter.convert(getSampleEntity(), context);

		assertEquals(
				"{\"dropStatements\":\"drop table if exists Example;\","
						+ "\"createStatements\":\"create table if not exists Example"
						+ "(_id integer primary key autoincrement,id integer,title text,description text,cost integer);\"}",
				result);
	}

	@Test
	public void convertCursor() {
		Cursor cursor = new Cursor();
		cursor.add("title", "title value");
		cursor.add("description", "description value");
		cursor.add("cost", 1);
		cursor.add("id", 1);
		cursor.next();

		String result = converter.convert(cursor, getSampleEntity(), context);

		assertEquals(
				"[{\"title\":\"title value\",\"description\":\"description value\",\"cost\":1,\"id\":1}]",
				result);
	}

	@Test
	public void convertCursorWithMoreThanOne() {
		Cursor cursor = new Cursor();
		cursor.add("title", "title value");
		cursor.add("description", "description value");
		cursor.add("cost", 1);
		cursor.add("id", 1);
		cursor.next();
		cursor.add("title", "title value2");
		cursor.add("description", "description value2");
		cursor.add("cost", 12);
		cursor.add("id", 2);
		cursor.next();

		String result = converter.convert(cursor, getSampleEntity(), context);

		assertEquals(
				"[{\"title\":\"title value\",\"description\":\"description value\",\"cost\":1,\"id\":1},"
						+ "{\"id\":2,\"title\":\"title value2\",\"description\":\"description value2\",\"cost\":12}]",
				result);
	}

	@Test
	public void convertCursorWithoutObject() {
		String result = converter.convert(new Cursor(), getSampleEntity(),
				context);

		assertEquals("[]", result);
	}

	@Test
	public void describeWithNullContext() {
		String result = converter.describe(null);
		assertEquals("{}", result);
	}

	@Test
	public void describe() {
		Context context = new GaeRestContext();
		context.setProvider(getSampleProvider());
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setName("testApplication");
		serviceInfo.setVersion("1");
		context.setServiceInfo(serviceInfo);
		String result = converter.describe(context);
		assertEquals(
				"{\"name\":\"testApplication\",\"version\":\"1\","
						+ "\"dropStatements\":["
						+ "\"drop table if exists Example;\""
						+ "],"
						+ "\"createStatements\":["
						+ "\"create table if not exists Example(_id integer primary key autoincrement,id integer,title text,description text,cost integer);\""
						+ "]" + "}", result);
	}

	// @Test
	// public void convertWithSelfRelationWithTheOwnerOfTheRelation() {
	// Context context = new RestContext();
	// MetaEntity me = getSampleEntity();
	// me.addRelation("parentId", "Example", "Example1", "text", true);
	// Provider provider = new GaeProvider();
	// provider.add(me);
	// context.setProvider(provider);
	//		
	// String result = converter.convert(me, context);
	// assertEquals(
	// "{\"name\":\"Example\",\"columns\":[{\"name\":\"id\",\"type\":\"integer\",\"key\":\"true\"},"
	// +
	// "{\"name\":\"parentId\",\"type\":\"text\"},{\"name\":\"title\",\"type\":\"text\"},"
	// +
	// "{\"name\":\"description\",\"type\":\"text\"},{\"name\":\"cost\",\"type\":\"integer\"}],"
	// +
	// "\"children\":[{\"name\":\"Example\",\"columns\":[{\"name\":\"id\",\"type\":\"integer\",\"key\":\"true\"}"
	// +
	// ",{\"name\":\"parentId\",\"type\":\"text\"},{\"name\":\"title\",\"type\":\"text\"},"
	// +
	// "{\"name\":\"description\",\"type\":\"text\"},{\"name\":\"cost\",\"type\":\"integer\"}]}]}",
	// result);
	// }

	@Test
	public void describeWithNoProvider() {
		Context context = new GaeRestContext();
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setName("testApplication");
		serviceInfo.setVersion("1");
		context.setServiceInfo(serviceInfo);
		String result = converter.describe(context);
		assertEquals("{\"name\":\"testApplication\",\"version\":\"1\"}", result);
	}

	@Test
	public void convertMetaDataSetWithReplaceUnique() {
		String result = converter.convert(getComplexEntity(), context);

		assertEquals(
				"{\"dropStatements\":\"drop table if exists Example;\","
						+ "\"createStatements\":\"create table if not exists Example"
						+ "(_id integer primary key autoincrement,id integer unique on conflict replace,title text);\"}",
				result);
	}

	private Provider getSampleProvider() {
		Provider provider = new GaeProvider();
		provider.add(getSampleEntity());
		return provider;
	}

	private MetaEntity getSampleEntity() {
		MetaEntity entity = new MetaEntity("novoda.clag.Example", "Example");
		entity.add(new MetaProperty.Builder("title").type(
				MetaEntity.Type.STRING).build());
		entity.add(new MetaProperty.Builder("description").type(
				MetaEntity.Type.STRING).build());
		entity.add(new MetaProperty.Builder("cost").type(
				MetaEntity.Type.INTEGER).build());
		entity.add(new MetaProperty.Builder("id").type(MetaEntity.Type.INTEGER)
				.key(true).build());
		return entity;
	}

	private MetaEntity getComplexEntity() {
		MetaEntity entity = new MetaEntity("novoda.clag.Example", "Example");
		entity.add(new MetaProperty.Builder("title").type(
				MetaEntity.Type.STRING).build());
		entity.add(new MetaProperty.Builder("id").type(MetaEntity.Type.INTEGER)
				.unique(true).onConflictPolicy(OnConflictPolicy.REPLACE).key(
						true).build());
		return entity;
	}

}
