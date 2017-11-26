/* TaskManagerBean.java 
 */
package edu.hm.cs.fwp.jeetrain.business.tasks.boundary;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.framework.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.jeetrain.framework.core.persistence.GenericRepositoryBean;

/**
 * {@code SERVICE FACADE} implementation class.
 * 
 * @author theism
 */
@Stateless
@RolesAllowed("JEETRAIN_USER")
@Interceptors({ TraceInterceptor.class })
public class TaskManagerBean {

	@Resource
	private SessionContext sessionContext;

	@EJB
	private GenericRepositoryBean repository;

	public Task addTask(Task newTask) {
		newTask.setResponsibleUserId(this.sessionContext.getCallerPrincipal().getName());
		this.repository.addEntity(newTask);
		return newTask;
	}

	public Task retrieveTaskById(long taskId) {
		return this.repository.getEntityById(Task.class, taskId);
	}

	public List<Task> retrieveAllTasks() {
		return this.repository.queryEntities(Task.class, Task.QUERY_ALL, null);
	}

	public void removeTask(Task task) {
		this.repository.removeEntity(task);
	}
}
