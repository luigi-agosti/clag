package novoda.clag.introspector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import novoda.clag.introspector.annotation.IsHidden;
import novoda.clag.model.MetaEntity;

/**
 * @author luigi.agosti
 */
@SuppressWarnings("unchecked")
public abstract class AbstractIntrospector implements Introspector {
	
	private static Map<String, String> TYPE_MAP = new HashMap<String, String>();
    static {
            TYPE_MAP.put(String.class.getName(), Introspector.Type.STRING);
            TYPE_MAP.put(Integer.class.getName(), Introspector.Type.INTEGER);
            TYPE_MAP.put(Long.class.getName(), Introspector.Type.INTEGER);
            TYPE_MAP.put(Date.class.getName(), Introspector.Type.INTEGER);
            TYPE_MAP.put(List.class.getName(), Introspector.Type.STRING);
    }

    public static final String getType(Class<?> clazz) {
    	return TYPE_MAP.get(clazz.getName());
    }
	
	@Override
	public MetaEntity extractMetaEntity(Class classToParse) {
		if(classToParse == null) {
			return null;
		}
		MetaEntity me = analyseClass(classToParse);
		List<Class> classes = new ArrayList<Class>();
		getClasses(classToParse, classes);
		for(Field field : getFields(classes)) {
			if(field.getAnnotation(IsHidden.class) == null) {
				filterFields(field, me);
			}
		}
		return me;
	}
	
	@Override
	public void linking(List<MetaEntity> metaEntities) {
		
	}
	
	protected abstract void filterFields(Field field, MetaEntity mds);
	
	protected abstract MetaEntity analyseClass(Class clazz);
	
	protected void getClasses(Class clazz, List<Class> classes) {
		Class superClass = clazz.getSuperclass();
		if(superClass != null) {
			getClasses(superClass, classes);
		}
		classes.add(clazz);
	}
	
	protected List<Field> getFields(List<Class> classes) {
		List<Field> allFields = new ArrayList<Field>();
		for(Class clazz : classes) {
			allFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}
		return allFields;
	}
	
}
