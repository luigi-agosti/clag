package novoda.clag.provider;

import java.util.Collection;

import novoda.clag.introspector.Introspector;
import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
import novoda.clag.model.Options;
import novoda.clag.util.Configurable;

/**
 * @author luigi.agosti
 */
public interface Provider extends Configurable {

	void setIntrospector(Introspector introspector);

	Cursor query(String name, String[] projection, String selection,
			String[] selectionArgs, String sortOrder, MetaEntity entity);

	Cursor query(String name, String[] projection, String selection,
			String[] selectionArgs, String sortOrder, MetaEntity entity,
			Options dataLimitation);

	Cursor query(String name, String[] projection, String selection,
			String[] selectionArgs, String sortOrder);

	Cursor query(String name, String[] projection, String selection,
			String[] selectionArgs, String sortOrder,
			Options dataLimitation);
	
	Cursor insert(String name, Cursor values, MetaEntity entity);

	MetaEntity schema(String name);
	
	Collection<MetaEntity> schema();

	void add(Class<?> clazz);

	void add(MetaEntity entity);

	void linkMetaEntities();

	void delete(String name, String remoteId, MetaEntity mds);
	
}
