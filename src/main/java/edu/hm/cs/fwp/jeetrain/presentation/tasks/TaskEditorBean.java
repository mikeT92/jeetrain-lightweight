/* TaskEditorManagedBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.presentation.tasks;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.hm.cs.fwp.jeetrain.business.tasks.boundary.TaskManagerBean;
import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.business.tasks.entity.TaskCategory;
import edu.hm.cs.fwp.jeetrain.business.tasks.entity.TaskPriority;

/**
 * {@code ManagedBean} that manages the browseTasks view.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 31.10.2012 16:23:52
 */
@Named("taskEditor")
@ViewScoped
public class TaskEditorBean implements Serializable {

	private static final long serialVersionUID = -8252721628528236785L;

	@Inject
	private TaskManagerBean boundary;

	private long taskId;

	private Task task;

	// event handlers --------------------------------------------------------

	/**
	 * Gets called after a new instance has been created and all injectable
	 * properties are set.
	 */
	@PostConstruct
	public void onPostConstruct() {
	}

	/**
	 * Gets called whenever the associated view is about to be rendered.
	 * <p>
	 * Obtains a {@code Task} instance to work on; if not already done:
	 * </p>
	 * <ul>
	 * <li>If {@link TaskEditorBean#taskId} == 0 the managed bean assumes that
	 * it supposed to created a new one.</li>
	 * <li>If {@link TaskEditorBean#taskId} is != 0 the managed bean assumes
	 * that someone put an instance into the session and it is supposed to
	 * modify that one.</li>
	 * </ul>
	 */
	public void onPreRenderView() {
		if (this.task == null) {
			if (this.taskId == 0) {
				this.task = new Task();
			} else {
				this.task = this.boundary.retrieveTaskById(this.taskId);
			}
		}
	}

	// parameters ------------------------------------------------------------

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	// values ----------------------------------------------------------------

	/**
	 * Returns the current task under work.
	 */
	public Task getTask() {
		return this.task;
	}

	/**
	 * Returns all available categories for tasks.
	 */
	public TaskCategory[] getTaskCategories() {
		return TaskCategory.values();
	}

	/**
	 * Returns all available priorities for tasks.
	 */
	public TaskPriority[] getTaskPriorities() {
		return TaskPriority.values();
	}

	// actions ---------------------------------------------------------------

	public String newTask() {
		this.task = this.boundary.addTask(this.task);
		return "viewTask?faces-redirect=true?taskId=" + this.task.getId();
	}

	public String editTask() {
		return "viewTask?faces-redirect=true";
	}

	public String closeView() {
		this.task = null;
		return "/tasks/browseTasks?faces-redirect=true";
	}
}
