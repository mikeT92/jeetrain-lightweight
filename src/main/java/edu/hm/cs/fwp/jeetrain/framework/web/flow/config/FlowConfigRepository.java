/* FlowConfigRepository.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.config;

import javax.validation.constraints.NotNull;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Repository that manages flow definitions.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 25.01.2012 15:47:12
 */
public interface FlowConfigRepository {

	/**
	 * Returns the flow definition of the flow identified by the given flow
	 * name.
	 * 
	 * @return FlowConfigBean, if a flow definition with the specified flow name
	 *         exists; otherwise <code>null</code>.
	 */
	public FlowConfigBean getFlowConfigByFlowName(@NotNull String flowName);

	/**
	 * Returns the flow definition of the flow identified by the given view
	 * path.
	 * 
	 * @return FlowConfigBean, if a flow definition related to the specified
	 *         view path exists; otherwise <code>null</code>.
	 */
	public FlowConfigBean getFlowConfigByViewPath(@NotNull String viewPath);

	/**
	 * Returns the flow definition of the flow identified by the given view
	 * reference.
	 * <p>
	 * The internal path of the view reference must match to a view path of a
	 * flow definition.
	 * </p>
	 * 
	 * @return FlowConfigBean, if a flow definition related to the specified
	 *         view path exists; otherwise <code>null</code>.
	 */
	public FlowConfigBean getFlowConfigByViewReference(@NotNull ViewReference viewReference);
}