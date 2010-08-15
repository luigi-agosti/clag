package novoda.clag.model;

/**
 * In my view I will like to add all the limitation that the provider need
 * to enforce on queries, for example : 
 * - users
 * - location
 * - time
 * - and pagination of course
 * 
 * @author luigi.agosti
 *
 */
public class Options {
	
	public static final int DEFAULT_LIMIT = 100;
	
	public static final int DEFAULT_OFFSET = 0;
	
	private int limit = DEFAULT_LIMIT;

	private int offset = DEFAULT_OFFSET;
	
	public static final Options getDefault() {
		return new Options();
	}
	
	public Options() {}
	
	public Options(int limit) {
		this.setLimit(limit);
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getOffset() {
		return offset;
	}
	
}
