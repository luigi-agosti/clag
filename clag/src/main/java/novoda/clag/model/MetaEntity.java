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

	private Map<String, MetaProperty> mds = new HashMap<String, MetaProperty>();
	
	private String name;
	
	private String className;
	
	private String keyProperty;
	
	private String parentProperty;
	
	private List<String> childProperties = new ArrayList<String>();
	
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
			setKeyProperty(property.getName());
		}
		if(property.isParent()) {
			setParentProperty(property.getName());
		}
		if(property.isChild()) {
			addChildProperty(property.getName());
		}
		if(mds.containsKey(name)) {
			MetaProperty oldProperty = mds.get(name);
			property.setParent(oldProperty.getParent());
			property.setChild(oldProperty.getChild());
		}
		mds.put(name, property);
	}

	private void addChildProperty(String childProperty) {
		childProperties.add(childProperty);
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
	
	public Set<String> getKeys() {
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

	public List<String> getChildProperties() {
		return childProperties;
	}

	public String getParentProperty() {
		return parentProperty;
	}
	
	public void setParentProperty(String parentProperty) {
		this.parentProperty = parentProperty;
	}

}