package novoda.clag.introspector;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import novoda.clag.introspector.jdo.sample.Story;
import novoda.clag.model.MetaEntity;

@SuppressWarnings("unchecked")
public class AbstractIntrospectorTest {
	
	@Test
	public void shouldGetAllTheClassesOfTheHierarchy() {
		AbstractIntrospector ai = new AbstractIntrospector() {
			@Override
			protected void filterFields(Field field, MetaEntity mds) {
			}
		};
		
		List<Class> classes = new ArrayList<Class>();
		ai.getClasses(Story.class, classes); 
		assertEquals(4, classes.size());
		
	}

}
