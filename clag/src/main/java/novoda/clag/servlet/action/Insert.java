package novoda.clag.servlet.action;

import novoda.clag.model.Cursor;
import novoda.clag.model.MetaEntity;
import novoda.clag.servlet.context.Context;

public class Insert implements Action {

	@Override
	public String execute(Context context) {
		MetaEntity mds = context.getProvider().schema(context.getName());
		Cursor cursor = context.getProvider().insert(context.getName(), context.getCursorFromRequest(mds), mds);
		return context.getConverter().convert(cursor, mds, context);
	}

}
