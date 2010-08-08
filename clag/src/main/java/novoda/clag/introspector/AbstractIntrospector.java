package novoda.clag.introspector;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.annotations.Persistent;

import novoda.clag.model.Entity;

import org.apache.log4j.Logger;

/**
 * @author luigi.agosti
 */
@SuppressWarnings("unchecked")
public abstract class AbstractIntrospector implements Introspector {

	private static final Logger logger = Logger.getLogger(AbstractIntrospector.class);
	
	private static Map<String, String> TYPE_MAP = new HashMap<String, String>();
    static {
            TYPE_MAP.put(String.class.getName(), Introspector.Type.STRING);
            TYPE_MAP.put(Integer.class.getName(), Introspector.Type.INTEGER);
    }
    
    public static final String getType(String name) {
    	return TYPE_MAP.get(name);
    }
	
	@Override
	public Entity getMetaDataSet(Class classToParse) {
		if(classToParse == null) {
			return null;
		}
		Entity mds = new Entity(classToParse.getName());
		List<Field> fields = Arrays.asList(classToParse.getDeclaredFields());
		for(Field field : fields) {
			if(field.getAnnotation(Persistent.class) != null) {
				logger.debug("adding property to map : <" + field.getName() + "," + getType(field.getType().getName()) + ">");
				mds.add(field.getName(), getType(field.getType().getName()));
			}
		}
		return mds;
	}
	
	protected abstract void filterFields(Field field, Entity mds);
	
}
