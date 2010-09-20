package novoda.clag.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Group of metadata information can represent the table.
 * @author luigi.agosti
 *
 */
public class MetaEntity {
	
	public enum OnConflictPolicy { 
		ROLLBACK, ABORT, FAIL, IGNORE, REPLACE, NOT_DEFINED 
	}
	
	public enum Type {
		STRING("text"),INTEGER("integer"),NULL("null"),REAL("real"),BLOB("blob");
		
		private String value;
		
		Type(String _value){
			value = _value; 
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
	private static Map<String, MetaEntity.Type> TYPE_MAP = new HashMap<String, MetaEntity.Type>();
    static {
        TYPE_MAP.put(String.class.getName(), MetaEntity.Type.STRING);
        TYPE_MAP.put(Integer.class.getName(), MetaEntity.Type.INTEGER);
        TYPE_MAP.put(Long.class.getName(), MetaEntity.Type.INTEGER);
        TYPE_MAP.put(Double.class.getName(), MetaEntity.Type.STRING);
        TYPE_MAP.put(Date.class.getName(), MetaEntity.Type.INTEGER);
        TYPE_MAP.put(List.class.getName(), MetaEntity.Type.STRING);
        TYPE_MAP.put(Double.class.getName(), MetaEntity.Type.REAL);
    }

    public static final String getType(Class<?> clazz) {
    	MetaEntity.Type value = TYPE_MAP.get(clazz.getName());
    	if(value != null) {
    		return value.getValue();
    	}
    	return null;
    }

	private Map<String, MetaProperty> mds = new HashMap<String, MetaProperty>();
	
	private String name;
	
	private String className;
	
	private String keyProperty;
	
	private String userIdPropertyName;
	
	private List<String> relations = new ArrayList<String>();
	
	public MetaEntity(String className, String name) {
		this.className = className;
		this.name = name;
	}

	@SuppressWarnings("unchecked")
	public MetaEntity(Class clazz) {
		this.className = clazz.getName();
		this.name = clazz.getSimpleName();
	}

	public void add(String name, MetaProperty property) {
		if(property.getKey()){
			setKeyProperty(name);
		} else if (property.isRelation()) {
			relations.add(name);
		}
		if(mds.containsKey(name)) {
			MetaProperty oldProperty = mds.get(name);
			property.setParent(oldProperty.getParent());
			property.setChild(oldProperty.getChild());
		}
		mds.put(name, property);
	}

	public void add(MetaProperty property) {
		add(property.getName(), property); 
		if(property.getUserId()) {
			userIdPropertyName = property.getName();
		}
	}

	public void add(String name, Class<?> clazz) {
		add(name, new MetaProperty.Builder(name).clazz(clazz).build()); 
	}
	
	public void addParent(String parent, String property) {
		add(new MetaProperty.Builder(property).parent(parent).build());
	}
	
	public void addChild(String child, String property) {
		add(new MetaProperty.Builder(property).child(child).build());
	}
	
	public void addKey(String name, Class<?> clazz) {
		add(new MetaProperty.Builder(name).clazz(clazz).key(true).build());
	}
	
	public void addRelation(String through, String owner, String from, Class<?> clazz, boolean include) {
		add(new MetaProperty.Builder(through).owner(owner).clazz(clazz).isRelation(true, from, include).build());
	}
	
	public Collection<MetaProperty> getMetaProperties(){
		return mds.values();
	}

	public MetaProperty getMetaProperty(String name){
		return mds.get(name);
	}

	public boolean contains(String name) {
		return mds.containsKey(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public Set<String> getPropertyNames() {
		return mds.keySet();
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	private void setKeyProperty(String keyProperty) {
		this.keyProperty = keyProperty;
	}

	public String getKeyProperty() {
		return keyProperty;
	}

	public List<String> getRelations() {
		return relations;
	}

	public void resetRelations() {
		relations = new ArrayList<String>(); 
	}

	public String getUserIdPropertyName() {
		return userIdPropertyName;
	}

	public void setUserIdPropertyName(String userIdPropertyName) {
		this.userIdPropertyName = userIdPropertyName;
	}

}