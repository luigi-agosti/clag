package novoda.clag.converter.json;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import novoda.clag.converter.Converter;
import novoda.clag.introspector.Introspector;
import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
import novoda.clag.model.MetaProperty;
import novoda.clag.provider.Provider;
import novoda.clag.provider.gae.GaeProvider;
import novoda.clag.servlet.context.Context;
import novoda.clag.servlet.context.RestContext;
import novoda.clag.servlet.context.ServiceInfo;

import org.junit.Test;

/**
 * @author luigi.agosti
 */
public class JsonCoverterTest {

	private static final String EXAMPLE_JSON = "{\"name\":\"Example\",\"columns\":["
		+ "{\"name\":\"id\",\"type\":\"integer\",\"key\":\"true\"},"
		+ "{\"name\":\"title\",\"type\":\"text\"}"
		+ ",{\"name\":\"description\",\"type\":\"text\"}"
		+ ",{\"name\":\"cost\",\"type\":\"integer\"}" + "]}";
	
	private Converter converter = new JsonConverter();

	@Test
	public void convertMetaDataSet() {
		String result = converter.convert(getSampleEntity());

		assertEquals(EXAMPLE_JSON, result);
	}

	@Test
	public void convertCursor() {
		Cursor cursor = new Cursor();
		cursor.add("title", "title value");
		cursor.add("description", "description value");
		cursor.add("cost", 1);
		cursor.add("id", 1);
		cursor.next();

		String result = converter.convert(cursor, getSampleEntity());

		assertEquals(
				"[{\"title\":\"title value\",\"description\":\"description value\",\"cost\":1,\"id\":1}]",
				result);
	}

	@Test
	public void convertCursorWithDateField() {
		Cursor cursor = new Cursor();
		cursor.add("date", new Date(1));
		cursor.next();

		String result = converter.convert(cursor, getSampleEntity());

		assertEquals("[{\"date\":1}]", result);
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

		String result = converter.convert(cursor, getSampleEntity());

		assertEquals(
				"[{\"title\":\"title value\",\"description\":\"description value\",\"cost\":1,\"id\":1},"
						+ "{\"id\":2,\"title\":\"title value2\",\"description\":\"description value2\",\"cost\":12}]",
				result);
	}

	@Test
	public void convertCursorWithoutObject() {
		String result = converter.convert(new Cursor(), getSampleEntity());

		assertEquals("[]", result);
	}
	
	@Test
	public void describeWithNullContext() {
		String result = converter.describe(null);
		assertEquals("{}", result);
	}

	@Test
	public void describe() {
		Context context = new RestContext();
		context.setProvider(getSampleProvider());
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setName("testApplication");
		serviceInfo.setVersion("1");
		context.setServiceInfo(serviceInfo);
		String result = converter.describe(context);
		assertEquals(
			"{\"name\":\"testApplication\",\"version\":\"1\"," +
				"\"services\":[" +
					"{\"get\":[\"Example\"]}" +
				"]," +
				"\"schema\":[" +
					EXAMPLE_JSON +
				"]" +
			"}", result);
	}

	@Test
	public void describeWithNoProvider() {
		Context context = new RestContext();
		ServiceInfo serviceInfo = new ServiceInfo();
		serviceInfo.setName("testApplication");
		serviceInfo.setVersion("1");
		context.setServiceInfo(serviceInfo);
		String result = converter.describe(context);
		assertEquals(
				"{\"name\":\"testApplication\",\"version\":\"1\"}", result);
	}
	
	private Provider getSampleProvider() {
		Provider provider = new GaeProvider();
		provider.add(getSampleEntity());
		return provider;
	}

	private MetaEntity getSampleEntity() {
		MetaEntity entity = new MetaEntity("novoda.clag.Example", "Example");
		entity.add(new MetaProperty.Builder("title").type(
				Introspector.Type.STRING).build());
		entity.add(new MetaProperty.Builder("description").type(
				Introspector.Type.STRING).build());
		entity.add(new MetaProperty.Builder("cost").type(
				Introspector.Type.INTEGER).build());
		entity.add(new MetaProperty.Builder("id").type(
				Introspector.Type.INTEGER).isKey(true).build());
		return entity;
	}

}
