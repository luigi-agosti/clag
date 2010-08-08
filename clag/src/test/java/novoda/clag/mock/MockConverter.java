package novoda.clag.mock;

import novoda.clag.converter.Converter;
import novoda.clag.model.Cursor;
import novoda.clag.model.Entity;

/**
 * @author luigi.agosti
 */
public class MockConverter implements Converter {

	@Override
	public String convert(Entity entity) {
		return "convert mds";
	}

	@Override
	public String convert(Cursor cursor, Entity entity) {
		return "convert cursor";
	}
	
}
