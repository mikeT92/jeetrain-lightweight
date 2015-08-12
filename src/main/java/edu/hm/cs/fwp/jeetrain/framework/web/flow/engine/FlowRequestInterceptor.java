/* FlowRequestInterceptor.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Listener that is capable of mapping HTTP requests to active flows and trigger
 * their execution.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
public interface FlowRequestInterceptor {

	/**
	 * Pre-processes the given HTTP request with respect to the current state of
	 * the flow engine.
	 * <p>
	 * This pre-processing of the given request may result in a HTTP redirect on
	 * the given response which prematurely completes any further processing of
	 * the given HTTP request. This premature completion is indicated by the
	 * return value <code>false</code>.
	 * </p>
	 * 
	 * @param request
	 *            current HTTP request
	 * @param response
	 *            current HTTP response
	 * @param listener
	 *            flow lifecycle listener
	 * 
	 * @return <code>true</code>, if the caller can continue with the HTTP
	 *         request processing; <code>false</code> otherwise.
	 */
	public boolean beforeProcessingRequest(HttpServletRequest request,
			HttpServletResponse response, FlowLifecycleListener listener);

	/**
	 * Post-processes the given HTTP request enabling the flow engine to perform
	 * some housekeeping activities like discarding deactivated flows etc.
	 * 
	 * @param request
	 *            current HTTP request
	 * @param response
	 *            current HTTP response
	 */
	public void afterProcessingRequest(HttpServletRequest request, HttpServletResponse response);
}
