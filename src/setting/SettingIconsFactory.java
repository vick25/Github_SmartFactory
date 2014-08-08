package setting;

import com.jidesoft.icons.IconsFactory;
import javax.swing.ImageIcon;

public class SettingIconsFactory {

    public static class Options {

        public static final String GENERAL = "images/general.png";
        public static final String PRIVACY = "images/privacy.png";
        public static final String WEB = "images/web.png";
        public static final String THEMES = "images/themes.png";
        public static final String LANGUAGE = "images/language.png";
        public static final String FONTSCOLOR = "images/fontscolor.png";
        public static final String CONNECTION = "images/connection.png";
        public static final String DIALOGICON = "images/advancedsettings(1).png";
    }

    public static ImageIcon getImageIcon(String name) {
        if (name != null) {
            return IconsFactory.getImageIcon(SettingIconsFactory.class, name);
        } else {
            return null;
        }
    }

//    public static void main(String[] argv) {
//        IconsFactory.generateHTML(SettingIconsFactory.class);
//    }
}
