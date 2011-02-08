package org.solovyev.study.gwt.common.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.solovyev.study.gwt.common.client.utils.GwtUtilsOnClient;

import java.util.HashMap;

/**
 * User: serso
 * Date: 2/6/11
 * Time: 5:02 PM
 */
public class Common implements EntryPoint {

	public void onModuleLoad() {
		CommonService.App.getInstance().initialLoading(new AsyncCallback<HashMap<String, Object>>() {
			@Override
			public void onFailure(Throwable caught) {
				GwtUtilsOnClient.handleException(caught);
			}

			@Override
			public void onSuccess(HashMap<String, Object> result) {
				extractInitialParameters(result);
			}
		});
	}

	protected void extractInitialParameters(HashMap<String, Object> result) {
		// todo serso: implement
		//To change body of created methods use File | Settings | File Templates.
	}
}
