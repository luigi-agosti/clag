package novoda.clag.servlet.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import novoda.clag.converter.Converter;
import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
import novoda.clag.model.Options;
import novoda.clag.provider.Provider;
import novoda.clag.util.RequestWrapper;

/**
 * @author luigi.agosti
 */
public class RestContext extends RequestWrapper implements Context {
	
	private static final String SEPARATOR = "/";
	
	private static final String QUESTION_MARK = "?";
	
	private Provider provider;
	
	private Converter converter;
	
	private ServiceInfo serviceInfo;
	
	public RestContext() {
	}
	
	public RestContext(Map<String, String[]> request) {
		super(request);
	}
	
	public RestContext(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public void setRequest(HttpServletRequest request) {
		super.setRequest(request);
	}
	
	@Override
	public void setUri(String uri) {
		super.setUri(uri);
	}

	@Override
	public boolean isQuery() {
		return getParameterAsBooleanIfContained(Parameter.QUERY, true);
	}

	@Override
	public boolean isSchema() {
		return getParameterAsBooleanIfContained(Parameter.SCHEMA, false);
	}
	
	@Override
	public String getName() {
		String uri = getUri();
		if(uri != null) {
			int index = uri.lastIndexOf(SEPARATOR);
			int terminalIndex = uri.lastIndexOf(QUESTION_MARK);
			if(index > 0 && index + 1 < uri.length()) {
				if(terminalIndex>0) {
					return uri.substring(index + 1, terminalIndex);
				}
				return uri.substring(index + 1);
			}
		}
		return null;
	}

	@Override
	public String getSortOrder() {
		return getParameterAsString(Parameter.SORT_ORDER);
	}

	@Override
	public String[] getSelectionArgs() {
		return getParameterAsStringArray(Parameter.SELECTION_ARGS);
	}

	@Override
	public String getSelection() {
		return getParameterAsString(Parameter.SELECTION);
	}

	@Override
	public String[] getProjection() {
		return getParameterAsStringArray(Parameter.PROJECTION);
	}

	@Override
	public Converter getConverter() {
		return converter;
	}

	@Override
	public Provider getProvider() {
		return provider;
	}

	@Override
	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	@Override
	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	@Override
	public Options getFetchOptions() {
		Options dl = new Options();
		dl.setLimit(getParameterAsInt(Parameter.LIMIT, Options.DEFAULT_LIMIT));
		dl.setOffset(getParameterAsInt(Parameter.OFFSET, Options.DEFAULT_OFFSET));
		return dl;
	}

	@Override
	public void setServiceInfo(ServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	@Override
	public ServiceInfo getServiceInfo() {
		return serviceInfo;
	}
	
	@Override
	public Cursor getCursorFromRequest(MetaEntity me) {
		return new Cursor(me.getName(), getEntity(me));
	}

	public Map<String, Object> getEntity(MetaEntity me) {
		Map<String, Object> map = new HashMap<String, Object>();
		for(String key : me.getPropertyNames()) {
			String value = getParameterAsString(key);
			if(value != null){
				map.put(key, value);
			}
		}
		return map;
	}

}
