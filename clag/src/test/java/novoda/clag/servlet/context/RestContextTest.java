package novoda.clag.servlet.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import novoda.clag.model.Options;
import novoda.clag.servlet.context.Context.Parameter;
import novoda.clag.util.RequestMapBuilder;

import org.junit.Test;

public class RestContextTest {

	@Test
	public void shouldGetTheNameFromTheUrlNullWhenNotDefined() {
		RestContext rr = new RestContext();
		rr.setUri("/data/");

		assertNull(rr.getName());
	}

	@Test
	public void shouldGetTheNameFromTheUrl() {
		RestContext rr = new RestContext();
		rr.setUri("/data/Story");

		assertNotNull(rr.getName());
		assertEquals("Story", rr.getName());
	}

	@Test
	public void shouldGetTheNameFromTheUrlWithParameter() {
		RestContext rr = new RestContext();
		rr.setUri("/data/Story?schema");

		assertNotNull(rr.getName());
		assertEquals("Story", rr.getName());
	}

	@Test
	public void shouldGetTheSchemaEvenIfTheParameterIfNull() {
		RestContext rr = new RestContext(new RequestMapBuilder().add("schema")
				.build());

		assertNotNull(rr.isSchema());
		assertTrue(rr.isSchema());
	}

	@Test
	public void shouldGetTheSchemaEvenIfTheParameterIsTrue() {
		RestContext rr = new RestContext(new RequestMapBuilder().add("schema",
				"true").build());

		assertNotNull(rr.isSchema());
		assertTrue(rr.isSchema());
	}

	@Test
	public void shouldGetTheSchemaEvenIfTheParameterIsFalse() {
		RestContext rr = new RestContext(new RequestMapBuilder().add("schema",
				"false").build());

		assertNotNull(rr.isSchema());
		assertFalse(rr.isSchema());
	}

	@Test
	public void shouldGetTheQueryTrueByDefault() {
		RestContext rr = new RestContext(new RequestMapBuilder().build());

		assertNotNull(rr.isQuery());
		assertTrue(rr.isQuery());
	}

	@Test
	public void shouldGetTheQueryEvenIfTheParameterIfNull() {
		RestContext rr = new RestContext(new RequestMapBuilder().add("query")
				.build());

		assertNotNull(rr.isQuery());
		assertTrue(rr.isQuery());
	}

	@Test
	public void shouldGetTheQueryEvenIfTheParameterIsTrue() {
		RestContext rr = new RestContext(new RequestMapBuilder().add("query",
				"true").build());

		assertNotNull(rr.isQuery());
		assertTrue(rr.isQuery());
	}

	@Test
	public void shouldGetTheQueryEvenIfTheParameterIsFalse() {
		RestContext rr = new RestContext(new RequestMapBuilder().add("query",
				"false").build());

		assertNotNull(rr.isQuery());
		assertFalse(rr.isQuery());
	}

	@Test
	public void shouldGetDefaultFetchOptions() {
		RestContext rr = new RestContext(new RequestMapBuilder().build());

		Options o = rr.getFetchOptions();
		assertNotNull(o);
		assertEquals(Options.DEFAULT_LIMIT, o.getLimit());
		assertEquals(Options.DEFAULT_OFFSET, o.getOffset());
	}

	@Test
	public void shouldGetLimit() {
		RestContext rr = new RestContext(new RequestMapBuilder().add("limit",
				"10").build());

		Options o = rr.getFetchOptions();
		assertNotNull(o);
		assertEquals(10, o.getLimit());
	}

	@Test
	public void shouldGetOffset() {
		RestContext rr = new RestContext(new RequestMapBuilder().add("offset",
				"10").build());

		Options o = rr.getFetchOptions();
		assertNotNull(o);
		assertEquals(10, o.getOffset());
	}

	@Test
	public void shouldGetSelection() {
		RestContext rr = new RestContext(new RequestMapBuilder().add(
				Parameter.SELECTION, "id").build());
		rr.setUri("/data/Story");

		assertNotNull(rr.getSelection());
		assertEquals("id", rr.getSelection());
	}
	
	@Test
	public void shouldGetSelectionArgs() {
		RestContext rr = new RestContext(new RequestMapBuilder().add(
				Parameter.SELECTION_ARGS, "id").build());
		rr.setUri("/data/Story");

		assertNotNull(rr.getSelectionArgs());
		assertEquals("id", rr.getSelectionArgs()[0]);
	}
	
	@Test
	public void shouldGetSelectionArgsFromUri() {
		RestContext rr = new RestContext(new RequestMapBuilder().build());
		rr.setUri("/data/Story/1");

		assertNotNull(rr.getSelection());
		assertEquals("id=1", rr.getSelection());
	}
	
	@Test
	public void shouldGetComplexSelectionArgsFromUri() {
		RestContext rr = new RestContext(new RequestMapBuilder().build());
		rr.setUri("/data/Page/1/Story/1");

		assertNotNull(rr.getSelection());
		assertEquals("id=1,pageId=1", rr.getSelection());
	}
	
}
