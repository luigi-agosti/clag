
package novoda.clag.introspector;

import novoda.clag.model.MetaEntity;

/**
 * @author luigi.agosti
 */
public interface Introspector {

	interface Type {
	
		String STRING = "text";
	
		String INTEGER = "integer";
		
		String NULL = "null";
		
		String REAL = "real";
		
		String BLOB = "blob";
		
	}
	
//	public enum SQLiteType {
//	    NULL, INTEGER, REAL, TEXT, BLOB
//	}

	/**
	 * Return a map that contains the definition of columns and types
	 *  
	 * @return
	 */
	MetaEntity extractMetaEntity(@SuppressWarnings("unchecked") Class classToParse);

}
