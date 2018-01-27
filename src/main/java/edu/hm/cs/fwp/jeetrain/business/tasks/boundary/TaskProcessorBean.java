/*
 * jeetrain-lightweight:TaskProcessorBean.java
 * Copyright (c) Michael Theis 2017
 */
package edu.hm.cs.fwp.jeetrain.business.tasks.boundary;

import java.time.LocalDateTime;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.business.tasks.entity.TaskLifeCycleState;
import edu.hm.cs.fwp.jeetrain.common.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.jeetrain.common.core.persistence.repository.GenericRepositoryBean;

/**
 * {@code Boundary} that processes tasks according to their lifecycle.
 * 
 * @author mikeT92
 * @version 1.0
 * @since 23.01.2018
 */
@Stateless
@RolesAllowed("JEETRAIN_USER")
@Interceptors({ TraceInterceptor.class })
public class TaskProcessorBean {

	@Resource
	private SessionContext sessionContext;

	@Inject
	private GenericRepositoryBean repository;

	/**
	 * Submits a new task to the application.
	 * <p>
	 * The currently authenticated user is set as the task submitter.
	 * </p>
	 * 
	 * @param task
	 */
	public void submitTask(Task task) {
		TaskLifeCycleState state = task.getLifeCycleState();
		if (TaskLifeCycleState.UNDEFINED == state) {
			task.setLifeCycleState(TaskLifeCycleState.OPEN_RUNNING);
		} else {
			throw new IllegalStateException(String.format("Expected task lifecycle state [%s] but was [%s]",
					TaskLifeCycleState.UNDEFINED, state));
		}
		task.setSubmitterUserId(this.sessionContext.getCallerPrincipal().getName());
		task.setSubmissionDate(LocalDateTime.now());
		this.repository.addEntity(task);
	}

	/**
	 * Retrieves the task with the given task ID.
	 * 
	 * @param taskId
	 *            unique task identifier
	 * @return task with the given task ID, if the task exists; otherwise
	 *         {@code null}.
	 */
	public Task retrieveTaskById(long taskId) {
		return this.repository.getEntityById(Task.class, taskId);
	}

	/**
	 * Removes the task with the given task ID.
	 * 
	 * @param taskId
	 *            unique task identifier
	 */
	public void removeTaskById(long taskId) {
		this.repository.removeEntityById(Task.class, taskId);
	}

}
