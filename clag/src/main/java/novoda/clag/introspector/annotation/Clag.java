package novoda.clag.introspector.annotation;

import novoda.clag.model.MetaEntity.OnConflictPolicy;


@java.lang.annotation.Target(value={java.lang.annotation.ElementType.FIELD,java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Clag {
	
	OnConflictPolicy onConflictPolicy() default OnConflictPolicy.NOT_DEFINED;
	
	boolean unique() default false;
	
	boolean key() default false;
	
	boolean searchable() default false;
	
	boolean indexable() default false;
	
	boolean hidden() default false;
	
	boolean userId() default false;
	
}
