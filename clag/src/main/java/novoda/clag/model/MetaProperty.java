package novoda.clag.model;

/**
 * Property is the atomic piece of information that is forming an entity.
 * 
 * @author luigi.agosti
 * 
 */
public class MetaProperty {

	private String name;

	private String type;

	private boolean isKey;

	private boolean canBeNull;
	
	private String parent;
	
	private String child;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean getIsKey() {
		return isKey;
	}

	public void setIsKey(boolean isKey) {
		this.isKey = isKey;
	}

	public boolean isCanBeNull() {
		return canBeNull;
	}

	public void setCanBeNull(boolean canBeNull) {
		this.canBeNull = canBeNull;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParent() {
		return parent;
	}
	
	public void setChild(String child) {
		this.child = child;
	}

	public String getChild() {
		return child;
	}
	
	public boolean isParent() {
		if(parent != null) {
			return true;
		}
		return false;
	}

	/**
	 * Builder
	 */
	public static final class Builder {
		private MetaProperty md;

		public Builder(String name) {
			md = new MetaProperty();
			md.setName(name);
		}

		public Builder type(String type) {
			md.setType(type);
			return this;
		}
		
		public Builder parent(String parent) {
			md.setParent(parent);
			return this;
		}

		public Builder isKey(boolean isKey) {
			md.setIsKey(isKey);
			return this;
		}

		public Builder canBeNull(boolean canBeNull) {
			md.setCanBeNull(canBeNull);
			return this;
		}
		
		public Builder child(String property) {
			md.setChild(property);
			return this;
		}

		public MetaProperty build() {
			return md;
		}
	}

}
