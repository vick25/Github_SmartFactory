package target;

/**
 *
 * @author Victor Kadiata
 */
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Main {

    public static void main(String[] argv) throws Exception {
        JTable table = new JTable();
        SelectionListener listener = new SelectionListener(table);
        table.getSelectionModel().addListSelectionListener(listener);
        table.getColumnModel().getSelectionModel().addListSelectionListener(listener);
    }
}

class SelectionListener implements ListSelectionListener {

    JTable table;

    SelectionListener(JTable table) {
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == table.getSelectionModel() && table.getRowSelectionAllowed()) {
            int first = e.getFirstIndex();
            int last = e.getLastIndex();
        } else if (e.getSource() == table.getColumnModel().getSelectionModel()
                && table.getColumnSelectionAllowed()) {
            int first = e.getFirstIndex();
            int last = e.getLastIndex();
        }
        if (e.getValueIsAdjusting()) {
            System.out.println("The mouse button has not yet been released");
        }
    }
}
