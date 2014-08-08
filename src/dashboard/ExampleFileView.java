package dashboard;

import java.io.File;
import java.util.Hashtable;
import javax.swing.Icon;
import javax.swing.filechooser.FileView;

public class ExampleFileView extends FileView {

    private final Hashtable icons = new Hashtable(5);
    private final Hashtable fileDescriptions = new Hashtable(5);
    private final Hashtable typeDescriptions = new Hashtable(5);

    @Override
    public String getName(File f) {
        return null;
    }

    public void putDescription(File f, String fileDescription) {
        fileDescriptions.put(f, fileDescription);
    }

    @Override
    public String getDescription(File f) {
        return (String) fileDescriptions.get(f);
    }

    public void putTypeDescription(String extension, String typeDescription) {
        typeDescriptions.put(extension, typeDescription);
    }

    public void putTypeDescription(File f, String typeDescription) {
        putTypeDescription(getExtension(f), typeDescription);
    }

    @Override
    public String getTypeDescription(File f) {
        return (String) typeDescriptions.get(getExtension(f));
    }

    public String getExtension(File f) {
        String name = f.getName();
        if (name != null) {
            int extensionIndex = name.lastIndexOf('.');
            if (extensionIndex < 0) {
                return null;
            }
            return name.substring(extensionIndex + 1).toLowerCase();
        }
        return null;
    }

    public void putIcon(String extension, Icon icon) {
        icons.put(extension, icon);
    }

    @Override
    public Icon getIcon(File f) {
        Icon icon = null;
        String extension = getExtension(f);
        if (extension != null) {
            icon = (Icon) icons.get(extension);
        }
        return icon;
    }

    @Override
    public Boolean isTraversable(File f) {
        // if (some_reason) {
        //    return Boolean.FALSE;
        // }
        return null;	// Use default from FileSystemView
    }
}
