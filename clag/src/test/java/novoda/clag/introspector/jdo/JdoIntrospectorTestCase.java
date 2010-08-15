package novoda.clag.introspector.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import novoda.clag.introspector.Introspector;
import novoda.clag.introspector.jdo.sample.Story;
import novoda.clag.model.Entity;

import org.junit.Test;

/**
 * @author luigi.agosti
 */
public class JdoIntrospectorTestCase {
	
	@Test
	public void shouldGetNullResult() {
		Entity jdo = new JdoIntrospector().extractMetaEntity(null);
		assertNull(jdo);
	}
	
	@Test
	public void shouldTheMapHaveTheStringMembersThatAreDeclaredWithAnnotationPersistent(){
		Entity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		
		assertTrue(entity.contains("mediaHref"));
		assertEquals(Introspector.Type.STRING, entity.getMetaData("mediaHref").getType());
		assertTrue(entity.contains("groupId"));
		assertEquals(Introspector.Type.STRING, entity.getMetaData("groupId").getType());
		assertTrue(entity.contains("title"));
		assertEquals(Introspector.Type.STRING, entity.getMetaData("title").getType());
		assertTrue(entity.contains("mediaImageHref"));
		assertEquals(Introspector.Type.STRING, entity.getMetaData("mediaImageHref").getType());
		assertTrue(entity.contains("caption"));
		assertEquals(Introspector.Type.STRING, entity.getMetaData("caption").getType());
		assertTrue(entity.contains("copy"));
		assertEquals(Introspector.Type.STRING, entity.getMetaData("copy").getType());
		
	}

	@Test
	public void shouldMetaEntityGiveBackCorrectNameClass(){
		Entity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		assertNotNull(entity.getClassName());
		assertEquals(Story.class.getName(), entity.getClassName());
	}

	@Test
	public void shouldMetaEntityGiveBackCorrectName(){
		Entity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		assertNotNull(entity.getName());
		assertEquals(Story.class.getSimpleName(), entity.getName());
	}

	@Test
	public void shouldTheMapHaveTheKey(){
		Entity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		
		assertTrue(entity.contains("mediaHref"));
		assertEquals(Introspector.Type.STRING, entity.getMetaData("mediaHref").getType());
		assertFalse(entity.getMetaData("mediaHref").getIsKey());
		
		assertTrue(entity.contains("id"));
		assertEquals(Introspector.Type.INTEGER, entity.getMetaData("id").getType());
		assertTrue(entity.getMetaData("id").getIsKey());
	}
}
