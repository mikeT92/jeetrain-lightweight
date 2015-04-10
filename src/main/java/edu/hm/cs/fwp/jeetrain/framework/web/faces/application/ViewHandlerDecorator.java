/* ViewHandlerDecorator.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.faces.application;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.context.FacesContext;

/**
 * Decorator that wraps any existing JSF 2.0 view handler to add some XFrame 2.0
 * specific behaviour.
 * <p>
 * The following features are added:
 * </p>
 * <ul>
 * <li>- customized calculation algorithm of current view's locale</li>
 * </ul>
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0
 */
public final class ViewHandlerDecorator extends ViewHandlerWrapper {

	private static final String LOCALE_VALUE_EXPRESSION = "#{i18n.locale}";

	private final Logger logger = Logger.getLogger(getClass().getName());

	private final ViewHandler decorated;

	public ViewHandlerDecorator(ViewHandler decorated) {
		this.logger.info("Decorating existing view handler [" + decorated + "]");
		this.decorated = decorated;
	}

	/**
	 * @see javax.faces.application.ViewHandlerWrapper#getWrapped()
	 */
	@Override
	public ViewHandler getWrapped() {
		return this.decorated;
	}

	/**
	 * @see javax.faces.application.ViewHandlerWrapper#calculateLocale(javax.faces.context.FacesContext)
	 */
	@Override
	public Locale calculateLocale(FacesContext context) {
		Locale result = null;

		try {
			ELContext elContext = context.getELContext();
			ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
			ValueExpression expression =
					expressionFactory.createValueExpression(
							elContext, LOCALE_VALUE_EXPRESSION, Locale.class);
			result = (Locale) expression.getValue(elContext);
		} catch (Exception ex) {
			logger.log(Level.WARNING, "Unable to resolve current locale using EL expression ["
					+ LOCALE_VALUE_EXPRESSION + "], falling back to standard JSF20 locale!", ex);
		}

		if (result == null) {
			result = super.calculateLocale(context);
		}

		return result;
	}
}
