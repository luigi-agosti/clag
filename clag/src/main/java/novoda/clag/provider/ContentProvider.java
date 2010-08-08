package novoda.clag.provider;

import novoda.clag.introspector.Introspector;
import novoda.clag.model.Cursor;
import novoda.clag.model.Entity;
import novoda.clag.util.Configurable;

/**
 * @author luigi.agosti
 */
public interface ContentProvider extends Configurable {
	
	void setIntrospector(Introspector introspector);

	Cursor query(String name, String[] projection, String selection,
			String[] selectionArgs, String sortOrder, Entity entity);
	
	Entity schema(String name);

	void add(Class<?> clazz);
}
