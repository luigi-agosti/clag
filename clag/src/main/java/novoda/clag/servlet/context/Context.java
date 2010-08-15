package novoda.clag.servlet.context;

import javax.servlet.http.HttpServletRequest;

import novoda.clag.converter.Converter;
import novoda.clag.model.Options;
import novoda.clag.provider.Provider;


public interface Context {
	
	interface Parameter {
		String QUERY = "query";
		String SCHEMA = "schema";
		String NAME = "name";
		String PROJECTION = "projection";
		String SELECTION = "selection";
		String SELECTION_ARGS = "selectionArgs";
		String SORT_ORDER = "sortOrder";
		String LIMIT = "limit";
		String OFFSET = "offset";
	}
	
	String getName();

	boolean isQuery();

	boolean isSchema();
	
	Converter getConverter();
	
	void setConverter(Converter converter);
	
	Provider getProvider();
	
	void setProvider(Provider provider);

	String getSortOrder();

	String[] getSelectionArgs();

	String getSelection();

	String[] getProjection();

	void setRequest(HttpServletRequest req);
	
	Options getFetchOptions();

}
