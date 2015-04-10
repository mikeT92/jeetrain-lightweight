/* TaskBrowserManagedBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.presentation.tasks;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.SortOrder;

import edu.hm.cs.fwp.jeetrain.business.tasks.boundary.TaskManagerBean;
import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.framework.web.context.ViewScoped;
import edu.hm.cs.fwp.jeetrain.framework.web.faces.component.datatable.SelectableLazyDataTableModel;

/**
 * {@code ManagedBean} that manages the browseTasks view.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 31.10.2012 16:23:52
 */
@SuppressWarnings("serial")
@Named("taskBrowser")
@ViewScoped
public class TaskBrowserBean implements Serializable {

	@Inject
	private TaskManagerBean taskStore;

	private SelectableLazyDataTableModel<Task> taskModel;

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
	}

	// values ----------------------------------------------------------------

	public SelectableLazyDataTableModel<Task> getTaskModel() {
		return this.taskModel;
	}

	public boolean isEditTaskEnabled() {
		return this.taskModel.isAnyRowSelected();
	}

	public boolean isViewTaskEnabled() {
		return this.taskModel.isAnyRowSelected();
	}

	public boolean isDeleteTaskEnabled() {
		return this.taskModel.isAnyRowSelected();
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
