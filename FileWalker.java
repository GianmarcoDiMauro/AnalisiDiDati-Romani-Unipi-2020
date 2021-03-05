import java.io.File;
import java.io.IOException;

public class FileWalker {
		// indirizzi delle cartelle
		//CAMBIARE INDIRIZZI
		static String inputDir = "D:\\Download\\pgdvd042010";
		static String unzipDir = "D:\\Documenti\\Università\\Informatica Umanistica\\Programmazione e analisi di dati - Romani 1819\\unzip";
		static String outputDir = "D:\\Documenti\\Università\\Informatica Umanistica\\Programmazione e analisi di dati - Romani 1819\\out";
		
	    public void walk( String path ) {

	        File root = new File( path );
	        File[] list = root.listFiles();
	        String appoggio;
	        UnzipFiles unZip = new UnzipFiles();
	        if (list == null) return;

	        for ( File f : list ) {
	            if ( f.isDirectory() ) {
	            	String name = f.getName();
	            	if (isNumeric(name)) {
	            		walk( f.getAbsolutePath() );
	            	}
	            }
	            else {
	            	appoggio=f.getAbsoluteFile().toString();
	            	if (appoggio.substring(appoggio.length()-4).equals(".ZIP")) {
	                	unZip.unZipIt(appoggio,unzipDir);
	            	}
	            }
	        }
	    }

	    public static void main(String[] args) throws IOException {
	        FileWalker fw = new FileWalker();
	        long inizio = System.currentTimeMillis ();
	        // l'indirizzo della cartella da cui prende i file
	        fw.walk (inputDir);
	        System.out.println("Unzip terminato!");
	        long fine = System.currentTimeMillis ();
			long tempoVero=(fine-inizio)/1000;
			System.out.println (" Impiegati "+tempoVero+" secondi ");
		} 
	    
	    public static boolean isNumeric(final String str) {

	        // null or empty
	        if (str == null || str.length() == 0) {
	            return false;
	        }

	        for (char c : str.toCharArray()) {
	            if (!Character.isDigit(c)) {
	                return false;
	            }
	        }
	        return true;

	    }
	}

