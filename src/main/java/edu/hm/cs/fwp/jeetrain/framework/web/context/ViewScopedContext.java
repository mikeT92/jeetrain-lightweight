package edu.hm.cs.fwp.jeetrain.framework.web.context;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * Custom scope that mimicks JSF's {@code ViewScoped} behaviour.
 * 
 * @author theism
 * @since 1.0
 */
public class ViewScopedContext implements Context, Extension {

	/**
	 * Registers this custom context.
	 */
	public void afterBeanDiscovery(@Observes AfterBeanDiscovery event,
			BeanManager manager) {
		event.addContext(this);
	}

	/**
	 * Returns an existing view-scoped bean from JSF's view map, if there is one
	 * that matches the specified contextual; otherwise {@null}.
	 * 
	 * @see javax.enterprise.context.spi.Context#get(javax.enterprise.context.spi.Contextual)
	 */
	@Override
	public <T> T get(Contextual<T> pContextual) {
		Bean<T> bean = (Bean<T>) pContextual;
		return (T) getViewMap().get(bean.getName());
	}

	/**
	 * Returns an existing view-scoped bean from JSF's view map, if there is one
	 * that matches the specified contextual. If no matching bean can be found,
	 * a new one is created and added to JSF's view map.
	 * 
	 * @see javax.enterprise.context.spi.Context#get(javax.enterprise.context.spi.Contextual,
	 *      javax.enterprise.context.spi.CreationalContext)
	 */
	@Override
	public <T> T get(Contextual<T> pContextual,
			CreationalContext<T> pCreationalContext) {
		T result = null;
		Bean<T> bean = (Bean<T>) pContextual;
		Map<String, Object> viewMap = getViewMap();
		result = (T) viewMap.get(bean.getName());
		if (result == null) {
			result = bean.create(pCreationalContext);
			viewMap.put(bean.getName(), result);
		}
		return result;
	}

	/**
	 * @see javax.enterprise.context.spi.Context#getScope()
	 */
	@Override
	public Class<? extends Annotation> getScope() {
		return ViewScoped.class;
	}

	/**
	 * @see javax.enterprise.context.spi.Context#isActive()
	 */
	@Override
	public boolean isActive() {
		return true;
	}

	/**
	 * Returns JSF's view map containing all view-scoped beans.
	 */
	private <T> Map<String, Object> getViewMap() {
		FacesContext fctx = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = fctx.getViewRoot();
		return viewRoot.getViewMap(true);
	}
}