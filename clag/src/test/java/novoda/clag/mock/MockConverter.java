package novoda.clag.mock;

import novoda.clag.converter.Converter;
import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
import novoda.clag.servlet.context.Context;

/**
 * @author luigi.agosti
 */
public class MockConverter implements Converter {

	@Override
	public String convert(MetaEntity entity, Context context) {
		return "schema";
	}

	@Override
	public String convert(Cursor cursor, MetaEntity entity, Context context) {
		return "query";
	}

	@Override
	public String describe(Context context) {
		return "describe";
	}
	
}
