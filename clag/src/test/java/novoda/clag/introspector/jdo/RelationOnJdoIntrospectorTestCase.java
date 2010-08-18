package novoda.clag.introspector.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import novoda.clag.introspector.Introspector;
import novoda.clag.introspector.jdo.sample.FacebookModel;
import novoda.clag.introspector.jdo.sample.Group;
import novoda.clag.introspector.jdo.sample.Page;
import novoda.clag.introspector.jdo.sample.Story;
import novoda.clag.model.MetaEntity;
import novoda.clag.model.MetaProperty;

import org.junit.Test;

/**
 * @author luigi.agosti
 */
public class RelationOnJdoIntrospectorTestCase {
	
	@Test
	public void shouldParentReturnNullIfDoesntHaveIt(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(FacebookModel.class);
		assertNotNull(entity);
		assertNull(entity.getParentProperty());
	}
	
	@Test
	public void shouldBeParentNotNull(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		
		assertNotNull(entity.getParentProperty());
		assertEquals("groupId", entity.getParentProperty());
		MetaProperty mp = entity.getMetaProperty("groupId");
		assertNotNull(mp);
		assertNotNull("Group", mp.getParent());
		assertNotNull(Introspector.Type.STRING, mp.getType());
	}
	
	@Test
	public void shouldHaveParentThrourghHieraecky(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Group.class);
		assertNotNull(entity);
		
		assertNotNull(entity.getParentProperty());
		assertEquals("pageId", entity.getParentProperty());
		MetaProperty mp = entity.getMetaProperty("pageId");
		assertNotNull(mp);
		assertNotNull("Page", mp.getParent());
		assertNotNull(Introspector.Type.INTEGER, mp.getType());
	}
	
	@Test
	public void shouldHaveParentAndChildsThrourghHieraecky(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Page.class);
		assertNotNull(entity);
		
		assertNotNull(entity.getParentProperty());
		assertEquals("parentKeyId", entity.getParentProperty());
		MetaProperty mp = entity.getMetaProperty("parentKeyId");
		assertNotNull(mp);
		assertNotNull("Page", mp.getParent());
		assertNotNull(Introspector.Type.INTEGER, mp.getType());
		
		assertNotNull(entity.getChildProperties());
		assertEquals(1, entity.getChildProperties().size());
		assertEquals("groupIds", entity.getChildProperties().get(0));
		mp = entity.getMetaProperty("groupIds");
		assertNotNull(mp);
		assertEquals("Group", mp.getChild());
		assertEquals(Introspector.Type.STRING, mp.getType());
		
	}
	
	
	@Test
	public void shouldChildrenNotNullEvenIfDoesntHave(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		assertNotNull(entity.getChildProperties());
		assertEquals(0, entity.getChildProperties().size());
	}
	
	@Test
	public void shouldHaveChildren(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Group.class);
		assertNotNull(entity);
		assertNotNull(entity.getChildProperties());
		assertEquals(0, entity.getChildProperties().size());
	}
	
}
