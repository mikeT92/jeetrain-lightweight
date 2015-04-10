/* TaskStoreBean.java 
 */
package edu.hm.cs.fwp.jeetrain.presentation.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;

import java.io.Serializable;

/**
 * Simple dummy store for {@code Task} entities, to be replaced with proper
 * {@code SERVICE FACADE}.
 * 
 * @author theism
 */
@Named
@SessionScoped
public class XTaskStoreBean implements Serializable {

	private static final long serialVersionUID = 4036662368222957601L;

	private final Map<Long, Task> tasksById = new HashMap<Long, Task>();

	private final AtomicLong taskIdSequence = new AtomicLong();

	public Task addTask(Task task) {
		task.setId(this.taskIdSequence.incrementAndGet());
		this.tasksById.put(task.getId(), task);
		return task;
	}
	
	public Task retrieveTaskbyId(long taskId) {
		return this.tasksById.get(taskId);
	}
	
	public List<Task> retrieveAllTasks() {
		return new ArrayList<Task>(this.tasksById.values());
	}
}
