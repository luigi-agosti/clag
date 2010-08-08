package novoda.clag.converter.json;

import java.util.Map;

import novoda.clag.converter.Converter;
import novoda.clag.model.Cursor;
import novoda.clag.model.Entity;
import novoda.clag.model.Property;

import com.google.appengine.repackaged.org.json.JSONStringer;

/**
 * @author luigi.agosti
 */
public class JsonConverter implements Converter {
	
	private static final String TABLE = "table";

	private static final String COLUMNS = "columns";
	
	private static final String NAME = "name";

	private static final String TYPE = "type";

	@Override
	public String convert(Entity entity) {
		try {
			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.object().key(TABLE).value(entity.getName()).key(COLUMNS).array();
			for(Property md : entity.getMetaDatas()) {
				jsonStringer.object().key(NAME).value(md.getName())
					.key(TYPE).value(md.getType()).endObject();
			}
			return jsonStringer.endArray().endObject().toString();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String convert(Cursor cursor, Entity entity) {
		try {
			JSONStringer jsonStringer = new JSONStringer();
			jsonStringer.array();
			for(Map<String, Object> row : cursor.getRows()) {
				jsonStringer.object();
				for(String key : row.keySet()){
					jsonStringer.key(key).value(row.get(key));
				}
				jsonStringer.endObject();
			}
			return jsonStringer.endArray().toString();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
