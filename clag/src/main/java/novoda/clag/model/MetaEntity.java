package novoda.clag.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Group of metadata information can represent the table.
 * @author luigi.agosti
 *
 */
public class MetaEntity {
	
	private static final Logger logger = Logger.getLogger(MetaEntity.class);
	
	public enum OnConflictPolicy { ROLLBACK, ABORT, FAIL, IGNORE, REPLACE, NOT_DEFINED }
	
	public interface Type {
		
		String STRING = "text";
	
		String INTEGER = "integer";
		
		String NULL = "null";
		
		String REAL = "real";
		
		String BLOB = "blob";
		
	}

	private Map<String, MetaProperty> mds = new HashMap<String, MetaProperty>();
	
	private String name;
	
	private String className;
	
	private String keyProperty;
	
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
		if(property.getIsKey()){
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
	}

	public void add(String name, String type) {
		add(name, new MetaProperty.Builder(name).type(type).build()); 
	}
	
	public void addParent(String parent, String property) {
		add(new MetaProperty.Builder(property).parent(parent).build());
	}
	
	public void addChild(String child, String property) {
		add(new MetaProperty.Builder(property).child(child).build());
	}
	
	public void addKey(String name, String type) {
		add(new MetaProperty.Builder(name).type(type).isKey(true).build());
	}
	
	public void addRelation(String through, String owner, String from, String type, boolean include) {
		add(new MetaProperty.Builder(through).owner(owner).type(type).isRelation(true, from, include).build());
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
	
	public void dump() {
		for(MetaProperty md : mds.values()) {
			logger.debug(md);
		}
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

}