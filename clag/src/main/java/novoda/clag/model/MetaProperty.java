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

		public Builder isKey(boolean isKey) {
			md.setIsKey(isKey);
			return this;
		}

		public Builder canBeNull(boolean canBeNull) {
			md.setCanBeNull(canBeNull);
			return this;
		}

		public MetaProperty build() {
			return md;
		}
	}

}
