/* MethodValidationInterceptor.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.core.validation.ejb;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 * EJB 3.0 interceptor that can validate constraints on parameters and return
 * values defined by Bean Validation API annotations.
 * <p>
 * This implementation uses a Hibernate extension to the JSR303 specification
 * which is capable of validating parameters and return values. Since
 * Hibernate's extension is strongly based on the recommendation for
 * method-level validation defined by the original JSR303 v1.0 spec and this
 * extension is very likely to make it into the v1.1 version of the spec, it is
 * worth the additional dependency on a specific vendor's implementation.
 * </p>
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 10.10.2011 16:25:03
 */
public class MethodValidationInterceptor {

	private final ValidatorFactory validatorFactory;

	public MethodValidationInterceptor() {
		this.validatorFactory = Validation.buildDefaultValidatorFactory();
	}

	/**
	 * Performs method-level validation defined by Bean Validation annotations
	 * attached to parameters and return values.
	 * <p>
	 * Parameters are validated before the intercepted method call is forwarded
	 * to the target object. So if parameter validation fails the actual method
	 * provided by the target object is never called.
	 * </p>
	 * <p>
	 * Return values are validated immediately after the method call has been
	 * forwarded to the target object and before the return values passed back
	 * from the actual method provided by the target object is returned. So if
	 * return value validation fails the orginal return value is lost.
	 * </p>
	 */
	@AroundInvoke
	public Object handleInvocation(InvocationContext invocation) throws Exception {
		validateParameters(invocation);
		Object result = invocation.proceed();
		validateReturnValue(invocation, result);
		return result;
	}

	/**
	 * Performs the validation of the parameters ensuring that all preconditions
	 * defined by Bean Validation annotations attached to the parameters itself
	 * or attached to the parameter class are met.
	 * 
	 * @throws MethodConstraintViolationException
	 *             if parameter validation fails.
	 */
	private void validateParameters(InvocationContext invocation) {
/* FIXME:		
		Set<MethodConstraintViolation<Object>> violations =
				getValidator().validateAllParameters(
						invocation.getTarget(), invocation.getMethod(), invocation.getParameters());
		if (!violations.isEmpty()) {
			throw new MethodConstraintViolationException(appendConditionViolationMessage(
					new StringBuilder(), "precondition", violations).toString(), violations);
		}
*/
	}

	/**
	 * Performs the validation of the return value ensuring that all
	 * postconditions defined by Bean Validation annotations attached to the
	 * return value itself or attached to the return value class are met.
	 * 
	 * @throws MethodConstraintViolationException
	 *             if return value validation fails.
	 */
	private void validateReturnValue(InvocationContext invocation, Object returnValue) {
/* FIXME:		
		Set<MethodConstraintViolation<Object>> violations =
				getValidator().validateReturnValue(
						invocation.getTarget(), invocation.getMethod(), returnValue);
		if (!violations.isEmpty()) {
			throw new MethodConstraintViolationException(appendConditionViolationMessage(
					new StringBuilder(), "postcondition", violations).toString(), violations);
		}
*/
	}

//	/**
//	 * Appends a message which precisely describes the failed precondition or
//	 * postconditions.
//	 */
//	private StringBuilder appendConditionViolationMessage(StringBuilder message,
//			String conditionType, Set<MethodConstraintViolation<Object>> violations) {
//		message.append(conditionType);
//		message.append(" violations detected:");
//		return appendViolationMessages(message, violations);
//	}
//
//	/**
//	 * Appends messages for each violation.
//	 */
//	private StringBuilder appendViolationMessages(StringBuilder message,
//			Set<MethodConstraintViolation<Object>> violations) {
//		for (MethodConstraintViolation<Object> current : violations) {
//			message.append(" \"");
//			message.append(current.getPropertyPath());
//			message.append(" ");
//			message.append(current.getMessage());
//			message.append("\"");
//		}
//		return message;
//	}
//
//	/**
//	 * Returns a validator capable of validating annotated parameters and return
//	 * values.
//	 * <p>
//	 * In order to avoid concurrency issues on Validator instances, each call
//	 * returns a new instance.
//	 * </p>
//	 */
//	private MethodValidator getValidator() {
//		return this.validatorFactory.getValidator().unwrap(MethodValidator.class);
//	}
}
