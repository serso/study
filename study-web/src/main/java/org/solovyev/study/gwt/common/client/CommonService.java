package org.solovyev.study.gwt.common.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

import java.util.HashMap;

/**
 * User: serso
 * Date: 2/6/11
 * Time: 5:04 PM
 */
@RemoteServiceRelativePath("CommonService")
public interface CommonService extends RemoteService {

	 HashMap<String, Object> initialLoading();

	/**
	 * Utility/Convenience class.
	 * Use CommonService.App.getInstance() to access static instance of CommonRemoteServiceAsync
	 */
	public static class App {
		private static final CommonServiceAsync ourInstance = (CommonServiceAsync) GWT.create(CommonService.class);

		public static CommonServiceAsync getInstance() {
			return ourInstance;
		}
	}
}
