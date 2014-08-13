package target;

import com.jidesoft.grid.SortableTable;
import java.awt.Component;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Victor Kadiata
 */
public class TableTimeSpinner {

    private static void createAndShowUI() {
        JFrame frame = new JFrame("TableTimeSpinner");
        frame.getContentPane().add(new TableTimeSpinner().getComponent());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowUI();
            }
        });
    }

    private final Object[][] data = {{"John Smtih", new Date()}, {"Mickey Mouse", new Date()},
    {"Frank Stein", new Date()}, {"Lizzy Borden", new Date()}};

    private final JPanel mainPanel = new JPanel();
    private final DefaultTableModel tableModel = new MyTableModel();
    private final SortableTable table = new SortableTable(tableModel);
//    private final JTable table = new JTable(tableModel);

    public TableTimeSpinner() {
        try {
            setUpTable();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mainPanel.add(new JScrollPane(table));
    }

    private void setUpTable() throws ParseException {
        table.getColumnModel().getColumn(1).setCellRenderer(new MyTimeCellRenderer());
        table.getColumnModel().getColumn(1).setCellEditor(new MyTimeCellEditor());
        for (Object[] objArray : data) {
            tableModel.addRow(objArray);
        }
    }

    public JComponent getComponent() {
        return mainPanel;
    }
}

@SuppressWarnings("serial")
class MyTableModel extends DefaultTableModel {

    public static final String[] COLUMN_NAMES = {"Name", "Time"};

    MyTableModel() {
        super(COLUMN_NAMES, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 1) {
            return Date.class;
        }
        return super.getColumnClass(columnIndex);
    }
}

@SuppressWarnings("serial")
class MyTimeCellRenderer extends DefaultTableCellRenderer {

    DateFormat formatter;

    MyTimeCellRenderer() {
        this.formatter = new SimpleDateFormat("hh:mm a");
    }

    @Override
    public void setValue(Object value) {
        setText((value == null) ? "" : formatter.format(value));
    }
}

@SuppressWarnings("serial")
class MyTimeCellEditor extends AbstractCellEditor implements TableCellEditor {

    private static final String MID_TIME = "12:01 PM";
    private static final String BOTTOM_TIME = "00:01 AM";
    private static final String TOP_TIME = "11:59 PM";

    private final SpinnerDateModel spinnerModel;
    private final JSpinner dateSpinner;

    MyTimeCellEditor() throws ParseException {
        SimpleDateFormat sdfWithDefaultYear = new SimpleDateFormat("hh:mm a");
        Date midDate = sdfWithDefaultYear.parse(MID_TIME);
        Date bottomDate = sdfWithDefaultYear.parse(BOTTOM_TIME);
        Date topDate = sdfWithDefaultYear.parse(TOP_TIME);
        spinnerModel = new SpinnerDateModel(midDate, bottomDate, topDate, Calendar.MINUTE);
        dateSpinner = new JSpinner(spinnerModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "hh:mm a"));
    }

    @Override
    public Object getCellEditorValue() {
        return spinnerModel.getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        dateSpinner.setValue(value);
        return dateSpinner;
    }
}
