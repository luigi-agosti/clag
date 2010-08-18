package novoda.clag.introspector.jdo.sample;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import novoda.clag.introspector.annotation.IsChild;

/**
 * @author Luigi Agosti <luigi.agosti@gmail.com>
 * 
 * This is representing a group or a page in facebook.
 */
@PersistenceCapable
@IsChild(of="Page", through="pageId")
public class Group extends FacebookModel {

	private static final long serialVersionUID = 1L;

	@Persistent private String title;

	public void setTitle(String title) { this.title = title; }
	public String getTitle() { return title; }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Group id : ");
		sb.append(getFacebookId()).append(" title : ").append(title).append("\n");
		return sb.toString();
	}
	
}
