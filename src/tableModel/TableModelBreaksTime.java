package tableModel;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor Kadiata
 */
public class TableModelBreaksTime extends AbstractTableModel {

    private final String[] COLUMNNAMES = {"#", "Start Time", "End Time", "<html>Break Name", ""};
    private final ArrayList[] DATA;
//    private boolean makeEditable;
//
//    public void setMakeEditable(boolean makeEditable) {
//        this.makeEditable = makeEditable;
//    }

    public TableModelBreaksTime() {
        DATA = new ArrayList[COLUMNNAMES.length];
        for (int i = 0; i < COLUMNNAMES.length; i++) {
            DATA[i] = new ArrayList();
        }
        for (int i = 0; i < COLUMNNAMES.length; i++) {
            if (i == 1 || i == 2) {
                DATA[i].add(new Date());
            }
        }
    }

    @Override
    public String getColumnName(int col) {
        return COLUMNNAMES[col];
    }

    @Override
    public int getRowCount() {
        return DATA[0].size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNNAMES.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
            case 2:
                return Date.class;
            case 3:
                return String.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return DATA[columnIndex].get(rowIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        DATA[columnIndex].set(rowIndex, aValue);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 0;
    }

    public void addNewRow() {
        for (int i = 0; i < COLUMNNAMES.length; i++) {
            if (i == 1 || i == 2) {
                DATA[i].add(new Date());
            } else {
                DATA[i].add("");
            }
        }
        this.fireTableRowsInserted(0, DATA[0].size() - 1);
    }

    public void removeNewRow() {
        for (int i = 0; i < COLUMNNAMES.length; i++) {
            DATA[i].remove(DATA[i].size() - 1);
        }
        this.fireTableRowsDeleted(0, DATA[0].size() - 1);
    }

    public void removeNewRow(int index) {
        for (int i = 0; i < COLUMNNAMES.length; i++) {
            DATA[i].remove(index);
        }
        this.fireTableRowsDeleted(0, DATA[0].size() - 1);
    }
}
