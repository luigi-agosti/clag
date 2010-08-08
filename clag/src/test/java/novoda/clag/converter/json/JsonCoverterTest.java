package novoda.clag.converter.json;

import static org.junit.Assert.assertEquals;
import novoda.clag.converter.Converter;
import novoda.clag.introspector.Introspector;
import novoda.clag.model.Cursor;
import novoda.clag.model.Entity;
import novoda.clag.model.Property;

import org.junit.Test;

/**
 * @author luigi.agosti
 */
public class JsonCoverterTest {
	
	private Converter converter = new JsonConverter();

	@Test
	public void convertMetaDataSet() {
		String result = converter.convert(getSampleEntity());

		assertEquals(
				"{\"table\":\"org.ls.a2engine.model.Example\",\"columns\":["
						+ "{\"name\":\"title\",\"type\":\"text\"}"
						+ ",{\"name\":\"description\",\"type\":\"text\"}"
						+ ",{\"name\":\"cost\",\"type\":\"integer\"}]}", result);
	}

	@Test
	public void convertCursor() {
		Cursor cursor = new Cursor();
		cursor.add("title", "title value");
		cursor.add("description", "description value");
		cursor.add("cost", 1);
		cursor.next();

		String result = converter.convert(cursor, getSampleEntity());

		assertEquals(
				"[{\"title\":\"title value\",\"description\":\"description value\",\"cost\":1}]",
				result);
	}

	@Test
	public void convertCursorWithMoreThanOne() {
		Cursor cursor = new Cursor();
		cursor.add("title", "title value");
		cursor.add("description", "description value");
		cursor.add("cost", 1);
		cursor.next();
		cursor.add("title", "title value2");
		cursor.add("description", "description value2");
		cursor.add("cost", 12);
		cursor.next();

		String result = converter.convert(cursor, getSampleEntity());

		assertEquals(
				"[{\"title\":\"title value\",\"description\":\"description value\",\"cost\":1},"
						+ "{\"title\":\"title value2\",\"description\":\"description value2\",\"cost\":12}]",
				result);
	}

	@Test
	public void convertCursorWithoutObject() {
		String result = converter.convert(new Cursor(), getSampleEntity());

		assertEquals("[]", result);
	}

	private Entity getSampleEntity() {
		Entity entity = new Entity("org.ls.a2engine.model.Example");
		entity.add(new Property.Builder("title").type(Introspector.Type.STRING)
				.build());
		entity.add(new Property.Builder("description").type(
				Introspector.Type.STRING).build());
		entity.add(new Property.Builder("cost").type(Introspector.Type.INTEGER)
				.build());
		return entity;
	}
	
}
