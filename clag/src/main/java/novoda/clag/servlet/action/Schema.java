package novoda.clag.servlet.action;

import novoda.clag.model.MetaEntity;
import novoda.clag.servlet.context.Context;

public class Schema implements Action {

	@Override
	public String execute(Context context) {
		MetaEntity mds = context.getProvider().schema(context.getName());
		return context.getConverter().convert(mds, context);
	}

}
