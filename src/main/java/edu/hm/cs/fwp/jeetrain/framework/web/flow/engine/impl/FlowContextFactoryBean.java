/* FlowContextFactoryBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContext;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowContextFactory;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowFactory;

/**
 * Default implementation of {@link FlowContextFactoryBean} which is based on a
 * Spring managed POJO.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 24.01.2012 11:31:09
 */
@Named("flowContextFactory")
public final class FlowContextFactoryBean implements FlowContextFactory {

	@Inject
	private FlowFactory flowFactory;

	@Override
	public FlowContext createInstance(HttpServletRequest request, HttpServletResponse response) {
		FlowContext result = (FlowContext) request.getAttribute(FlowContextImpl.ATTRIBUTE_NAME);
		if (result != null) {
			throw new IllegalStateException(
					"*** FLOW *** There is already a flow context ["
							+ result
							+ "] bound to the given HTTP request ["
							+ request.getRequestURL().toString()
							+ "]!"
							+ " Did you call FlowContextFactory.createInstance() more than once during the lifetime of the specified request?");
		}

		HttpSession session = request.getSession();
		FlowStack flowStack = null;
		FlowScopeBeanStore flowScopeBeanStore = null;
		synchronized (session) {
			flowStack = (FlowStack) session.getAttribute(FlowStack.ATTRIBUTE_NAME);
			if (flowStack == null) {
				flowStack = new FlowStack();
				session.setAttribute(FlowStack.ATTRIBUTE_NAME, flowStack);
			}
			flowScopeBeanStore =
					(FlowScopeBeanStore) session.getAttribute(FlowScopeBeanStore.ATTRIBUTE_NAME);
			if (flowScopeBeanStore == null) {
				flowScopeBeanStore = new FlowScopeBeanStore();
				session.setAttribute(FlowScopeBeanStore.ATTRIBUTE_NAME, flowScopeBeanStore);
			}
		}
		result =
				new FlowContextImpl(
						request, response, flowStack, this.flowFactory, new FlowLifecyleImpl(),
						flowScopeBeanStore);

		request.setAttribute(FlowContextImpl.ATTRIBUTE_NAME, result);

		return result;
	}

	@Override
	public FlowContext getCurrentInstance(HttpServletRequest request) {
		FlowContext result = (FlowContext) request.getAttribute(FlowContextImpl.ATTRIBUTE_NAME);
		if (result == null) {
			throw new IllegalStateException(
					"*** FLOW *** There is no flow context bound to the given HTTP request ["
							+ request.getRequestURL().toString()
							+ "]!"
							+ " Did you forget to call FlowContextFactory.createInstance() during the lifetime of the specified request?");
		}
		return result;
	}

	@Override
	public void disposeInstance(FlowContext flowContext) {
		// TODO: implement cleanup of session attributes not needed anymore!
	}
}
