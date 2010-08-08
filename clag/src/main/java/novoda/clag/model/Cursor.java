package novoda.clag.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luigi.agosti
 */
public class Cursor {

	private List<Map<String,Object>> rows;
	
	private Map<String, Object> currentRow;
	
	public Cursor() {
		rows = new ArrayList<Map<String,Object>>();
		currentRow = new LinkedHashMap<String, Object>();
	}
	
	public void add(String key, Object value) {
		currentRow.put(key, value);
	}
	
	public void next() {
		rows.add(currentRow);
		currentRow = new HashMap<String, Object>();
	}
	
	public List<Map<String, Object>> getRows() {
		return rows;
	}
	
}
