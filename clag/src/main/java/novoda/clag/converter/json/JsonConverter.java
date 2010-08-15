package novoda.clag.converter.json;

import java.util.Date;
import java.util.Map;

import novoda.clag.converter.Converter;
import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
import novoda.clag.model.MetaProperty;

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

	@Override
	public String convert(MetaEntity entity) {
		try {
			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key(TABLE).value(entity.getName()).key(COLUMNS).array();
			for(MetaProperty md : entity.getMetaProperties()) {
				jsonStringer.object().key(NAME).value(md.getName())
					.key(TYPE).value(md.getType());
				if(md.getIsKey()) {
					jsonStringer.key(KEY).value(KEY_VALUE);
				}
				jsonStringer.endObject();
			}
			return jsonStringer.endArray().endObject().toString();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String convert(Cursor cursor, MetaEntity entity) {
		try {
			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.array();
			for(Map<String, Object> row : cursor.getRows()) {
				jsonStringer.object();
				for(String key : row.keySet()){
					Object obj = row.get(key);
					if(obj instanceof Date) {
						jsonStringer.key(key).value(((Date)obj).getTime());
					} else {
						jsonStringer.key(key).value(row.get(key));
					}
				}
				jsonStringer.endObject();
			}
			return jsonStringer.endArray().toString();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
