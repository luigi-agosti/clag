package novoda.clag.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

public class RequestWrapper {
	
	private static final Logger logger = Logger.getLogger(RequestWrapper.class.getName());

	protected static final String DELIMITER = ",";

	private Map<String, String[]> parameterMap;

	private String uri;
	
	private HttpServletRequest request;

	public RequestWrapper() {

	}

	protected RequestWrapper(HttpServletRequest request) {
		setRequest(request);
	}
	
	@SuppressWarnings("unchecked")
	public void setRequest(HttpServletRequest request) {
		this.request = request;
		this.uri = request.getRequestURI();
		this.parameterMap = request.getParameterMap();
	}

	public RequestWrapper(Map<String, String[]> parameterMap) {
		this.parameterMap = parameterMap;
	}
	
	public String getData() {
		try {
			String data = convertStreamToString(request.getInputStream());
			logger.info("Data of the post is : " + data);
			return data;
		} catch (Exception e) {
			logger.severe("Problem with getting data from the request " + e.getMessage());
		}
		return "";
	}
	
	public String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				is.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	protected int getParameterAsInteger(String parameterName, int defaultValue) {
		int returnValue = defaultValue;
		String[] parameterValues = parameterMap.get(parameterName);
		if (parameterValues != null && parameterValues.length > 0) {
			returnValue = Integer.parseInt(parameterValues[0]);
		}
		return returnValue;
	}

	protected Long getParameterAsLong(String parameterName) {
		return getParameterAsLong(parameterName, null);
	}

	protected Long getParameterAsLong(String parameterName, Long defaultValue) {
		Long returnValue = defaultValue;
		String[] parameterValues = parameterMap.get(parameterName);
		if (parameterValues != null && parameterValues.length > 0) {
			try {
				returnValue = Long.valueOf(parameterValues[0]);
			} catch (Exception e) {
				return null;
			}
		}
		return returnValue;
	}

	protected int getParameterAsInt(String parameterName, int defaultValue) {
		int returnValue = defaultValue;
		String[] parameterValues = parameterMap.get(parameterName);
		if (parameterValues != null && parameterValues.length > 0) {
			try {
				returnValue = Integer.valueOf(parameterValues[0]);
			} catch (Exception e) {
				return -1;
			}
		}
		return returnValue;
	}

	protected boolean getParameterAsBoolean(String parameterName, boolean defaultValue) {
		boolean returnValue = defaultValue;
		String[] parameterValues = parameterMap.get(parameterName);
		if (parameterValues != null && parameterValues.length > 0) {
			if(parameterValues[0] != null && parameterValues[0].length() > 0) {
				returnValue = Boolean.valueOf(parameterValues[0]);
			}
		}
		return returnValue;
	}

	protected boolean getParameterAsBooleanIfContained(String parameterName, boolean defaultValue) {
		if(parameterMap.containsKey(parameterName)) {
			return getParameterAsBoolean(parameterName, true);
		}
		return getParameterAsBoolean(parameterName, defaultValue);
	}

	protected String getParameterAsString(String parameterName,
			String defaultValue) {
		String returnValue = defaultValue;
		String[] parameterValues = parameterMap.get(parameterName);
		if (parameterValues != null && parameterValues.length > 0) {
			returnValue = String.valueOf(parameterValues[0]);
		}
		return returnValue;
	}

	protected String getParameterAsString(String parameterName) {
		return getParameterAsString(parameterName, null);
	}

	protected List<String> getParameterAsStrings(String parameterName) {
		String value = getParameterAsString(parameterName);
		if (value == null || value.length() == 0) {
			return null;
		}
		return Arrays.asList(value.split(DELIMITER));
	}

	protected Date getParameterAsDate(String parameterName) {
		String value = getParameterAsString(parameterName);
		if (value == null || value.length() == 0) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(value);
			return date;
		} catch (ParseException pe) {
			throw new RuntimeException("Impossible to parse string " + value
					+ " to date");
		}
	}

	protected String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	protected String[] getParameterAsStringArray(String parameterName) {
		List<String> strings = getParameterAsStrings(parameterName);
		if (strings == null) {
			return null;
		}
		return strings.toArray(new String[] {});
	}

	protected String getHeader(String param) {
		return request.getHeader(param);
	}

}
