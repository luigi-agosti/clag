package novoda.clag.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestWrapper {
	
	protected static final String DELIMITER = ",";
	
	private Map<String, String[]> parameterMap;

	@SuppressWarnings("unchecked")
	public RequestWrapper(HttpServletRequest request) {
		this.parameterMap = request.getParameterMap();
	}

	public RequestWrapper(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }
    
    protected int getParameterAsInteger(String parameterName, int defaultValue) {
        int returnValue = defaultValue;
        String[] parameterValues = parameterMap.get(parameterName);
        if(parameterValues != null && parameterValues.length > 0) {
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
    	if(parameterValues != null && parameterValues.length > 0) {
    		try {
    			returnValue = Long.valueOf(parameterValues[0]);
    		} catch (Exception e) {
				return null;
			}
    	}
    	return returnValue;
    }
    
    protected boolean getParameterAsBoolean(String parameterName, boolean defaultValue) {
        boolean returnValue = defaultValue;
        String[] parameterValues = parameterMap.get(parameterName);
        if(parameterValues != null && parameterValues.length > 0) {
		    returnValue = Boolean.valueOf(parameterValues[0]);
	    }
        return returnValue;
    }
    
    protected String getParameterAsString(String parameterName, String defaultValue) {
        String returnValue = defaultValue;
        String[] parameterValues = parameterMap.get(parameterName);
        if(parameterValues != null && parameterValues.length > 0) {
		    returnValue = String.valueOf(parameterValues[0]);
	    }
        return returnValue;
    }
    
    protected String getParameterAsString(String parameterName) {
    	return getParameterAsString(parameterName, null);
    }

    protected List<String> getParameterAsStrings(String parameterName) {
    	String value = getParameterAsString(parameterName);
    	if(value == null || value.length() == 0) {
    		return null;
    	}
    	return Arrays.asList(value.split(DELIMITER));  
    }

    protected Date getParameterAsDate(String parameterName) {
    	String value = getParameterAsString(parameterName);
    	if(value == null || value.length() == 0) {
    		return null;
    	}
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    		Date date = sdf.parse(value);
    		return date;
    	} catch(ParseException pe) {
    		throw new RuntimeException("Impossible to parse string " + value + " to date");
    	}
    }
    
}
