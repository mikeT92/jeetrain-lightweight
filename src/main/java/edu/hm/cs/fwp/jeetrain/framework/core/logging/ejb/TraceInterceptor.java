/* TraceInterceptor.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.core.logging.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * Trace interceptor for EJB method invocations.
 * <p>
 * To enable tracing of EJB method invocations, make sure that a logger named
 * <code>EJBTrace</code> is configured on DEBUG level.
 * </p>
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 2.1 May 6, 2010 5:54:05 PM
 */
public class TraceInterceptor {

	private final Logger logger = Logger.getLogger("EJBTrace");

	// private final Log logger = LogFactory.getLog("EJBTrace");

	private static final String DEFAULT_EYE_CATCHER = "*** EJBTrace ***";

	/**
	 * Maximum length of message return value that will be logged.
	 */
	private static final int MAX_RETURN_VALUE_LENGTH = 1024 * 8;

	private String eyeCatcher = DEFAULT_EYE_CATCHER;

	@Resource
	private SessionContext context;

	@AroundInvoke
	public Object handleInvocation(InvocationContext invocation) throws Exception {
		if (isTraceEnabled()) {
			return invokeUnderTrace(invocation);
		} else {
			return invokeLoggingExceptions(invocation);
		}
	}

	/**
	 * Proceeds with the intercepted invocation tracing method parameters and
	 * return value plus duration on DEBUG level.
	 */
	private Object invokeUnderTrace(InvocationContext invocation) throws Exception {
		Object result = null;
		try {
			long start = 0;
			traceEnterMethod(invocation);
			start = System.nanoTime();
			result = invocation.proceed();
			long stop = System.nanoTime();
			long duration = stop - start;
			traceExitMethod(result, invocation, duration);
		} catch (Exception ex) {
			traceException(ex, invocation);
			throw ex;
		}
		return result;
	}

	/**
	 * Proceeds with the intercepted invocation logging exceptions only on ERROR
	 * level.
	 */
	private Object invokeLoggingExceptions(InvocationContext invocation) throws Exception {
		try {
			return invocation.proceed();
		} catch (Exception ex) {
			logException(ex, invocation);
			throw ex;
		}
	}

	/**
	 * Writes a log entry with all information about the called service
	 * operation to the given logger.
	 */
	private void traceEnterMethod(InvocationContext invocation) {
		StringBuilder writer = new StringBuilder();
		appendEnterMethodEntry(writer, invocation);
		logger.info(ensureMaxMessageLength(writer.toString()));
	}

	/**
	 * Writes a log entry with all information about the return value of the
	 * called service operation to the given logger.
	 */
	private void traceExitMethod(Object returnValue, InvocationContext invocation, long duration) {
		StringBuilder writer = new StringBuilder();
		appendExitMethodEntry(writer, invocation, returnValue, duration);
		logger.info(ensureMaxMessageLength(writer.toString()));
	}

	/**
	 * Writes a log entry with all information about the exception thrown by the
	 * called service operation to the given logger.
	 */
	private void traceException(Exception exception, InvocationContext invocation) {
		StringBuilder writer = new StringBuilder();
		appendExceptionEntry(writer, invocation, exception);
		logger.severe(ensureMaxMessageLength(writer.toString()));
	}

	/**
	 * Writes a log entry with all information about the called service
	 * operation to the given logger.
	 */
	private void logException(Exception exception, InvocationContext invocation) {
		StringBuilder writer = new StringBuilder();
		appendEnterMethodEntry(writer, invocation);
		writer.append("aborted unexpectedly with exception [");
		writer.append(exception.getMessage());
		writer.append("]");
		logger.severe(ensureMaxMessageLength(writer.toString()));
	}

	/**
	 * Writes the enter method trace entry to the given writer.
	 */
	private StringBuilder appendEnterMethodEntry(StringBuilder writer, InvocationContext invocation) {
		appendPrefix(writer, invocation);
		writer.append("invoked with arguments [");
		appendArguments(writer, invocation);
		writer.append("]");
		return writer;
	}

	/**
	 * Writes the arguments of the service operation to the given writer.
	 */
	private StringBuilder appendArguments(StringBuilder writer, InvocationContext invocation) {

		Object[] arguments = invocation.getParameters();
		if (null != arguments && 0 != arguments.length) {
			int lastIndex = arguments.length - 1;
			for (int argumentIndex = 0; argumentIndex < arguments.length; argumentIndex++) {
				writer.append("argument[");
				writer.append(argumentIndex);
				writer.append("]=[");
				writer.append(arguments[argumentIndex]);
				writer.append("]");
				if (argumentIndex < lastIndex) {
					writer.append(", ");
				}
			}
		}
		return writer;
	}

	/**
	 * Writes the exit method trace entry to the given writer.
	 */
	private StringBuilder appendExitMethodEntry(StringBuilder writer, InvocationContext invocation, Object returnValue,
			long duration) {
		appendPrefix(writer, invocation);
		writer.append("returned with return value [");
		appendReturnValue(returnValue, invocation, writer);
		writer.append("] duration: [");
		writer.append(duration);
		writer.append(" ns]");
		return writer;
	}

	/**
	 * Writes the return value of the called service operation to the given
	 * writer. Arrays of primitive types are ignored.
	 */
	private StringBuilder appendReturnValue(Object returnValue, InvocationContext invocation, StringBuilder writer) {

		if (returnValue != null && returnValue.getClass().isArray()) {
			if (returnValue instanceof Object[]) {
				Object[] array = (Object[]) returnValue;
				for (int i = 0; i < array.length; i++) {
					appendReturnValue(array[i], invocation, writer);
					if (i < array.length - 1) {
						writer.append(", ");
					}
				}
			} else {
				// ignore primitive arrays like byte streams (documents).
				return writer;
			}
		}

		writer.append(returnValue);
		return writer;
	}

	/**
	 * Writes the enter method trace entry to the given writer.
	 */
	private StringBuilder appendExceptionEntry(StringBuilder writer, InvocationContext invocation,
			Throwable exception) {
		appendPrefix(writer, invocation);
		writer.append("aborted unexpectedly with exception [");
		writer.append(exception.getMessage());
		writer.append("]");
		return writer;
	}

	/**
	 * Appends a default prefix containing the eyecatcher, the class name and
	 * the method name to the specified writer.
	 */
	private StringBuilder appendPrefix(StringBuilder writer, InvocationContext invocation) {
		writer.append(this.eyeCatcher);
		writer.append(" class [");
		writer.append(invocation.getTarget().getClass().getName());
		writer.append("] method [");
		writer.append(invocation.getMethod().getName());
		writer.append("] ");
		if (this.context != null) {
			writer.append("called by [");
			writer.append(context.getCallerPrincipal().getName());
			writer.append("] ");
		}
		return writer;
	}

	/**
	 * Ensures that messages exceeding the predefined maximum length are
	 * truncated to avoid OutOfMemoryErrors during logging.
	 */
	private String ensureMaxMessageLength(String message) {
		String result = message;
		if (MAX_RETURN_VALUE_LENGTH < message.length()) {
			result = message.substring(0, MAX_RETURN_VALUE_LENGTH) + "... (truncated " + message.length()
					+ " characters)";
		}
		return result;
	}

	/**
	 * Returns <code>true</code> if tracing on DEBUG level is enabled;
	 * <code>false</code> otherwise.
	 */
	private boolean isTraceEnabled() {
		return this.logger.isLoggable(Level.FINE);
	}
}
