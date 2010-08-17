package novoda.clag.converter;

import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
import novoda.clag.servlet.context.Context;

/**
 * @author luigi.agosti
 */
public interface Converter {

	String convert(MetaEntity mds);

	String convert(Cursor cursor, MetaEntity mds);

	String describe(Context context);

}
