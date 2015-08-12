/**
 * 
 */
package edu.hm.cs.fwp.jeetrain.business.tasks.boundary;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;

/**
 * REST-Endpunkt für Boundary {@link TaskManager}.
 * @author theism
 */
@Path("/tasks")
public class TaskManagerRestEndpointBean {

	@EJB
	TaskManagerBean boundary;
	
	public Task addTask(Task newTask) {
		// TODO Auto-generated method stub
		return null;
	}

	public Task retrieveTaskById(long taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	public List<Task> retrieveAllTasks() {
		return this.boundary.retrieveAllTasks();
	}
}
