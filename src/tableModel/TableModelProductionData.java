package tableModel;

import com.jidesoft.grid.CellStyle;
import com.jidesoft.grid.HeaderStyleModel;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor Kadiata
 */
public class TableModelProductionData extends AbstractTableModel implements HeaderStyleModel {

    private static final CellStyle CENTER_STYLE = new CellStyle();
    private static final CellStyle PERIOD_STYLE = new CellStyle();
    String[] columnNames = {"#", "Day", "Time", "Value"};
    ArrayList[] Data;

    static {
        CENTER_STYLE.setForeground(Color.GRAY);
        CENTER_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
        PERIOD_STYLE.setFontStyle(Font.BOLD);
        PERIOD_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public TableModelProductionData() {
        Data = new ArrayList[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            Data[i] = new ArrayList();
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return Data[0].size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return Data[col].get(row);
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return (false);
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        Data[col].set(row, value);
        fireTableCellUpdated(row, col);
    }

    @Override
    public CellStyle getHeaderStyleAt(int rowIndex, int columnIndex) {
        if (rowIndex == 0) {
            return PERIOD_STYLE;
        } else if (columnIndex <= 3) {
            return PERIOD_STYLE;
        }
        return CENTER_STYLE;
    }

    @Override
    public boolean isHeaderStyleOn() {
        return true;
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
