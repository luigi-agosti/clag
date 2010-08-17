package novoda.clag.converter.json;

import java.util.Date;
import java.util.Map;

import novoda.clag.converter.Converter;
import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
import novoda.clag.model.MetaProperty;
import novoda.clag.servlet.context.Context;
import novoda.clag.servlet.context.ServiceInfo;

import com.google.appengine.repackaged.org.json.JSONStringer;

/**
 * @author luigi.agosti
 */
public class JsonConverter implements Converter {

	private static final String TABLE = "name";

	private static final String COLUMNS = "columns";

	private static final String NAME = "name";

	private static final String TYPE = "type";

	private static final String KEY = "key";

	private static final String KEY_VALUE = "true";

	private static final String SCHEMA = "schema";
	
	private static final String SERVICES = "services";
	
	private static final String VERSION = "version";

	private static final String GET = "get";

	@Override
	public String convert(MetaEntity entity) {
		try {
			JSONStringer jsonStringer = new JSONStringer();
			converEntity(jsonStringer, entity);
			return jsonStringer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String convert(Cursor cursor, MetaEntity entity) {
		try {
			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.array();
			for (Map<String, Object> row : cursor.getRows()) {
				jsonStringer.object();
				for (String key : row.keySet()) {
					Object obj = row.get(key);
					if (obj instanceof Date) {
						jsonStringer.key(key).value(((Date) obj).getTime());
					} else {
						jsonStringer.key(key).value(row.get(key));
					}
				}
				jsonStringer.endObject();
			}
			return jsonStringer.endArray().toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String describe(Context context) {
		try {
			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object();
			if(context != null) {
				ServiceInfo si = context.getServiceInfo();
				if(si != null) {
					jsonStringer.key(NAME).value(si.getName()).
						key(VERSION).value(si.getVersion());
				}
				
				if(context.getProvider() != null) {
					jsonStringer.key(SERVICES).array().object();				
					jsonStringer.key(GET).array();
					for(MetaEntity entity: context.getProvider().schema()) {
						jsonStringer.value(entity.getName());
					}
					jsonStringer.endArray();
					
					jsonStringer.endObject().endArray();
				
					jsonStringer.key(SCHEMA).array();
					for(MetaEntity entity: context.getProvider().schema()) {
						converEntity(jsonStringer, entity);
					}
					jsonStringer.endArray();
				}
			}
			return jsonStringer.endObject().toString();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void converEntity(JSONStringer jsonStringer, MetaEntity entity)
			throws Exception {
		jsonStringer.object().key(TABLE).value(entity.getName()).key(COLUMNS)
				.array();
		for (MetaProperty md : entity.getMetaProperties()) {
			jsonStringer.object().key(NAME).value(md.getName()).key(TYPE)
					.value(md.getType());
			if (md.getIsKey()) {
				jsonStringer.key(KEY).value(KEY_VALUE);
			}
			jsonStringer.endObject();
		}
		jsonStringer.endArray().endObject();
	}

}
