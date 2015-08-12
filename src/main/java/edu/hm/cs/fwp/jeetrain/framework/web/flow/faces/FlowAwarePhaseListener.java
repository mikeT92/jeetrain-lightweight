/* FlowAwarePhaseListener.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.faces;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.Flow;
import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.FlowRequestInterceptor;

/**
 * A JSF phase listener which adds awareness of {@link Flow}s to the JSF request
 * processing lifecycle.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 18.01.2012 18:02:24
 */
public final class FlowAwarePhaseListener implements PhaseListener {

	private static final long serialVersionUID = 6186803056593795255L;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private FlowRequestInterceptor flowRequestInterceptor;

	private final FlowScopeEventAdapter flowScopeEventAdapter = new FlowScopeEventAdapter();

	/**
	 * Deactivates flow processing after the RENDER_RESPONSE phase or when
	 * FacesContext.renderResponse is set to {@code true}.
	 * 
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	@Override
	public void afterPhase(PhaseEvent event) {
		this.logger.trace("*** FLOW *** afterPhase [{}]...", event.getPhaseId());
		if (PhaseId.RENDER_RESPONSE.equals(event.getPhaseId())
				|| event.getFacesContext().getResponseComplete()) {
			stopFlowRequestProcessing(event.getFacesContext());
		}
	}

	/**
	 * Activates flow processing before the RESTORE_VIEW phase.
	 * 
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	@Override
	public void beforePhase(PhaseEvent event) {
		this.logger.trace("*** FLOW *** beforePhase [{}]...", event.getPhaseId());
		if (PhaseId.RESTORE_VIEW.equals(event.getPhaseId())) {
			startFlowRequestProcessing(event.getFacesContext());
		}
	}

	/**
	 * Tells JSF that this listener is listening to all phases.
	 * 
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	private void startFlowRequestProcessing(FacesContext faces) {
		this.logger.trace("*** FLOW *** starting flow-aware request processing...");
		if (!getFlowRequestInteceptor(faces).beforeProcessingRequest(
				(HttpServletRequest) faces.getExternalContext().getRequest(),
				(HttpServletResponse) faces.getExternalContext().getResponse(), this.flowScopeEventAdapter)) {
			this.logger
					.debug("*** FLOW *** aborting JSF request processing due to redirect requested by flow engine...");
			faces.renderResponse();
			faces.responseComplete();
		}
	}

	private void stopFlowRequestProcessing(FacesContext faces) {
		this.logger.trace("*** FLOW *** stopping flow-aware request processing...");
		getFlowRequestInteceptor(faces).afterProcessingRequest(
				(HttpServletRequest) faces.getExternalContext().getRequest(),
				(HttpServletResponse) faces.getExternalContext().getResponse());
	}

	/**
	 * Returns the flow request interceptor which processes flow-related HTTP
	 * requests.
	 */
	private FlowRequestInterceptor getFlowRequestInteceptor(FacesContext facesContext) {
		if (null == this.flowRequestInterceptor) {
			this.flowRequestInterceptor = CDIUtils.lookupBean(FlowRequestInterceptor.class);
		}
		return this.flowRequestInterceptor;
	}
}
