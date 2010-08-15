package novoda.clag.introspector.jdo;

import java.lang.reflect.Field;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import novoda.clag.introspector.AbstractIntrospector;
import novoda.clag.model.MetaEntity;

import org.apache.log4j.Logger;

/**
 * @author luigi.agosti
 */
public class JdoIntrospector extends AbstractIntrospector {
	
	private static final Logger logger = Logger.getLogger(JdoIntrospector.class);

	@Override
	protected void filterFields(Field field, MetaEntity mds) {
		if(field.getAnnotation(Persistent.class) != null) {
			if(field.getAnnotation(PrimaryKey.class) != null) {
				logger.debug("Adding field key : " + field.getName());
				mds.addKey(field.getName(), getType(field.getType().getName()));
			} else {
				logger.debug("Adding field : " + field.getName());
				mds.add(field.getName(), getType(field.getType().getName()));				
			}
		}
	}
}
