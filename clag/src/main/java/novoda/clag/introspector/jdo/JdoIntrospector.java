package novoda.clag.introspector.jdo;

import java.lang.reflect.Field;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import novoda.clag.introspector.AbstractIntrospector;
import novoda.clag.introspector.annotation.IsChild;
import novoda.clag.introspector.annotation.IsParent;
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
				mds.addKey(field.getName(), getType(field.getType()));
			} else {
				logger.debug("Adding field : " + field.getName());
				mds.add(field.getName(), getType(field.getType()));				
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected MetaEntity analyseClass(Class clazz) {
		MetaEntity mds = new MetaEntity(clazz.getName(), clazz.getSimpleName());
		if(clazz.isAnnotationPresent(IsChild.class)) {
			IsChild a = (IsChild)clazz.getAnnotation(IsChild.class);
			String parent = a.of();
			String property = a.through();
			mds.addParent(parent, property);
		}
		if(clazz.isAnnotationPresent(IsParent.class)) {
			IsParent a = (IsParent)clazz.getAnnotation(IsParent.class);
			String child = a.of();
			String property = a.through();
			mds.addChild(child, property);
		}
		return mds;
	}
}
