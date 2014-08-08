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
 * @author Makumbe
 */
public class TableModelShiftTime extends AbstractTableModel implements HeaderStyleModel {

    private static final CellStyle CENTER_STYLE = new CellStyle();
    private static final CellStyle PERIOD_STYLE = new CellStyle();
    String[] columnNames = {"#", "From", "To"};
    ArrayList[] Data;

    static {
        CENTER_STYLE.setForeground(Color.gray);
        CENTER_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
        PERIOD_STYLE.setFontStyle(Font.BOLD);
        PERIOD_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public TableModelShiftTime(int taille) {
        Data = new ArrayList[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            Data[i] = new ArrayList();
        }
        for (int i = 0; i < columnNames.length; i++) {
            for (int j = 0; j < taille; j++) {
                if (i == 0) {
                    Data[i].add(j, j + 1);
                } else {
                    Data[i].add(j, "00:00");
                }
            }
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
    public Class getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
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
        return col != 0;
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
        for (int i = 0; i < 1; i++) {
            Data[i].add(Data[i].size());
        }
        for (int i = 1; i < columnNames.length; i++) {
            Data[i].add(Data[i].size(), "00:00");
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
