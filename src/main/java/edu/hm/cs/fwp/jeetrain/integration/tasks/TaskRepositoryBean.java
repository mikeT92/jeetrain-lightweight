/* TaskRepositoryBean.java 
 */
package edu.hm.cs.fwp.jeetrain.integration.tasks;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;

/**
 * {@code Repository} für {@link Task}s.
 * 
 * @author theism
 */
@Stateless
public class TaskRepositoryBean {
	@PersistenceContext
	EntityManager entityManager;

	public Task addTask(Task task) {
		this.entityManager.persist(task);
		this.entityManager.flush();
		this.entityManager.refresh(task);
		return task;
	}

	public Task setTask(Task task) {
		return this.entityManager.merge(task);
	}

	public void removeTask(Task task) {
		Task mergedTask = this.entityManager.merge(task);
		this.entityManager.remove(mergedTask);
	}

	public Task getTaskById(long taskId) {
		return this.entityManager.find(Task.class, taskId);
	}

	public List<Task> getAllTasks() {
		TypedQuery<Task> query = this.entityManager.createNamedQuery(
				Task.QUERY_ALL, Task.class);
		return query.getResultList();
	}
}
