
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import net.lingala.zip4j.unzip.Unzip;

@ManagedBean
@SessionScoped
@RequestScoped
public class JavaBean {

    private String folder;
    private String destination;
    private Part file;
    private boolean pathExists;
    private boolean zip;
    private String label;

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setZip(boolean zip) {
        this.zip = zip;
    }

    public boolean isZip() {
        return zip;
    }

    public boolean isPathExists() {
        return pathExists;
    }

    public void setPathExists(boolean pathExists) {
        this.pathExists = pathExists;
    }

    public void setDestination(String Destination) {
        this.destination = Destination;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getDestination() {
        return destination;
    }

    public void setFolder(String Folder) {
        this.folder = Folder;
    }

    public String getFolder() {
        return folder;
    }

    public void zipFolder() throws IOException {

        File directoryToZip = new File(folder);

        List<File> fileList = new ArrayList<File>();
        ZipDirectory.getAllFiles(directoryToZip, fileList);
        ZipDirectory.writeZipFile(directoryToZip, fileList);
    }

    public void unzipFolder(String File2Unzip, String Directory) throws IOException {
        UnzipDirectory unzipFile = new UnzipDirectory();
        unzipFile.unzip(File2Unzip, Directory);

    }

    public void deleteFile(File File2Delete) {
        try {
            File2Delete.delete();
        } catch (Exception e) {

        }

    }

    public void checkFolderPath() {

        if (destination != "") {
            String substring = destination.substring(Math.max(destination.length() - 1, 0));
            if (!substring.equals("\\")) {
                label = "Path should be ..\\folder\\";
            } else {
                File F = new File(destination);
                pathExists = F.exists();
                if (pathExists) {
                    label = "  Path is valid!";
                } else {
                    label = "  Folder not found!";
                }

            }
        }
    }

    public void upload() throws IOException {

//        if (pathExists) {
            file.write(destination + getFileName(file));

//            if (zip) {

                String file2Unzip = destination + "/" + getFileName(file);
                String folderPath = file2Unzip.substring(0, file2Unzip.lastIndexOf('.'));
                unzipFolder(file2Unzip, folderPath);

                File ZipFile2Delete;
                ZipFile2Delete = new File(file2Unzip);
                deleteFile(ZipFile2Delete);
//            }
        }
//    }

    public String getFileName(Part part) {
        for (String cd : part.getHeader("Content-Disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }
        return null;

    }

}
