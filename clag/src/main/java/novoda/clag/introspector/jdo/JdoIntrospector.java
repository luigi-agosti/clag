package novoda.clag.introspector.jdo;

import java.lang.reflect.Field;

import javax.jdo.annotations.PrimaryKey;

import novoda.clag.introspector.AbstractIntrospector;
import novoda.clag.introspector.annotation.Clag;
import novoda.clag.introspector.annotation.IsKey;
import novoda.clag.model.MetaEntity;
import novoda.clag.model.MetaProperty;

import org.apache.log4j.Logger;

/**
 * @author luigi.agosti
 */
public class JdoIntrospector extends AbstractIntrospector {

	private static final Logger logger = Logger
			.getLogger(JdoIntrospector.class);

	@Override
	protected void filterFields(Field field, MetaEntity mds) {
		if (field.getAnnotation(Clag.class) != null) {
			logger.debug("Adding field key : " + field.getName());
			Clag c = (Clag) field.getAnnotation(Clag.class);
			mds.add(new MetaProperty.Builder(field.getName()).isKey(c.isKey())
					.unique(c.unique()).onConflictPolicy(c.onConflictpolicy())
					.type(getType(field.getType())).build());
		} else if (field.getAnnotation(PrimaryKey.class) != null) {
			logger.debug("Adding field key : " + field.getName());
			mds.addKey(field.getName(), getType(field.getType()));
		} else if (field.getAnnotation(IsKey.class) != null) {
			logger.debug("Adding key for relation : " + field.getName());
			IsKey relation = (IsKey) field.getAnnotation(IsKey.class);
			mds.addRelation(field.getName(), mds.getName(), relation.from(),
					getType(field.getType()), relation.include());
		} else {
			logger.debug("Adding field : " + field.getName());
			mds.add(field.getName(), getType(field.getType()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected MetaEntity analyseClass(Class clazz) {
		// put here the code if in the future will be necessary to use class
		// annotation
		return new MetaEntity(clazz.getName(), clazz.getSimpleName());
	}
}
