package org.solovyev.study.gwt.common.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * User: serso
 * Date: 2/6/11
 * Time: 5:04 PM
 */
public interface CommonServiceAsync {

	void initialLoading(AsyncCallback<HashMap<String, Object>> async);
}
