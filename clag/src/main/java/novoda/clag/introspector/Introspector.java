
package novoda.clag.introspector;

import novoda.clag.model.Entity;

/**
 * @author luigi.agosti
 */
public interface Introspector {

	interface Type {
	
		String STRING = "text";
	
		String INTEGER = "integer";
		
	}

	/**
	 * Return a map that contains the definition of columns and types
	 *  
	 * @return
	 */
	Entity getMetaDataSet(@SuppressWarnings("unchecked") Class classToParse);

}
