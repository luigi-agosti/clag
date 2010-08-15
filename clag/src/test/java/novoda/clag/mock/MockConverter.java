package novoda.clag.mock;

import novoda.clag.converter.Converter;
import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;

/**
 * @author luigi.agosti
 */
public class MockConverter implements Converter {

	@Override
	public String convert(MetaEntity entity) {
		return "convert mds";
	}

	@Override
	public String convert(Cursor cursor, MetaEntity entity) {
		return "convert cursor";
	}
	
}
