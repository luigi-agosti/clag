package novoda.clag.converter;

import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
import novoda.clag.servlet.context.Context;

/**
 * @author luigi.agosti
 */
public interface Converter {

	/**
	 * Convert a entity in a format readable for the user.
	 * @param mds
	 * @param context
	 * @return
	 */
	String convert(MetaEntity mds, Context context);

	/**
	 * A list of result organized in a cursor in a format readable for the user.
	 * @param cursor
	 * @param mds
	 * @param context
	 * @return
	 */
	String convert(Cursor cursor, MetaEntity mds, Context context);

	/**
	 * Return the all description of the schema.
	 * @param context
	 * @return
	 */
	String describe(Context context);

}
