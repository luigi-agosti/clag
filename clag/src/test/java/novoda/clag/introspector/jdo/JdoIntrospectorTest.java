package novoda.clag.introspector.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import novoda.clag.introspector.Introspector;
import novoda.clag.introspector.jdo.sample.Story;
import novoda.clag.model.MetaEntity;
import novoda.clag.model.MetaProperty;

import org.junit.Test;

/**
 * @author luigi.agosti
 */
public class JdoIntrospectorTest {
	
	@Test
	public void shouldGetNullResult() {
		MetaEntity jdo = new JdoIntrospector().extractMetaEntity(null);
		assertNull(jdo);
	}
	
	@Test
	public void shouldTheMapHaveTheStringMembersThatAreDeclaredWithAnnotationPersistent(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		
		assertTrue(entity.contains("mediaHref"));
		assertEquals(Introspector.Type.STRING, entity.getMetaProperty("mediaHref").getType());
		assertTrue(entity.contains("title"));
		assertEquals(Introspector.Type.STRING, entity.getMetaProperty("title").getType());
		assertTrue(entity.contains("mediaImageHref"));
		assertEquals(Introspector.Type.STRING, entity.getMetaProperty("mediaImageHref").getType());
		assertTrue(entity.contains("caption"));
		assertEquals(Introspector.Type.STRING, entity.getMetaProperty("caption").getType());
		assertTrue(entity.contains("copy"));
		assertEquals(Introspector.Type.STRING, entity.getMetaProperty("copy").getType());
	}
	
	@Test
	public void shouldHidePropertiesMarkedWithIsHidden(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		
		assertFalse(entity.contains("groupId"));
	}

	@Test
	public void shouldMetaEntityGiveBackCorrectNameClass(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		assertNotNull(entity.getClassName());
		assertEquals(Story.class.getName(), entity.getClassName());
	}

	@Test
	public void shouldMetaEntityGiveBackCorrectName(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		assertNotNull(entity.getName());
		assertEquals(Story.class.getSimpleName(), entity.getName());
	}

	@Test
	public void shouldTheMapHaveTheKey(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		
		assertTrue(entity.contains("mediaHref"));
		MetaProperty mp = entity.getMetaProperty("mediaHref");
		assertEquals(Introspector.Type.STRING, mp.getType());
		assertFalse(mp.getIsKey());
		
		assertTrue(entity.contains("id"));
		mp = entity.getMetaProperty("id");
		assertEquals(Introspector.Type.INTEGER, mp.getType());
		assertTrue(mp.getIsKey());
	}
	
}
