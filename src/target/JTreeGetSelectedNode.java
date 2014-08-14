package target;

/**
 *
 * @author Victor Kadiata
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class JTreeGetSelectedNode extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JTreeGetSelectedNode().setVisible(true);
            }
        });
    }

    public JTreeGetSelectedNode() throws HeadlessException {
        initializeUI();
    }

    private void initializeUI() {
        setSize(200, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Countries");
        DefaultMutableTreeNode asia = new DefaultMutableTreeNode("Asia");
        String[] countries = new String[]{"India", "Singapore", "Indonesia", "Vietnam"};
        for (String country : countries) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(country);
            asia.add(node);
        }

        DefaultMutableTreeNode northAmerica = new DefaultMutableTreeNode("North America");
        countries = new String[]{"United States", "Canada"};
        for (String country : countries) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(country);
            northAmerica.add(node);
        }

        DefaultMutableTreeNode southAmerica = new DefaultMutableTreeNode("South America");
        countries = new String[]{"Brazil", "Argetina", "Uruguay"};
        for (String country : countries) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(country);
            southAmerica.add(node);
        }

        DefaultMutableTreeNode europe = new DefaultMutableTreeNode("Europe");
        countries = new String[]{"United Kingdom", "Germany", "Spain", "France", "Italy"};
        for (String country : countries) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(country);
            europe.add(node);
        }

        root.add(asia);
        root.add(northAmerica);
        root.add(southAmerica);
        root.add(europe);

        final JTree tree = new JTree(root);
        JScrollPane pane = new JScrollPane(tree);
        pane.setPreferredSize(new Dimension(200, 400));

        JButton button = new JButton("Get Selected");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath[] paths = tree.getSelectionPaths();
                try {
                    for (TreePath path : paths) {
                        System.out.println("You've selected: " + path.getLastPathComponent());
                    }
                } catch (Exception ex) {
                }
            }
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pane, BorderLayout.CENTER);
        getContentPane().add(button, BorderLayout.SOUTH);
    }
}
