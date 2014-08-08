package tableModel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor Kadiata
 */
public class TableModelTarget extends AbstractTableModel {

    String[] columnNames = {"No", "Machine(s)", "<html><font color=red>Production Rate</font>",
        "<html><font color=blue>Total Production</font>"};
    ArrayList[] Data;

    public TableModelTarget() {
        Data = new ArrayList[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            Data[i] = new ArrayList();
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getRowCount() {
        return Data[0].size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 1:
                return String.class;
            case 2:
            case 3:
                return Double.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return Data[columnIndex].get(rowIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Data[columnIndex].set(rowIndex, aValue);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if (col == 0 || col == 1) {
            return (false);
        } else {
            return true;
        }
    }

    public void addNewRow() {
        for (int i = 0; i < columnNames.length; i++) {
            Data[i].add("");
        }
        this.fireTableRowsInserted(0, Data[0].size() - 1);
    }

    public void removeNewRow() {
        for (int i = 0; i < columnNames.length; i++) {
            Data[i].remove(Data[i].size() - 1);
        }
        this.fireTableRowsDeleted(0, Data[0].size() - 1);
    }

    public void removeNewRow(int index) {
        for (int i = 0; i < columnNames.length; i++) {
            Data[i].remove(index);
        }
        this.fireTableRowsDeleted(0, Data[0].size() - 1);
    }
}
