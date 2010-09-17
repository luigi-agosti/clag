package novoda.clag.introspector.jdo.sample;

import novoda.clag.introspector.annotation.Clag;
import novoda.clag.model.MetaEntity.OnConflictPolicy;

public class ClagAnnotationModel {
	
	@Clag(unique=true,key=true,onConflictPolicy=OnConflictPolicy.REPLACE)
	private Long id;
	
	@Clag
	private String field;
	
	@Clag(userId=true,hidden=true)
	private String userId;

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

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	
}