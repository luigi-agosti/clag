package novoda.clag.converter;

import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;

/**
 * @author luigi.agosti
 */
public interface Converter {

	String convert(MetaEntity mds);

	String convert(Cursor cursor, MetaEntity mds);

}
