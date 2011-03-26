package novoda.clag.converter.json;

import novoda.clag.converter.Converter;

public abstract class BaseConverter implements Converter {
	
	public static final String CONTENT_TYPE = "text/plain";

	@Override
	public String getContentType() {
		return CONTENT_TYPE; 
	}
	
}
