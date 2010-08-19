package novoda.clag.introspector.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

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
		assertEquals(Introspector.Type.STRING, entity.getMetaProperty("mediaHref").getType());
		assertFalse(entity.getMetaProperty("mediaHref").getIsKey());
		
		assertTrue(entity.contains("id"));
		assertEquals(Introspector.Type.INTEGER, entity.getMetaProperty("id").getType());
		assertTrue(entity.getMetaProperty("id").getIsKey());
	}
	
	@Test
	public void shouldDuplicateInformationAboutRelationshipWithTheLinkage(){
		//a child of b   -> linker as to reconstruct that b is parent of a with a property name that
		//is constructed 
		MetaEntity e1 = new MetaEntity("A", "a");
		e1.addParent("B", "B_id");
		MetaEntity e2 = new MetaEntity("B", "b");
		
		new JdoIntrospector().linking(Arrays.asList(e1, e2));
		
		assertEquals(1, e2.getChildProperties().size());
		assertEquals("A_id", e2.getChildProperties().get(0));
		MetaProperty mp = e2.getMetaProperty("A_id");
		assertNotNull(mp);
		assertEquals("A", mp.getChild());
	}
	
	@Test(expected = RuntimeException.class)
	public void shouldFailIfAnEntityRequiredByTheLinkageIsMissing() {
		MetaEntity e1 = new MetaEntity("A", "a");
		e1.addParent("B", "B_id");
		
		new JdoIntrospector().linking(Arrays.asList(e1));
	}
	
}
