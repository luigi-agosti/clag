package novoda.clag.servlet.context;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import novoda.clag.converter.Converter;
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
	
	public RestContext() {
	}
	
	public RestContext(Map<String, String[]> request) {
		super(request);
	}
	
	public RestContext(HttpServletRequest request) {
		super(request);
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
		return getParameterAsString(Parameter.SELECTION_ARGS);
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

}