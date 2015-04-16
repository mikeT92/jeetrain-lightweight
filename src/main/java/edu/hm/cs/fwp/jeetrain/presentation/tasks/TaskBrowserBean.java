/* TaskBrowserManagedBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.presentation.tasks;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.hm.cs.fwp.jeetrain.business.tasks.boundary.TaskManager;
import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.framework.web.faces.component.datatable.SelectableDataTableModel;
import edu.hm.cs.fwp.jeetrain.framework.web.faces.component.datatable.SelectableLazyDataTableModel;

/**
 * {@code ManagedBean} that manages the browseTasks view.
 * 
 * @author theism
 * @version 1.0
 * @since release 1.0
 */
@Named("taskBrowser")
@ViewScoped
public class TaskBrowserBean implements Serializable {

	private static final long serialVersionUID = -4264634758367258630L;

	@Inject
	private TaskManager boundary;

	private List<Task> tasks;
	
	private SelectableDataTableModel<Task> taskModel;

	// event handlers --------------------------------------------------------

	/**
	 * Gets called whenever a new instance of this managed bean has been created
	 * and all dependencies resolved.
	 */
	@PostConstruct
	public void onPostConstruct() {
		System.out.println(getClass().getSimpleName() + "#onPostConstruct()");
	}

	/**
	 * Gets called whenever the associated view is about to be rendered.
	 */
	public void onPreRenderView() {
		System.out.println(getClass().getSimpleName() + "#onPreRenderView()");
		if (this.tasks == null) {
			this.tasks = this.boundary.retrieveAllTasks();
			this.taskModel = new SelectableDataTableModel<Task>(this.tasks);
		}
	}

	// values ----------------------------------------------------------------

	public SelectableDataTableModel<Task> getTaskModel() {
		return this.taskModel;
	}

	public boolean isEditTaskEnabled() {
		return true; // this.taskModel.isAnyRowSelected();
	}

	public boolean isViewTaskEnabled() {
		return true; // this.taskModel.isAnyRowSelected();
	}

	public boolean isDeleteTaskEnabled() {
		return true; // this.taskModel.isAnyRowSelected();
	}

	// actions ---------------------------------------------------------------

	public String newTask() {
		return "newTask?faces-redirect=true";
	}

	public String editTask() {
		return "editTask?faces-redirect=true" + "&taskId="
				+ this.taskModel.getSelectedRow().getId();
	}

	public String viewTask() {
		return "viewTask?faces-redirect=true" + "&taskId="
				+ this.taskModel.getSelectedRow().getId();
	}

	public String deleteTask() {
		return "browseTasks?faces-redirect=true";
	}

	public String closeView() {
		return "/home/home?faces-redirect=true";
	}
}
