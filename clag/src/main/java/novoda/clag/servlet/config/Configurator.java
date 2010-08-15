package novoda.clag.servlet.config;

import novoda.clag.servlet.context.Context;
import novoda.clag.util.Configurable;

public interface Configurator extends Configurable {

	interface InitParameters {
		String CONVERTER = "converter";
		
		String CONTENT_PROVICER = "provider";
		
		String INTROSPECTOR = "introspector";
		
		String CONTENT_CLASSES = "contentClasses";
		
		String CONTEXT = "contextClass";
	}

	Context getContext();

}
