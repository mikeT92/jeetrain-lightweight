/* Task.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.tasks.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity;

/**
 * {@code Entity} type that represents tasks.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 29.10.2012 17:27:22
 */
@Entity
@Table(name = "T_TASK")
@NamedQueries({
		@NamedQuery(name = Task.QUERY_ALL, query = "SELECT t FROM Task t ORDER BY t.id"),
		@NamedQuery(name = Task.COUNT_ALL, query = "SELECT COUNT(t) FROM Task t") })
public class Task implements Serializable, AuditableEntity {

	private static final long serialVersionUID = 6549807945660625663L;

	public static final String QUERY_ALL = "edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task.QUERY_ALL";

	public static final String COUNT_ALL = "edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task.COUNT_ALL";

	/**
	 * Unique identifier of this task.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator="Task.id.generator")
	@TableGenerator(name="Task.id.generator", table="T_SEQUENCE")
	@Column(name = "TASK_ID")
	private long id;

	/**
	 * Single-line description or summary of what this task is about.
	 */
	@NotNull
	@Size(max = 80)
	@Column(name = "SUBJECT")
	private String subject;

	/**
	 * Detailed description of this task.
	 */
	@NotNull
	@Size(max = 1024)
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * Groups task into specific categories like "Bug", "Enhancement".
	 */
	@NotNull
	@Column(name = "CATEGORY")
	@Enumerated(EnumType.ORDINAL)
	private TaskCategory category = TaskCategory.UNDEFINED;

	/**
	 * Priority.
	 */
	@NotNull
	@Column(name = "PRIORITY")
	@Enumerated(EnumType.ORDINAL)
	private TaskPriority priority = TaskPriority.UNDEFINED;

	/**
	 * Status of this task.
	 */
	@NotNull
	@Column(name = "LIFECYCLE_STATE")
	@Enumerated(EnumType.ORDINAL)
	private TaskLifeCycleState lifeCycleState = TaskLifeCycleState.UNDEFINED;

	/**
	 * Date/time when this task has been requested.
	 * <p>
	 * Expected to be set when task lifeCycleState is <code>running</code>.
	 * </p>
	 */
	@Column(name = "SUBMISSION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date submissionDate;

	/**
	 * User-ID of participant who submitted this task.
	 * <p>
	 * Expected to be set when task lifeCycleState is <code>completed</code>.
	 * </p>
	 */
	@Column(name = "SUBMITTER_ID")
	private String submitterUserId;

	/**
	 * Date/time when this task is supposed to be completed.
	 */
	@Column(name = "DUE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;

	/**
	 * Completion rate in percent, ranges from 0 to 100.
	 */
	@Column(name = "COMPLETION_RATE")
	private int completionRate;

	/**
	 * Date/time when this task has been completed.
	 * <p>
	 * Expected to be set when task lifeCycleState is <code>completed</code>.
	 * </p>
	 */
	@Column(name = "COMPLETION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date completionDate;

	/**
	 * User-ID of participant who completed this task.
	 * <p>
	 * Expected to be set when task lifeCycleState is <code>completed</code>.
	 * </p>
	 */
	@Column(name = "COMPLETER_ID")
	private String completedByUserId;

	/**
	 * User-ID of participant who is currently responsible for the completion of
	 * this task.
	 */
	@Size(max = 16)
	@Column(name = "RESPONSIBLE_ID")
	private String responsibleUserId;

	/**
	 * Project-ID of the project this task is related to.
	 * <p>
	 * Equals {@link #affectedApplicationId} if this is a application
	 * maintenance task not related to a specific project.
	 * </p>
	 */
	@Size(max = 16)
	@Column(name = "AFFECTED_PROJECT_ID")
	private String affectedProjectId;

	/**
	 * Application-ID of the application this task is related to.
	 */
	@Size(max = 8)
	@Column(name = "AFFECTED_APPLICATION_ID")
	private String affectedApplicationId;

	/**
	 * Name of the logical module this task is related to.
	 */
	@Size(max = 32)
	@Column(name = "AFFECTED_MODULE")
	private String affectedModule;

	/**
	 * Application resource that this task is referring to.
	 */
	@Size(max = 256)
	@Column(name = "AFFECTED_RESOURCE")
	private String affectedResource;

	/**
	 * Estimated effort in hours to complete this task.
	 */
	@Column(name = "ESTIMATED_EFFORT")
	private int estimatedEffort;

	/**
	 * Actual effort in hours to complete this task.
	 */
	@Column(name = "ACTUAL_EFFORT")
	private int actualEffort;

	/**
	 * Current version of this instance (used for optimistic locking).
	 */
	@Column(name = "VERSION")
	@Version
	private int version;

	/**
	 * User ID of the user who created this entity.
	 */
	@Size(max = 16)
	@Column(name = "CREATOR_ID")
	private String creatorId;

	/**
	 * Date/timer when this entity was created.
	 */
	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	/**
	 * User ID of the user who modified this entity.
	 */
	@Size(max = 16)
	@Column(name = "LAST_MODIFIER_ID")
	private String lastModifierId;

	/**
	 * Date/time this entity was modified.
	 */
	@Column(name = "LAST_MODIFICATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModificationDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskCategory getCategory() {
		return category;
	}

	public void setCategory(TaskCategory category) {
		this.category = category;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public TaskLifeCycleState getLifeCycleState() {
		return lifeCycleState;
	}

	public void setLifeCycleState(TaskLifeCycleState state) {
		this.lifeCycleState = state;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date startDate) {
		this.submissionDate = startDate;
	}

	public String getSubmitterUserId() {
		return submitterUserId;
	}

	public void setSubmitterUserId(String requesterUserId) {
		this.submitterUserId = requesterUserId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getCompletionRate() {
		return completionRate;
	}

	public void setCompletionRate(int completionRate) {
		this.completionRate = completionRate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getCompletedByUserId() {
		return completedByUserId;
	}

	public void setCompletedByUserId(String completedById) {
		this.completedByUserId = completedById;
	}

	public String getResponsibleUserId() {
		return responsibleUserId;
	}

	public void setResponsibleUserId(String responsibleId) {
		this.responsibleUserId = responsibleId;
	}

	public String getAffectedProjectId() {
		return affectedProjectId;
	}

	public void setAffectedProjectId(String affectedProjectId) {
		this.affectedProjectId = affectedProjectId;
	}

	public String getAffectedApplicationId() {
		return affectedApplicationId;
	}

	public void setAffectedApplicationId(String affectedApplicationId) {
		this.affectedApplicationId = affectedApplicationId;
	}

	public String getAffectedModule() {
		return affectedModule;
	}

	public void setAffectedModule(String affectedModule) {
		this.affectedModule = affectedModule;
	}

	public String getAffectedResource() {
		return affectedResource;
	}

	public void setAffectedResource(String affectedResource) {
		this.affectedResource = affectedResource;
	}

	public int getEstimatedEffort() {
		return estimatedEffort;
	}

	public void setEstimatedEffort(int estimatedEffort) {
		this.estimatedEffort = estimatedEffort;
	}

	public int getActualEffort() {
		return actualEffort;
	}

	public void setActualEffort(int actualEffort) {
		this.actualEffort = actualEffort;
	}

	public int getVersion() {
		return version;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity#getCreatorId()
	 */
	public String getCreatorId() {
		return this.creatorId;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity#getCreationDate()
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity#trackCreation(java.lang.String,
	 *      java.util.Date)
	 */
	public void trackCreation(String creatorId, Date creationDate) {
		if (this.creatorId != null || this.creationDate != null) {
			throw new IllegalStateException(
					"AuditableEntity.trackCreation() can only be called once during the lifetime of an AuditableEntity!");
		}
		this.creatorId = creatorId;
		this.creationDate = creationDate;
		trackModification(creatorId, creationDate);
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity#getLastModifierId()
	 */
	public String getLastModifierId() {
		return this.lastModifierId;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity#getLastModificationDate()
	 */
	public Date getLastModificationDate() {
		return this.lastModificationDate;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.framework.core.persistence.AuditableEntity#trackModification(java.lang.String,
	 *      java.util.Date)
	 */
	public void trackModification(String lastModifierId,
			Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
		this.lastModifierId = lastModifierId;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + " { id : " + this.id
				+ ", version : " + this.version + " }";
	}
}
