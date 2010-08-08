package novoda.clag.converter;

import novoda.clag.model.Cursor;
import novoda.clag.model.Entity;

/**
 * @author luigi.agosti
 */
public interface Converter {

	String convert(Entity mds);

	String convert(Cursor cursor, Entity mds);

}
