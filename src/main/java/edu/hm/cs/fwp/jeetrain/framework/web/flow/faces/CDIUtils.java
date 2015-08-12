/**
 * 
 */
package edu.hm.cs.fwp.jeetrain.framework.web.flow.faces;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

/**
 * Utility class of CDI managed bean lookup in a non-CDI context.
 * 
 * @author theism
 *
 */
public class CDIUtils {
	
	/**
	 * Performs a CDI lookup for the bean of the specified type.
	 * @param expectedType expected type of the bean
	 * @return found bean instance; never {@code null}
	 * @throws IllegalStateException - if no bean or more than one bean is found
	 */
	public static <T> T lookupBean(Class<T> expectedType) {
		BeanManager beanManager = getBeanManager();
		Set<Bean<?>> availableBeans = beanManager.getBeans(expectedType);
		if (availableBeans.isEmpty()) {
			throw new IllegalStateException("No beans found of given type [" + expectedType + "]");
		}
		if (availableBeans.size() > 1) {
			throw new IllegalStateException("Expected to found only 1 bean of type ["+ expectedType + "], but found " + availableBeans.size());
		}
		Bean<?> singleBean = availableBeans.iterator().next();
		CreationalContext<?> context = beanManager.createCreationalContext(singleBean);
		return expectedType.cast(beanManager.getReference(singleBean, expectedType, context));
	}
	
	private static BeanManager getBeanManager() {
		return CDI.current().getBeanManager();
	}
}
