package smartfactoryV2;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Victor Kadiata
 */
public class ExtensionFilter extends FileFilter {

    private String extensions[];
    private String description;

    public ExtensionFilter(String description, String extension) {
        this(description, new String[]{extension});
    }

    public ExtensionFilter(String description, String extensions[]) {
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
