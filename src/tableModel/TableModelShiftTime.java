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
    private final String[] COLUMN_NAMES = {"#", "From", "To"};
    private final ArrayList[] DATA;

    static {
        CENTER_STYLE.setForeground(Color.GRAY);
        CENTER_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
        PERIOD_STYLE.setFontStyle(Font.BOLD);
        PERIOD_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public TableModelShiftTime(int taille) {
        DATA = new ArrayList[COLUMN_NAMES.length];
        for (int i = 0; i < COLUMN_NAMES.length; i++) {
            DATA[i] = new ArrayList();
        }
        for (int i = 0; i < COLUMN_NAMES.length; i++) {
            for (int j = 0; j < taille; j++) {
                if (i == 0) {
                    DATA[i].add(j, j + 1);
                } else {
                    DATA[i].add(j, "00:00");
                }
            }
        }
    }

    @Override
    public String getColumnName(int col) {
        return COLUMN_NAMES[col];
    }

    @Override
    public int getRowCount() {
        return DATA[0].size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
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
            DATA[i].add(DATA[i].size());
        }
        for (int i = 1; i < COLUMN_NAMES.length; i++) {
            DATA[i].add(DATA[i].size(), "00:00");
        }
        this.fireTableRowsInserted(0, DATA[0].size() - 1);
    }

    public void removeNewRow() {
        for (int i = 0; i < COLUMN_NAMES.length; i++) {
            DATA[i].remove(DATA[i].size() - 1);
        }
        this.fireTableRowsDeleted(0, DATA[0].size() - 1);
    }

    public void removeNewRow(int index) {
        for (int i = 0; i < COLUMN_NAMES.length; i++) {
            DATA[i].remove(index);
        }
        this.fireTableRowsDeleted(0, DATA[0].size() - 1);
    }
}
