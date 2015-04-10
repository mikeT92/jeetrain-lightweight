/* TaskCategory.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.tasks.entity;

/**
 * Enumeration representing the various categories of a {@link Task}.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 31.10.2012 13:17:48
 */
public enum TaskCategory {

	UNDEFINED,
	BUGFIX,
	REFACTORING,
	NEW_FEATURE,
	PERFORMANCE_IMPROVEMENT,
	RELEASE_MANAGEMENT,
	QUALITY_ASSURANCE,
	BUILD_FAILURE,
	COMMUNICATION
}
