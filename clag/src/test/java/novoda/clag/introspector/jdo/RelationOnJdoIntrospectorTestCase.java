package novoda.clag.introspector.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import novoda.clag.introspector.jdo.sample.Group;
import novoda.clag.introspector.jdo.sample.Page;
import novoda.clag.introspector.jdo.sample.Story;
import novoda.clag.model.MetaEntity;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author luigi.agosti
 */
public class RelationOnJdoIntrospectorTestCase {
	
	@Test
	public void shouldParentReturnNullIfDoesntHaveIt(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Page.class);
		assertNotNull(entity);
		assertNull(entity.getParentProperty());
	}
	
	@Test
	public void shouldBeParentNotNull(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		
		assertNotNull(entity.getParentProperty());
		assertEquals("groupId", entity.getParentProperty());
		assertNotNull(entity.getMetaProperty("groupId"));
		assertNotNull("Group", entity.getMetaProperty("groupId").getParent());
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
