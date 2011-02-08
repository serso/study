package org.solovyev.study.gwt.common.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.solovyev.study.gwt.common.client.CommonService;

import java.util.HashMap;

/**
 * User: serso
 * Date: 2/6/11
 * Time: 5:04 PM
 */
public class CommonRemoteServiceImpl extends RemoteServiceServlet implements CommonService {
	@Override
	public HashMap<String, Object> initialLoading() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}