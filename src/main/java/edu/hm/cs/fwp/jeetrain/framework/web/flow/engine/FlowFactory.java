/* FlowFactory.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Factory that creates executable instances of flows from flow definitions.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 25.01.2012 17:42:46
 */
public interface FlowFactory {

	/**
	 * Creates an executable flow instance from the specified flow name.
	 */
	public Flow createFlow(String flowName);

	/**
	 * Creates an executable flow instance which is triggered by the specified
	 * view path.
	 * 
	 * @return newly created {@code Flow} instance, if a flow definition
	 *         associated with the specified view path can be found;
	 *         <code>null</code> otherwise.
	 */
	public Flow createFlowFromViewPath(String viewPath);

	/**
	 * Creates an executable flow instance which is triggered by the specified
	 * view reference.
	 * 
	 * @return newly created {@code Flow} instance, if a flow definition
	 *         associated with the specified view reference can be found;
	 *         <code>null</code> otherwise.
	 */
	public Flow createFlowFromViewReference(ViewReference viewReference);
}
