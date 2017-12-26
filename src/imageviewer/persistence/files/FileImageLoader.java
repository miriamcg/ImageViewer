package imageviewer.persistence.files;

import imageviewer.model.Image;
import imageviewer.persistence.ImageLoader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

public class FileImageLoader implements ImageLoader{

    private final static String[] ImageExtensions = new String[] {"jpg", "png", "bmp"};
    private final File[] files;

    public FileImageLoader(String folder) {
        this.files = new File(folder).listFiles(withImageExtension());
    }
    
    @Override
    public Image load() {
        return ImageAt(0);
    }
    
    private Image ImageAt(final int index){
        return new Image(){
            @Override
            public byte[] bitmap() {
               try{
                   FileInputStream is = new FileInputStream(files[index]);
                   return read(is);
               } catch (IOException ex) { 
                    return new byte[0];
                } 
            }
            

            private byte[] read(FileInputStream is) throws IOException {
                byte[] buffer = new byte[4096];
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                while(true){
                    int length = is.read(buffer);
                    if(length < 0){
                        break;
                    }
                    os.write(buffer, 0, length);
                }
                return os.toByteArray();
            }

            @Override
            public Image next() {
                return (index < files.length-1) ? ImageAt(index+1) : ImageAt(0);
            }

            @Override
            public Image prev() {
                return (index > 0) ? ImageAt(index-1) : ImageAt(files.length-1);
            }
            
        };
    }  

    private FilenameFilter withImageExtension() {
        return new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                for (String extension : ImageExtensions) {
                    if(name.endsWith(extension)) return true;
                }
                return false;
            }
        };
    }
}
