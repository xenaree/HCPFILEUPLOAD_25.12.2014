
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





@ManagedBean
@SessionScoped
@RequestScoped
public class JavaBean {

    private String folder;
    private String destination;
    private Part file;
    private boolean b;
    public String b1 ="eee";

    public boolean isB() {
        return b;
    }
  public void setB(boolean b) {
        this.b = b;
    }
    public void setB1(String b1) {
        this.b1 = b1;
    }
        public String isB1() {
        return b1;
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

//        File zipFile = new File(directoryToZip+".zip");
        
//        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        
//        UnZip zipFile = new UnZip();
//        zipFile.unZipIt("E:\\MyFilee.zip", "E:\\outputzip2");

//return "success";
//        
//        return "fail";

    }
    
    public String upload() throws IOException{
        
        File F = new File(destination);
       try { 
        b= F.exists();
        SecurityManager s= new SecurityManager();
        s.checkRead(destination);
      }catch(SecurityException e){
      folder= e.getMessage();
      }
//        Process p = Runtime.getRuntime().exec("pwd");
//        String s = p.getInputStream().toString();
//        file.write(destination+getFileName(file));
        return "success";
    }
    
    

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
