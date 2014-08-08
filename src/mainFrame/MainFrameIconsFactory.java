package mainFrame;

import com.jidesoft.icons.IconsFactory;
import javax.swing.ImageIcon;

public class MainFrameIconsFactory {

    public static class Standard {

        // ---- file menu
        public static final String NEW = "/images/icons/document_new.png";
        public static final String OPEN = "/images/icons/document_open16x16.png";
        public static final String SAVE = "/images/icons/document_save.png";
        public static final String SAVEALL = "/images/icons/document_save_all.png";
//        public static final String LOGOUT = "/images/icons/b_usrdrop.png";
        public static final String LOGOUT = "/images/icons/logout16x16.png";
        public static final String EXIT = "/images/icons/exit(5).png";
        // ---- edit menu
        public static final String UNDO = "/images/icons/undo16.png";
        public static final String REDO = "/images/icons/redo16.png";
        public static final String CUT = "/images/icons/cut.gif";
        public static final String COPY = "/images/icons/copy.gif";
        public static final String PASTE = "/images/icons/paste.gif";
        // ---- tools menu
        public static final String EXPORTDATA = "/images/icons/vcs_commit.png";
//        public static final String USER = "/images/icons/user-info(3).png";
        public static final String USER = "/images/icons/add_user16x16_1.png";
        public static final String CHANGEPASSWORD = "/images/icons/password16.png";
        // ----options menu
        public static final String SEARCHEDIT = "/images/icons/search16x16.png";
        public static final String DOC = "/images/icons/bookcase16x16.png";
        public static final String TEMPLATE = "/images/icons/bookmark-add16.png";
        public static final String STAT = "/images/icons/kchart(10).png";
        public static final String SETTING = "/images/icons/advancedsettings(1).png";
        // ----help menu
        public static final String ABOUT = "/images/icons/document-properties(9).png";
        public static final String CHECKUPDATE = "/images/icons/browser(17).png";
        public static final String HELP = "/images/icons/help-browser(18).png";
    }

    public static class ShortCut {

        public static final String SHORTCUT = "/images/icons/display(14).png";
    }

    public static ImageIcon getImageIcon(String name) {
        if (name != null) {
            return IconsFactory.getImageIcon(MainFrameIconsFactory.class, name);
        } else {
            return null;
        }
    }
}
