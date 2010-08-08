package novoda.clag.mock;

import novoda.clag.model.Cursor;
import novoda.clag.model.Entity;
import novoda.clag.provider.AbstractContentProvider;

/**
 * @author luigi.agosti
 */
public class MockContentProvider extends AbstractContentProvider {

	@Override
	public Cursor query(String name, String[] projection, String selection,
			String[] selectionArgs, String sortOrder, Entity entity) {
		return null;
	}

	@Override
	public Entity schema(String name) {
		return null;
	}

}
