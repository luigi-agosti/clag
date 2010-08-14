package novoda.clag.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import novoda.clag.servlet.RequestReader;

import org.junit.Test;

public class RequestReaderTest {
	
	@Test
	public void shouldGetTheNameFromTheUrlNullWhenNotDefined() {
	    
		RequestReader rr = new RequestReader();
		rr.setUri("/data/");
		
		assertNull(rr.getName());	
	}
	
	@Test
	public void shouldGetTheNameFromTheUrl() {
	    
		RequestReader rr = new RequestReader();
		rr.setUri("/data/Story");
		
		assertNotNull(rr.getName());
		assertEquals("Story", rr.getName());
	}
	
	@Test
	public void shouldGetTheNameFromTheUrlWithParameter() {
	    
		RequestReader rr = new RequestReader();
		rr.setUri("/data/Story?schema");
		
		assertNotNull(rr.getName());
		assertEquals("Story", rr.getName());
	}
	
	@Test
	public void shouldGetTheSchemaIfNotTrue() {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("schema", null);
		
		RequestReader rr = new RequestReader(map);
		
		assertNotNull(rr.isSchema());
		assertTrue(rr.isSchema());
	}

}
