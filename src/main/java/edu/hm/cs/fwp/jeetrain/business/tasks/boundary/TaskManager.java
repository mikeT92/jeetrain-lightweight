/* TaskManager.java 
 */
package edu.hm.cs.fwp.jeetrain.business.tasks.boundary;

import java.util.List;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;

/**
 * @author theism
 *
 */
public interface TaskManager {
	
	public Task addTask(Task newTask);

	public Task retrieveTaskById(long taskId);

	public List<Task> retrieveAllTasks();

}
