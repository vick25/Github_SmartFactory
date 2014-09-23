package smartfactoryV2;

import com.jidesoft.plaf.LookAndFeelFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

/**
 *
 * @author Victor Kadiata
 */
public class EnhancedFileTester extends JPanel {

    private class AnOvalIcon implements Icon {

        Color color;

        AnOvalIcon(Color c) {
            color = c;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillOval(x, y, getIconWidth(), getIconHeight());
        }

        @Override
        public int getIconWidth() {
            return 10;
        }

        @Override
        public int getIconHeight() {
            return 15;
        }
    }

    private class IconView extends FileView {

        private HashMap hash = new HashMap();

        IconView() {
            hash.put("htm", new AnOvalIcon(Color.RED));
            hash.put("html", new AnOvalIcon(Color.GREEN));
            hash.put("java", new AnOvalIcon(Color.BLUE));
        }

        @Override
        public String getName(File f) {
            String s = f.getName();
            if (s.length() == 0) {
                s = f.getAbsolutePath();
            }
            return s;
        }

        @Override
        public String getDescription(File f) {
            return f.getName();
        }

        @Override
        public String getTypeDescription(File f) {
            return f.getAbsolutePath();
        }

        @Override
        public Icon getIcon(File f) {
            String path = f.getAbsolutePath();
            int pos = path.lastIndexOf('.');
            if ((pos >= 0) && (pos < (path.length() - 1))) {
                String ext = path.substring(pos + 1).toLowerCase();
                return (Icon) hash.get(ext);
            }
            return null;
        }

        @Override
        public Boolean isTraversable(File file) {
            return file.isDirectory();
        }
    }

    private class ExtensionFilter extends FileFilter {

        private String extensions[];

        private String description;

        ExtensionFilter(String description, String extension) {
            this(description, new String[]{extension});
        }

        ExtensionFilter(String description, String extensions[]) {
            this.description = description;
            this.extensions = extensions.clone();
        }

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            int count = extensions.length;
            String path = file.getAbsolutePath();
            for (int i = 0; i < count; i++) {
                String ext = extensions[i];
                if (path.endsWith(ext)
                        && (path.charAt(path.length() - ext.length()) == '.')) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            return (description == null ? extensions[0] : description);
        }
    }

    public EnhancedFileTester() {
        JButton jb = new JButton("Open File Viewer");
        jb.setFocusable(false);
        add(jb);
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(".");
                FileFilter type1 = new ExtensionFilter("Java source", ".java");
                FileFilter type2 = new ExtensionFilter("Image files",
                        new String[]{".jpg", ".gif", "jpeg", "xbm"});
                FileFilter type3 = new ExtensionFilter("HTML files",
                        new String[]{".htm", ".html"});
                chooser.addChoosableFileFilter(type1);
                chooser.addChoosableFileFilter(type2);
                chooser.addChoosableFileFilter(type3);
                chooser.setFileFilter(type2); // Initial filter setting
                FileView view = new IconView();
                chooser.setFileView(view);
                int status = chooser.showOpenDialog(EnhancedFileTester.this);
                if (status == JFileChooser.APPROVE_OPTION) {
                    File f = chooser.getSelectedFile();
                    System.out.println(f);
                }
            }
        };
        jb.addActionListener(listener);
    }

    public static void main(String args[]) {
        LookAndFeelFactory.installDefaultLookAndFeel();
        JFrame f = new JFrame("Enhanced File Example");
        JPanel j = new EnhancedFileTester();
        f.getContentPane().add(j, BorderLayout.CENTER);
        f.setSize(300, 200);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
