package novoda.clag.servlet.action;

import novoda.clag.servlet.context.Context;

public class Describe implements Action {

	@Override
	public String execute(Context context) {
		return context.getConverter().describe(context);
	}

}
