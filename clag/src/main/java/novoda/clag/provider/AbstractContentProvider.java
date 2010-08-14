package novoda.clag.provider;

import java.util.HashMap;
import java.util.Map;

import novoda.clag.introspector.Introspector;
import novoda.clag.model.Entity;

import org.apache.log4j.Logger;

/**
 * @author luigi.agosti
 */
public abstract class AbstractContentProvider implements ContentProvider {
	
	protected static final Logger logger = Logger.getLogger(AbstractContentProvider.class);

	protected Map<String, Entity> entities = new HashMap<String, Entity>();

	protected Introspector introspector;
	
	@Override
	public void isConfigured() {
		logger.debug("Checking configuration");
		if(introspector == null) {
			throw new RuntimeException("Introspector has not been set in the content provider");
		} else {
			logger.debug("Introspector is set.");
		}
		if(entities.isEmpty()) {
			throw new RuntimeException("No Entity has been set in the content provider");			
		} else {
			for(String entityKey : entities.keySet()) {
				logger.debug("Entities are : " + entityKey);
			}
		}
	}
	
	@Override
	public void setIntrospector(Introspector introspector) {
		this.introspector = introspector;
	}
	
	@Override
	public void add(Class<?> clazz) {
		Entity entity = introspector.getMetaDataSet(clazz);
		if(entity != null) {
			logger.debug("Adding Entity : " + clazz.getSimpleName() + "," + clazz);
			entities.put(clazz.getSimpleName(), entity);
		} else {
			throw new RuntimeException("Faild to getThe entity description out of the class " + clazz);
		}
	}
	
	@Override
	public Entity schema(String name) {
		if(!entities.containsKey(name)) {
			logger.warn("Schema for entity name : " + name + " CANNOT be found!");
			return null;
		}
		return entities.get(name);
	}
	
}
