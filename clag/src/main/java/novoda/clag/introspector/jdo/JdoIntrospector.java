package novoda.clag.introspector.jdo;

import java.lang.reflect.Field;

import javax.jdo.annotations.Persistent;

import novoda.clag.introspector.AbstractIntrospector;
import novoda.clag.model.Entity;

import org.apache.log4j.Logger;

/**
 * @author luigi.agosti
 */
public class JdoIntrospector extends AbstractIntrospector {
	
	private static final Logger logger = Logger.getLogger(JdoIntrospector.class);

	@Override
	protected void filterFields(Field field, Entity mds) {
		if(field.getAnnotation(Persistent.class) != null) {
			logger.debug("adding property to map : <" + field.getName() + "," + getType(field.getType().getName()) + ">");
			mds.add(field.getName(), getType(field.getType().getName()));
		}
	}
}
