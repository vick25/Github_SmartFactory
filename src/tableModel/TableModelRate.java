package tableModel;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.grid.CellPainter;
import com.jidesoft.grid.CellStyle;
import com.jidesoft.grid.ContextSensitiveTableModel;
import com.jidesoft.grid.EditorContext;
import com.jidesoft.grid.HeaderStyleModel;
import com.jidesoft.grid.StyleModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Victor Kadiata
 */
public class TableModelRate extends AbstractTableModel implements ContextSensitiveTableModel, HeaderStyleModel, 
        StyleModel {

    private static final CellStyle CENTER_STYLE = new CellStyle();
    private static final CellStyle TIME_STYLE = new CellStyle();
    private static final CellStyle PERIOD_STYLE = new CellStyle();
    private final static CellStyle CELL_STYLE_UNDERLAY = new CellStyle();

    String[] columnNames = {"Machine(s)", "Current", "Last hour", "Daily", "Weekly", "MTD", "YTD"};
    ArrayList[] Data;

    public TableModelRate() {
        Data = new ArrayList[columnNames.length];
        for (int i = 0; i < columnNames.length; i++) {
            Data[i] = new ArrayList();
        }
    }

    static {
        CENTER_STYLE.setForeground(Color.gray);
        CENTER_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
        TIME_STYLE.setForeground(new Color(0, 128, 0));
        TIME_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
        PERIOD_STYLE.setFontStyle(Font.BOLD);
        PERIOD_STYLE.setHorizontalAlignment(SwingConstants.CENTER);
    }

    static {
        CELL_STYLE_UNDERLAY.setUnderlayCellPainter(new CellPainter() {
            @Override
            public void paint(Graphics g, Component component, int row, int column, Rectangle cellRect, Object value) {
//                try {
//                    value = Double.parseDouble(value.toString());
//                    if (value instanceof Double) {
//                        Graphics2D g2d = (Graphics2D) g.create();
//                        double dv = (Double) value;
//                        Rectangle clip = new Rectangle(cellRect.x, cellRect.y, (int) (cellRect.width * 100.0 / 100.0), cellRect.height);
//                        g2d.clip(clip);
//                        if (dv > 10.0) {
//                            JideSwingUtilities.fillGradient(g2d, cellRect,
//                                    new Color(147, 98, 184), new Color(229, 193, 255), false);
//                        } else if (dv > 5.0) {
//                            JideSwingUtilities.fillGradient(g2d, cellRect,
//                                    new Color(173, 135, 24), new Color(246, 218, 135), false);
//                        } else {
//                            JideSwingUtilities.fillGradient(g2d, cellRect,
//                                    new Color(75, 126, 176), new Color(170, 208, 246), false);
//                        }
//                        g2d.dispose();
//                    }
//                } catch (NumberFormatException e) {
//                }
            }
        });
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
                return String.class;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return Double.class;
        }
        return super.getColumnClass(column);
//        return getColumnClass(column);
    }

    @Override
    public CellStyle getCellStyleAt(int rowIndex, int columnIndex) {
        if (columnIndex == 2) {
            return CELL_STYLE_UNDERLAY;
        }
        return null;
    }

    @Override
    public boolean isCellStyleOn() {
        return true;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        return Data[column].get(row);
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
        } else if (columnIndex == 0) {
            return PERIOD_STYLE;
        } else if (columnIndex <= 7) {
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
