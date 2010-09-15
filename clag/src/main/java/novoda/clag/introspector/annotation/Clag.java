package novoda.clag.introspector.annotation;

import novoda.clag.model.MetaEntity.OnConflictPolicy;


@java.lang.annotation.Target(value={java.lang.annotation.ElementType.FIELD,java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Clag {
	
	OnConflictPolicy onConflictpolicy() default OnConflictPolicy.NOT_DEFINED;
	
	boolean unique() default false;
	
	boolean isKey() default false;
	
	boolean isSearchable() default false;
	
	boolean isIndexable() default false;
	
}
