package testCode;

/**
 *
 * @author Victor Kadiata
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.Enumeration;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class HighlightTreeNodeTest {

    private final JTree tree = new JTree();
    private final JTextField field = new JTextField("foo");
//    private final JButton button = new JButton();
    private final MyTreeCellRenderer renderer
            = new MyTreeCellRenderer(tree.getCellRenderer());

    public JComponent makeUI() {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fireDocumentChangeEvent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fireDocumentChangeEvent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        tree.setCellRenderer(renderer);
        renderer.q = field.getText();
        fireDocumentChangeEvent();
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        p.add(field, BorderLayout.NORTH);
        p.add(new JScrollPane(tree));
        return p;
    }

    private void fireDocumentChangeEvent() {
        String q = field.getText();
        renderer.q = q;
        TreePath root = tree.getPathForRow(0);
        collapseAll(tree, root);
        if (!q.isEmpty()) {
            searchTree(tree, root, q);
        }
        tree.repaint();
    }

    private static void searchTree(JTree tree, TreePath path, String q) {
        TreeNode node = (TreeNode) path.getLastPathComponent();
        if (node == null) {
            return;
        }
        if (node.toString().startsWith(q)) {
            tree.expandPath(path.getParentPath());
        }
        if (!node.isLeaf() && node.getChildCount() >= 0) {
            Enumeration e = node.children();
            while (e.hasMoreElements()) {
                searchTree(tree, path.pathByAddingChild(e.nextElement()), q);
            }
        }
    }

    private static void collapseAll(JTree tree, TreePath parent) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (!node.isLeaf() && node.getChildCount() >= 0) {
            Enumeration e = node.children();
            while (e.hasMoreElements()) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                collapseAll(tree, path);
            }
        }
        tree.collapsePath(parent);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.getContentPane().add(new HighlightTreeNodeTest().makeUI());
        f.setSize(320, 240);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}

class MyTreeCellRenderer extends DefaultTreeCellRenderer {

    private final TreeCellRenderer renderer;
    public String q;

    MyTreeCellRenderer(TreeCellRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public Component getTreeCellRendererComponent(
            JTree tree, Object value, boolean isSelected, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {
        JComponent c = (JComponent) renderer.getTreeCellRendererComponent(
                tree, value, isSelected, expanded, leaf, row, hasFocus);
        if (isSelected) {
            c.setOpaque(false);
            c.setForeground(getTextSelectionColor());
        } else {
            c.setOpaque(true);
            if (q != null && !q.isEmpty() && value.toString().startsWith(q)) {
                c.setForeground(getTextNonSelectionColor());
                c.setBackground(Color.YELLOW);
            } else {
                c.setForeground(getTextNonSelectionColor());
                c.setBackground(getBackgroundNonSelectionColor());
            }
        }
        return c;
    }
}
