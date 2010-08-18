package novoda.clag.introspector.jdo.sample;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import novoda.clag.introspector.annotation.IsChild;

/**
 * @author Luigi Agosti <luigi.agosti@gmail.com>
 */
@PersistenceCapable
@IsChild(of="Page", through="parentKeyId")
public class Page extends Model {

	private static final long serialVersionUID = 1L;
	
	@Persistent private List<String> groupIds;
	@Persistent private String title;
	@Persistent private String description;
	@Persistent private String headline;
	@Persistent private String relativeUrl;
	@Persistent private Long parentKeyId;
	@Persistent private Long order;
	@Persistent private String color;
	@Persistent private Date modifiedDate;
	
	public void setTitle(String title) { this.title = title; }
	public String getTitle() { return title; }

	public void setDescription(String description) { this.description = description; }
	public String getDescription() { return description; }
	
	public void setRelativeUrl(String relativeUrl) { this.relativeUrl = relativeUrl; }
	public String getRelativeUrl() { return relativeUrl; }

	public void setParentKeyId(Long parentKeyId) { this.parentKeyId = parentKeyId; }
	public Long getParentKeyId() { return parentKeyId; }

	public void setOrder(Long order) { this.order = order; }
	public Long getOrder() { return order; }
	
	public void setColor(String color) { this.color = color; }
	public String getColor() { return color; }
	
	public void setGroupIds(List<String> groupIds) { this.groupIds = groupIds; }
	public List<String> getGroupIds() { return groupIds; }
	
	public void setHeadline(String headline) { this.headline = headline; }
	public String getHeadline() { return headline; }
	
	public void setModifiedDate(Date modifiedDate) { this.modifiedDate = modifiedDate; }
	public Date getModifiedDate() { return modifiedDate; }

	
}
