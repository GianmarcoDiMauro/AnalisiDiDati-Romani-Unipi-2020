import java.io.File;
import java.io.FilenameFilter;

public class ScanDirectory implements FilenameFilter {
	private String ends;
    private String[] list;
    
    public ScanDirectory(final String dirName, final String ends) {
        this.ends = ends;
        this.list = new File(dirName).list(this);
    }
    
    public ScanDirectory(final String dirName) {
        this(dirName, null);
    }
    
    public String[] getList() {
        return this.list;
    }
    
    @Override
    public boolean accept(final File dir, final String name) {
        return this.ends == null || name.toLowerCase().endsWith(this.ends.toLowerCase());
    }
}
