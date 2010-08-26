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
	
	private boolean isRelation;
	
	private boolean include;
	
	private String owner;
	
	private String parent;
	
	private String child;
	
	private String from;

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
	
	public boolean isChild() {
		if(child != null) {
			return true;
		}
		return false;
	}


	public void setRelation(boolean isRelation) {
		this.isRelation = isRelation;
	}

	public boolean isRelation() {
		return isRelation;
	}


	public void setFrom(String from) {
		this.from = from;
	}

	public String getFrom() {
		return from;
	}


	public void setInclude(boolean include) {
		this.include = include;
	}

	public boolean isInclude() {
		return include;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
		return owner;
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

		public Builder isRelation(boolean isRelation, String from, boolean include) {
			md.setRelation(true);
			md.setFrom(from);
			md.setInclude(include);
			return this;
		}

		public Builder owner(String owner) {
			md.setOwner(owner);
			return this;
		}
	}
}
