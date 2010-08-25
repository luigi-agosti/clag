package novoda.clag.converter;

import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
import novoda.clag.servlet.context.Context;

/**
 * @author luigi.agosti
 */
public interface Converter {

	String convert(MetaEntity mds, Context context);

	String convert(Cursor cursor, MetaEntity mds, Context context);

	String describe(Context context);

}
