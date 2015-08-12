/* TaskManagerBean.java 
 */
package edu.hm.cs.fwp.jeetrain.business.tasks.boundary;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.framework.core.persistence.GenericRepositoryBean;

/**
 * {@code SERVICE FACADE} implementation class.
 * 
 * @author theism
 */
@Stateless
@Local(TaskManager.class)
@RolesAllowed("JEETRAIN_USER")
public class TaskManagerBean implements TaskManager {

	@Resource
	private SessionContext sessionContext;

	@EJB
	private GenericRepositoryBean repository;

	/**
	 * @see edu.hm.cs.fwp.jeetrain.business.tasks.boundary.TaskManager#addTask(edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task)
	 */
	@Override
	public Task addTask(Task newTask) {
		newTask.setResponsibleUserId(this.sessionContext.getCallerPrincipal()
				.getName());
		this.repository.addEntity(newTask);
		return newTask;
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.business.tasks.boundary.TaskManager#retrieveTaskById(long)
	 */
	@Override
	public Task retrieveTaskById(long taskId) {
		return this.repository.getEntityById(Task.class, taskId);
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.business.tasks.boundary.TaskManager#retrieveAllTasks()
	 */
	@Override
	public List<Task> retrieveAllTasks() {
		return this.repository.queryEntities(Task.class, Task.QUERY_ALL, null);
	}
}
