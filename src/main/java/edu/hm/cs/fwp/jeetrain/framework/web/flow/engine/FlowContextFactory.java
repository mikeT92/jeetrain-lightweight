/* FlowContextFactory.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Factory that manages request scoped instances of {@link FlowContext}.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 24.01.2012 11:23:26
 */
public interface FlowContextFactory {

	/**
	 * Creates a new {@link FlowContext} instance and binds it to the specified
	 * HTTP request.
	 * 
	 * @param request
	 *            current HTTP request
	 * @param response
	 *            current HTTP response
	 * @return new instance bound to the given HTTP request
	 * @throws IllegalStateException
	 *             if an FlowContext instance has already been bound to the
	 *             given request.
	 */
	public FlowContext createInstance(HttpServletRequest request, HttpServletResponse response);

	/**
	 * Returns the {@link FlowContext} instance bound to the specified HTTP request.
	 * @throws IllegalStateException
	 *             if no FlowContext instance has been bound to the
	 *             given request.
	 */
	public FlowContext getCurrentInstance(HttpServletRequest request);
	
	/**
	 * Disposes the given flow context and all reources allocated by it.
	 */
	public void disposeInstance(FlowContext flowContext);
}
