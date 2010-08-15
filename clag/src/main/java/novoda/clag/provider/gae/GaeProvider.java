package novoda.clag.provider.gae;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import novoda.clag.model.Cursor;
import novoda.clag.model.Options;
import novoda.clag.model.MetaEntity;
import novoda.clag.provider.AbstractProvider;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * @author luigi.agosti
 */
public class GaeProvider extends AbstractProvider {
	
	private static final String DOT = ".";

	protected static final GaeQueryHelper QC = new GaeQueryHelper();
	
	protected DatastoreService ds = DatastoreServiceFactory
			.getDatastoreService();

	@Override
	public Cursor query(String name, String[] projection, String selection,
			String[] selectionArgs, String sortOrder, MetaEntity entity,
			Options dataLimitation) {
		if (logger.isDebugEnabled()) {
			logger.debug("executing query : " + name);
			logger.debug("projection : " + projection);
			logger.debug("selection : " + selection);
			logger.debug("selectionArgs : " + selectionArgs);
			logger.debug("sortOrder : " + sortOrder);
		}
		Query q = buildQuery(name, projection, selection, selectionArgs,
				sortOrder);
		PreparedQuery pq = ds.prepare(q);
		Cursor cursor = new Cursor();
		Collection<String> propertiesMatchingProjection = getPropertyToLookup(
				projection, entity);
		String keyProperty = entity.getKeyProperty();
		for (Entity e : pq.asQueryResultIterable(getFetchOption(dataLimitation))) {
			for (String property : propertiesMatchingProjection) {
				if (property.equals(keyProperty)) {
					cursor.add(property, e.getKey().getId());
				} else {
					cursor.add(property, e.getProperty(property));
				}
			}
			cursor.next();
		}
		return cursor;
	}
	
	private FetchOptions getFetchOption(Options dl) {
		return FetchOptions.Builder.withLimit(dl.getLimit()).offset(dl.getOffset());
	}

	private Collection<String> getPropertyToLookup(String[] projection,
			MetaEntity entity) {
		Set<String> keys = entity.getKeys();
		if (projection == null) {
			return keys;
		}
		List<String> projectionMatches = new ArrayList<String>();
		for (String projectionProperty : projection) {
			if (keys.contains(projectionProperty)) {
				projectionMatches.add(projectionProperty);
			}
		}
		return projectionMatches;
	}

	private Query buildQuery(String name, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		Query q = new Query(getNameFromFullClassName(name));
		for (GaeQueryHelper.FilterHolder fh : QC.getFilters(selection,
				selectionArgs)) {
			q.addFilter(fh.propertyName, fh.operator, fh.value);
		}
		for (GaeQueryHelper.SortHolder sort : QC.getSorts(sortOrder)) {
			q.addSort(sort.propertyName, sort.direction);
		}
		return q;
	}

	private String getNameFromFullClassName(String name) {
		int index = name.lastIndexOf(DOT);
		if (index < 0) {
			return name;
		}
		return name.substring(index + 1);
	}
	
}
