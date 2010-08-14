package novoda.clag.servlet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import novoda.clag.util.RequestWrapper;

/**
 * @author luigi.agosti
 */
public class RequestReader extends RequestWrapper {

	interface Parameter {
		String QUERY = "query";
		String SCHEMA = "schema";
		String NAME = "name";
		String PROJECTION = "projection";
		String SELECTION = "selection";
		String SELECTION_ARGS = "selectionArgs";
		String SORT_ORDER = "sortOrder";
	}
	
	public RequestReader() {
	}
	
	public RequestReader(Map<String, String[]> request) {
		super(request);
	}
	
	public RequestReader(HttpServletRequest request) {
		super(request);
	}

	public boolean isQuery() {
		return getParameterAsBoolean(Parameter.QUERY, false);
	}

	public boolean isSchema() {
		boolean contains = getParameterAsBooleanIfContained(Parameter.SCHEMA);
		if(contains) {
			return contains;
		} else {
			return getParameterAsBoolean(Parameter.SCHEMA, false);
		}
	}

	public String getName() {
		String uri = getUri();
		if(uri != null) {
			int index = uri.lastIndexOf("/");
			int terminalIndex = uri.lastIndexOf("?");
			if(index > 0 && index + 1 < uri.length()) {
				if(terminalIndex>0) {
					return uri.substring(index + 1, terminalIndex);
				}
				return uri.substring(index + 1);
			}
		}
		return null;
	}

	public String getSortOrder() {
		return getParameterAsString(Parameter.SORT_ORDER);
	}

	public String[] getSelectionArgs() {
		return getParameterAsStringArray(Parameter.SELECTION_ARGS);
	}

	public String getSelection() {
		return getParameterAsString(Parameter.SELECTION_ARGS);
	}

	public String[] getProjection() {
		return getParameterAsStringArray(Parameter.PROJECTION);
	}
	
	//TO PROMOTE
	public String[] getParameterAsStringArray(String parameterName) {
		List<String> strings = getParameterAsStrings(parameterName);
		if(strings == null) {
			return null;
		}
		return strings.toArray(new String[]{});
	}

}
