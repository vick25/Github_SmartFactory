package tableModel;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.grid.CellStyle;
import com.jidesoft.grid.ContextSensitiveTableModel;
import com.jidesoft.grid.EditorContext;
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
public class TableModelEventData extends AbstractTableModel implements ContextSensitiveTableModel, HeaderStyleModel {

    public static void setCustomCodeValue(String customCodeValue) {
        TableModelEventData.customCodeValue = customCodeValue;
    }

    private static final CellStyle CENTER_STYLE = new CellStyle();
    private static final CellStyle TIME_STYLE = new CellStyle();
    private static final CellStyle PERIOD_STYLE = new CellStyle();
    private static String customCodeValue;

    private final String[] COLUMN_NAMES = {"#", "Start Time", "Until Time", customCodeValue, "Total parts"};
    private final ArrayList[] DATA;

    public TableModelEventData() {
        DATA = new ArrayList[COLUMN_NAMES.length];
        for (int i = 0; i < COLUMN_NAMES.length; i++) {
            DATA[i] = new ArrayList();
        }
    }

    static {
        CENTER_STYLE.setForeground(Color.GRAY);
        CENTER_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
        TIME_STYLE.setForeground(new Color(0, 128, 0));
        TIME_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
        PERIOD_STYLE.setFontStyle(Font.BOLD);
        PERIOD_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public int getRowCount() {
        return DATA[0].size();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public ConverterContext getConverterContextAt(int row, int column) {
        return null;
    }

    @Override
    public EditorContext getEditorContextAt(int row, int column) {
        return null;
    }

    @Override
    public Class<?> getCellClassAt(int row, int column) {
        switch (column) {
            case 0:
                return Integer.class;
            case 1:
            case 2:
            case 3:
                return String.class;
            case 4:
                return Double.class;
        }
        return super.getColumnClass(column);
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        return DATA[column].get(row);
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        DATA[col].set(row, value);
        fireTableCellUpdated(row, col);
    }

    @Override
    public CellStyle getHeaderStyleAt(int rowIndex, int columnIndex) {
        if (rowIndex == 0) {
            return PERIOD_STYLE;
        } else if (columnIndex <= 4) {
            return TIME_STYLE;
        }
        return CENTER_STYLE;
    }

    @Override
    public boolean isHeaderStyleOn() {
        return true;
    }

    public int getPreferredWidth(int column) {
        return -1;
    }

    public void addNewRow() {
        for (int i = 0; i < COLUMN_NAMES.length; i++) {
            DATA[i].add("");
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
