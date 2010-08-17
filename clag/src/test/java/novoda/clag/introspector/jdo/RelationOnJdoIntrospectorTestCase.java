package novoda.clag.introspector.jdo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import novoda.clag.introspector.jdo.sample.Page;
import novoda.clag.introspector.jdo.sample.Story;
import novoda.clag.model.MetaEntity;

import org.junit.Test;

/**
 * @author luigi.agosti
 */
public class RelationOnJdoIntrospectorTestCase {
	
	@Test
	public void shouldParentReturnNullIfDoesntHaveIt(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Page.class);
		assertNotNull(entity);
		assertNull(entity.getParent());
	}
	
	@Test
	public void shouldParentNotNull(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		
		assertNotNull(entity.getParent());
	}
	
	@Test
	public void shouldChildrenNotNullEvenIfDoesntHave(){
		MetaEntity entity = new JdoIntrospector().extractMetaEntity(Story.class);
		assertNotNull(entity);
		assertNotNull(entity.getChildren());
		assertEquals(0, entity.getChildren().size());
	}
	
}
