package novoda.clag.introspector.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(value={java.lang.annotation.ElementType.TYPE})
@Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface IsParent {

	String through();

	String of();
	
}
