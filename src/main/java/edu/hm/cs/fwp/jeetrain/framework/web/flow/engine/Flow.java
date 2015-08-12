/* Flow.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine;

import java.util.List;
import java.util.Map;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.view.ViewReference;

/**
 * Flow representing a visual sequence of business activities.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
public interface Flow {

	/**
	 * Name of the request parameter representing a flow id of an active flow.
	 */
	public static final String FLOW_ID_PARAM_NAME = "flow.flowId";

	/**
	 * Returns the unique identifier of this flow.
	 */
	public String getId();

	/**
	 * Returns the human-readable name of this flow.
	 */
	public String getName();

	/**
	 * Returns all states of this flow as an unmodifiable list.
	 */
	public List<State> getStates();

	/**
	 * Returns the current state of this flow.
	 */
	public State getCurrentState();

	/**
	 * Returns the view path that will properly identify this flow.
	 */
	public String getCurrentViewPath();

	/**
	 * Returns the view reference that this flow will return to when it is the last
	 * active flow.
	 * <p>
	 * The returnToViewReference of a flow is determined during the flow's activation.
	 * </p>
	 */
	public ViewReference getReturnToViewReference();

	/**
	 * Activates this flow in order to process flow events.
	 * 
	 * @param flowContext
	 *            flow context
	 * @param firstStateName
	 *            name of the first state this flow is supposed to start with
	 *            (optional)
	 * @param parameters
	 *            map of inputs parameters passed to this flow (optional)
	 */
	public void activate(FlowContext flowContext, String firstStateName,
			Map<String, Object> parameters);

	/**
	 * Deactivates this flow stopping the processing of flow events.
	 */
	public void deactivate(FlowContext flowContext);
}
