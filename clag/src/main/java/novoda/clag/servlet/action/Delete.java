package novoda.clag.servlet.action;

import novoda.clag.model.MetaEntity;
import novoda.clag.servlet.context.Context;

public class Delete implements Action {

	@Override
	public String execute(Context context) {
		MetaEntity mds = context.getProvider().schema(context.getName());
		try {
			String remoteId = context.getRemoteId();
			context.getProvider().delete(context.getName(), remoteId, mds);
			return "{\"id\":" + remoteId + "}";
		} catch (Exception e) {
			throw new RuntimeException("Problem during the delete of entity " 
					+ context.getName() + " with id : " + context.getRemoteId(), e);
		}
	}

}
