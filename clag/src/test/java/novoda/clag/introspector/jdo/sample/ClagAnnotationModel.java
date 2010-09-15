package novoda.clag.introspector.jdo.sample;

import novoda.clag.introspector.annotation.Clag;
import novoda.clag.model.MetaEntity.OnConflictPolicy;

public class ClagAnnotationModel {
	
	@Clag(unique=true,isKey=true,onConflictpolicy=OnConflictPolicy.REPLACE)
	private Long id;
	
	@Clag
	private String field;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}
	
	

}
