/* FlowScopeELResolver.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.faces;

import java.beans.FeatureDescriptor;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.faces.context.FacesContext;

import edu.hm.cs.fwp.jeetrain.framework.web.flow.engine.impl.FlowScopeBeanStore;

/**
 * Custom {@link ELResolver} implementation which handles the resolution of
 * flow-scoped managed beans.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 31.01.2012 13:38:34
 */
public final class FlowScopeELResolver extends ELResolver {

	/**
	 * Returns <code>null</code>, if the specified base is null; otherwise
	 * {@code Object.class}.
	 * 
	 * @see javax.el.ELResolver#getCommonPropertyType(javax.el.ELContext,
	 *      java.lang.Object)
	 */
	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		Class<?> result = null;
		if (base != null) {
			result = Object.class;
		}
		return result;
	}

	/**
	 * Returns an {@code Iterator} referencing an empty [@code List} since this
	 * EL resolver does not come with any {@code FeatureDescriptor}.
	 * 
	 * @see javax.el.ELResolver#getFeatureDescriptors(javax.el.ELContext,
	 *      java.lang.Object)
	 */
	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext arg0, Object arg1) {
		return Collections.<FeatureDescriptor> emptyList().iterator();
	}

	/**
	 * @see javax.el.ELResolver#getType(javax.el.ELContext, java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public Class<?> getType(ELContext context, Object base, Object property)
			throws NullPointerException, PropertyNotFoundException, ELException {
		return Object.class;
	}

	/**
	 * @see javax.el.ELResolver#getValue(javax.el.ELContext, java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public Object getValue(ELContext context, Object base, Object property)
			throws NullPointerException, PropertyNotFoundException, ELException {
		if (property == null) {
			throw new PropertyNotFoundException();
		}
		Object result = null;
		if (base == null && FlowScopeEventAdapter.FLOW_SCOPE_NAME.equals(property.toString())) {
			// explicit scope lookup request
			result = getRequiredFlowScopeBeanStore(context);
		} else if (base != null && base instanceof FlowScopeBeanStore) {
			// We're dealing with the custom scope that has been explicity
			// referenced
			// by an expression. 'property' will be the name of some entity
			// within the scope.
			result = ((FlowScopeBeanStore) base).get(property.toString());
		} else if (base == null) {
			// bean may have already been created and is in scope.
			// check to see if the bean is present
			result = getRequiredFlowScopeBeanStore(context).get(property.toString());
		}
		if (result != null) {
			context.setPropertyResolved(true);
		}
		return result;
	}

	/**
	 * Returns always true since this EL resolver does not handle writeable
	 * properties.
	 * 
	 * @see javax.el.ELResolver#isReadOnly(javax.el.ELContext, java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property)
			throws NullPointerException, PropertyNotFoundException, ELException {
		return true;
	}

	/**
	 * Ignores the specified value since this EL resolver does not handle
	 * writeable properties.
	 * 
	 * @see javax.el.ELResolver#setValue(javax.el.ELContext, java.lang.Object,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public void setValue(ELContext arg0, Object arg1, Object arg2, Object arg3)
			throws NullPointerException, PropertyNotFoundException, PropertyNotWritableException,
			ELException {
	}

	/**
	 * Returns the flow-scope beanstore from the current HTTP session.
	 */
	private Map<String, Object> getRequiredFlowScopeBeanStore(ELContext context) {
		FlowScopeBeanStore result = null;
		FacesContext facesContext = (FacesContext) context.getContext(FacesContext.class);
		result =
				(FlowScopeBeanStore) facesContext
						.getExternalContext().getSessionMap()
						.get(FlowScopeBeanStore.ATTRIBUTE_NAME);
		if (result == null) {
			throw new IllegalStateException(
					"Unable to retrieve flow-scope beanstore from HTTP session! Are you sure that you configured the FlowAwarePhaseListener correctly in your faces-config.xml?");
		}
		return result;
	}
}
