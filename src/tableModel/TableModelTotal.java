package tableModel;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.grid.CellPainter;
import com.jidesoft.grid.CellStyle;
import com.jidesoft.grid.ContextSensitiveTableModel;
import com.jidesoft.grid.EditorContext;
import com.jidesoft.grid.HeaderStyleModel;
import com.jidesoft.grid.StyleModel;
import com.jidesoft.swing.JideSwingUtilities;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import productionQuickView.ProductionQuickView;
import setting.SettingKeyFactory;
import smartfactoryV2.ConnectDB;

/**
 *
 * @author Victor Kadiata
 */
public class TableModelTotal extends AbstractTableModel implements ContextSensitiveTableModel, HeaderStyleModel,
        StyleModel {

    private static final CellStyle CENTER_STYLE = new CellStyle();
    private static final CellStyle TIME_STYLE = new CellStyle();
    private static final CellStyle PERIOD_STYLE = new CellStyle();
    private final static CellStyle CELL_STYLE_UNDERLAY = new CellStyle();
    private static double machineTarget;

    private final String[] columnNames = {"Machine(s)", "Current {Last Data:sp}", "Last hour", "Daily", "Weekly", "MTD", "YTD"};
    private final ArrayList[] Data;

    public TableModelTotal() {
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
                try {
                    if (column == 0) {
                        try {
                            machineTarget = ConnectDB.getMachineTarget(value.toString(), "Cumulative");
                            if (null != ConnectDB.pref.get(SettingKeyFactory.DefaultProperties.TARGET_TIME_UNIT, "hour")) {
                                switch (ConnectDB.pref.get(SettingKeyFactory.DefaultProperties.TARGET_TIME_UNIT, "hour")) {
                                    case "second":
                                        machineTarget *= 3600;
                                        break;
                                    case "minute":
                                        machineTarget *= 60;
                                        break;
                                }
                            }
                        } catch (SQLException ex) {
                            ConnectDB.catchSQLException(ex);
                        }
                    }
                    Double valueD = Double.valueOf(value.toString());
                    if (valueD instanceof Double) {
                        Graphics2D g2d = (Graphics2D) g.create();
                        Rectangle clip = new Rectangle(cellRect.x, cellRect.y, (int) (cellRect.width * 100.0 / 100.0),
                                cellRect.height);
                        g2d.clip(clip);
                        if (valueD >= machineTarget) {//Green new Color(147, 98, 184)
                            JideSwingUtilities.fillGradient(g2d, cellRect,
                                    Color.GREEN, new Color(229, 193, 255), false);
                        } else if ((valueD * 0.05) <= machineTarget && machineTarget <= (valueD * 0.75)) {
                            //Amber or Orange new Color(173, 135, 24)
                            JideSwingUtilities.fillGradient(g2d, cellRect,
                                    Color.ORANGE, new Color(246, 218, 135), false);
                        } else if (valueD < machineTarget) {//Red new Color(75, 126, 176)
                            JideSwingUtilities.fillGradient(g2d, cellRect,
                                    Color.RED, new Color(170, 208, 246), false);
                        } else {
                            JideSwingUtilities.fillGradient(g2d, cellRect,
                                    new Color(255, 255, 255), new Color(255, 255, 255), false);
                        }
                        g2d.dispose();
                    }
                } catch (NumberFormatException e) {
                }
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
    }

    @Override
    public CellStyle getCellStyleAt(int rowIndex, int columnIndex) {
        if (ProductionQuickView.isTotProdSelected()) {
            if (columnIndex == 0 || columnIndex == 2) {
                return CELL_STYLE_UNDERLAY;
            }
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
