/* TaskEditorManagedBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.presentation.tasks;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
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
@SuppressWarnings("serial")
@Named("taskEditor")
@ConversationScoped
public class TaskEditorBean implements Serializable {

	@Inject
	private Conversation conversation;

	@Inject
	private TaskManagerBean facade;

	private boolean owningConversation;

	private long taskId;

	private Task task;

	// event handlers --------------------------------------------------------

	/**
	 * Gets called after a new instance has been created and all injectable
	 * properties are set.
	 */
	@PostConstruct
	public void onPostConstruct() {
		if (this.conversation.isTransient()) {
			this.conversation.begin();
			this.owningConversation = true;
		}
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
		System.out.println(getClass().getSimpleName()
				+ "#onPreRenderView: task=[" + this.task + "], taskId=["
				+ this.taskId + "]");
		if (this.task == null) {
			if (this.taskId == 0) {
				this.task = new Task();
			} else {
				this.task = this.facade.retrieveTaskById(this.taskId);
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
		this.task = this.facade.addTask(this.task);
		return "viewTask?faces-redirect=true";
	}

	public String editTask() {
		return "viewTask?faces-redirect=true";
	}

	public String closeView() {
		this.task = null;
		if (!this.conversation.isTransient() && this.owningConversation) {
			this.conversation.end();
		}
		return "/tasks/browseTasks?faces-redirect=true";
	}
}
