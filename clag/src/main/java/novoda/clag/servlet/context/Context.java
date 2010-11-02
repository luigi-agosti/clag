package novoda.clag.servlet.context;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import novoda.clag.converter.Converter;
import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
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
		String EMAIL = "account";
		String REMOTE_ID = "remoteId";
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
	
	ServiceInfo getServiceInfo();

	void setServiceInfo(ServiceInfo serviceInfo);

	Cursor getCursorFromRequest(MetaEntity me);

	Cursor getCursorFromJsonDataRequest(MetaEntity me) throws JsonParseException, JsonMappingException, IOException;
	
	String getData();

	String getRemoteId();

}
