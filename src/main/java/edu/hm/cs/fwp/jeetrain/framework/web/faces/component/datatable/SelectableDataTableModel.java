package edu.hm.cs.fwp.jeetrain.framework.web.faces.component.datatable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.SelectableDataModel;

/**
 * Extension of JSF's {@code ListDataModel} to simplify databinding of
 * {@code List} based values to {@code p:dataTable} components with full
 * support of row selection.
 * 
 * @author theism
 * @version 1.0
 * @since 03.02.2015
 */
public class SelectableDataTableModel<T> extends ListDataModel<T> implements SelectableDataModel<T> {
    /**
     * Currently selected row.
     */
    private T selectedRow;
    /**
     * Currently selected rows.
     */
    private List<T> selectedRows = Collections.emptyList();
    public SelectableDataTableModel() {
        super();
    }
    public SelectableDataTableModel(List<T> data) {
        super(data);
    }
    public SelectableDataTableModel(Collection<T> data) {
        super(new ArrayList<T>(data));
    }
    /**
     * Returns the wrapped data as a {@code List} in order to ensure typesafety
     * and keep nasty type casts at one place.
     * <p>
     * Hopefully, future JSF versions will overcome this design flaw of the
     * abstract class {@code DataModel}.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public List<T> getWrappedDataList() {
        return (List<T>) super.getWrappedData();
    }
    /**
     * Returns the index of the specified object in the underlying list as the
     * row key.
     * 
     * @see org.primefaces.model.SelectableDataModel#getRowKey(java.lang.Object)
     */
    @Override
    public Object getRowKey(T object) {
        List<T> values = getWrappedDataList();
        return values.indexOf(object);
    }
    /**
     * Returns the object with the specified row key from the underlying list
     * assuming the specified row key represents the position of the object
     * within the list.
     * 
     * @see org.primefaces.model.SelectableDataModel#getRowData(java.lang.String)
     */
    @Override
    public T getRowData(String rowKey) {
        return getWrappedDataList().get(Integer.parseInt(rowKey));
    }
    /**
     * Returns the currently selected row, if any item has been selected from
     * the list; otherwise <code>null</code>.
     */
    public T getSelectedRow() {
        return this.selectedRow;
    }
    /**
     * Sets the data table item representing the currently selected row.
     * <p>
     * Usually this setter is called by {@code ManagedBean}s in order to select
     * a specific row.
     * </p>
     * 
     * @param row
     *            data table item representing the currently selected row.
     */
    public void setSelectedRow(T row) {
        this.selectedRow = row;
    }
    /**
     * Returns the selected rows as an array of data table items.
     * <p>
     * This getter is usually called from views since PrimeFaces
     * {@code p:dataTable} is based on {@code Object} arrays.
     * {@code ManagedBean}s should use {@link #getSelectedRowsList()} to ensure
     * type safety.
     * </p>
     */
    public Object[] getSelectedRows() {
        return selectedRows.toArray();
    }
    /**
     * Sets all data table items currently selected.
     * <p>
     * This getter is usually called from views using PrimeFaces
     * {@code p:dataTable} with {@code multiple} selection mode.
     * {@code ManagedBean}s should use {@link #setSelectedRowsList(List)} to
     * ensure type safety.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void setSelectedRows(Object[] selectedRows) {
        if (selectedRows != null && selectedRows.length > 0) {
            this.selectedRows = new ArrayList<T>(selectedRows.length);
            for (Object current : selectedRows) {
                this.selectedRows.add((T) current);
            }
        }
        else {
            this.selectedRows = Collections.emptyList();
        }
    }
    /**
     * Returns all data table items which are currently selected in the
     * corresponding {@code x2h:dataTable}, if any row is selected; otherwise an
     * empty list.
     */
    public List<T> getSelectedRowsList() {
        return this.selectedRows;
    }
    /**
     * Returns all data table items which are supposed to be rendered as
     * selected in the corresponding {@code x2h:dataTable}.
     */
    public void setSelectedRowsList(List<T> selectedRows) {
        this.selectedRows = selectedRows;
    }
    /**
     * Returns {@code true} if at least one row is selected in the corresponding
     * {@code x2h:dataTable}; otherwise {@code false}.
     */
    public boolean isAnyRowSelected() {
        return this.selectedRow != null || this.selectedRows.size() > 0;
    }
    /**
     * Returns the number of currently selected rows.
     */
    public int getNumberOfSelectedRows() {
        int result = this.selectedRow != null ? 1 : 0;
        if (result == 0) {
            result = this.selectedRows.size();
        }
        return result;
    }
    /**
     * Handles {@code rowSelect} events, which indicate that a row in
     * p:dataTable has been selected.
     * <p>
     * This event listener method does not provide any implementation, so
     * subclasses must override this method in order to perform custom behaviour
     * on rowSelect events.
     * </p>
     */
    public void onRowSelected(SelectEvent event) {
    }
    /**
     * Handles {@code rowUnselect} events, which indicate that a row in
     * p:dataTable has been unselected.
     * <p>
     * This event listener method does not provide any implementation, so
     * subclasses must override this method in order to perform custom behaviour
     * on rowUnselect events.
     * </p>
     */
    public void onRowUnselected(UnselectEvent event) {
    }
}
