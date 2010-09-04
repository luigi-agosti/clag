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
import novoda.clag.model.MetaProperty;

/**
 * @author luigi.agosti
 */
@SuppressWarnings("unchecked")
public abstract class AbstractIntrospector implements Introspector {
	
	private static Map<String, String> TYPE_MAP = new HashMap<String, String>();
    static {
            TYPE_MAP.put(String.class.getName(), MetaEntity.Type.STRING);
            TYPE_MAP.put(Integer.class.getName(), MetaEntity.Type.INTEGER);
            TYPE_MAP.put(Long.class.getName(), MetaEntity.Type.INTEGER);
            TYPE_MAP.put(Double.class.getName(), MetaEntity.Type.STRING);
            TYPE_MAP.put(Date.class.getName(), MetaEntity.Type.INTEGER);
            TYPE_MAP.put(List.class.getName(), MetaEntity.Type.STRING);
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
	public void linking(Map<String, MetaEntity> metaEntities) {
		Map<String, List<MetaProperty>> relations = new HashMap<String, List<MetaProperty>>();
		for(String key : metaEntities.keySet()) {
			MetaEntity me = metaEntities.get(key);
			for(String rKey : me.getRelations()) {
				MetaProperty mp = me.getMetaProperty(rKey);
				if(relations.containsKey(mp.getFrom())) {
					relations.get(mp.getFrom()).add(mp);
				} else {
					List<MetaProperty> rs = new ArrayList<MetaProperty>();
					rs.add(mp);
					relations.put(mp.getFrom(), rs);
				}
			}			
		}
		
		for(String key : metaEntities.keySet()) {
			MetaEntity me = metaEntities.get(key);
			me.resetRelations();
		}
		
		for(String key : relations.keySet()) {
			if(metaEntities.containsKey(key)) {
				MetaEntity me = metaEntities.get(key);
				for(MetaProperty mp : relations.get(key)) {
					me.addRelation(mp.getName(), mp.getOwner(), mp.getFrom(), mp.getType(), mp.isInclude());
				}
			}
		}
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
