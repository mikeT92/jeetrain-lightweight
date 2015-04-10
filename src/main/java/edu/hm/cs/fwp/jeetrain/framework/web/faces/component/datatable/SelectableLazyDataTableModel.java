/* SelectableLazyDataTableModel.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.framework.web.faces.component.datatable;

import org.primefaces.model.LazyDataModel;

/**
 * TODO: add documentation
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 01.11.2012 12:40:27
 */
@SuppressWarnings("serial")
public abstract class SelectableLazyDataTableModel<T> extends LazyDataModel<T> {

	private T selectedRow;
	
	public void setSelectedRow(T row) {
		this.selectedRow = row;
	}
	
	public T getSelectedRow() {
		return this.selectedRow;
	}

	public boolean isAnyRowSelected() {
		return this.selectedRow != null;
	}
	
}
