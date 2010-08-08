package novoda.clag.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Group of metadata information can represent the table.
 * @author luigi.agosti
 *
 */
public class Entity {
	
	private static final Logger logger = Logger.getLogger(Entity.class);

	private Map<String, Property> mds = new HashMap<String, Property>();
	
	private String name;
	
	public Entity(String name) {
		this.name = name;
	}

	public void add(String name, Property metaData) {
		mds.put(name, metaData); 
	}

	public void add(Property metaData) {
		mds.put(metaData.getName(), metaData); 
	}

	public void add(String name, String type) {
		mds.put(name, new Property.Builder(name).type(type).build()); 
	}
	
	public Collection<Property> getMetaDatas(){
		return mds.values();
	}

	public Property getMetaData(String name){
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
		for(Property md : mds.values()) {
			logger.debug(md);
		}
	}
}