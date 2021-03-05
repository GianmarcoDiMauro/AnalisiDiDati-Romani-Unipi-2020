import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class GUTStrip {
	private String[] fileList;
    static String inputDir;
    static String unzipDir;
    static String outputDir;
    
    static {
        GUTStrip.inputDir = "D:\\Download\\pgdvd042010";
        GUTStrip.unzipDir = "D:\\Documenti\\Università\\Informatica Umanistica\\Programmazione e analisi di dati - Romani 1819\\unzip";
        GUTStrip.outputDir = "D:\\Documenti\\Università\\Informatica Umanistica\\Programmazione e analisi di dati - Romani 1819\\out\\";
    }
    
    public GUTStrip() {
        this.fileList = new ScanDirectory(GUTStrip.unzipDir, ".txt").getList();
        if (this.fileList == null) {
            throw new RuntimeException("Nessun file trovato");
        }
    }
    
    public void strip() {
    	int c = 0;
        try {
            new File(GUTStrip.outputDir).mkdir();
            final String[] x = new ScanDirectory(GUTStrip.outputDir, ".txt").getList();
            if (x != null && x.length != 0) {
                System.out.println("Esistono gi\u00e0 dei file ripuliti");
                return;
            }
            while (c < this.fileList.length) {
            	try {
            		String line = "";
            		String encoding ="";
            		final String filePath = String.valueOf(GUTStrip.unzipDir) + "\\" + this.fileList[c];
            		final String newFilePath = String.valueOf(GUTStrip.outputDir) + "\\" + this.fileList[c];
            		final BufferedReader br = new BufferedReader(new FileReader(filePath));
            		final PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(newFilePath, true)));
            		boolean english = false;
            		boolean skip = true;
            		while ((line = br.readLine()) != null) {
            			if (line.startsWith("Character set encoding")) {
            				String[] y = line.split(": ");
            				encoding = y[1];
            			}
            		}
            		BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
            		while ((line = br2.readLine()) != null) {
            			if (!skip) {
            				out.println(line);
            			}
                    
            			if (line.startsWith("Language: English")) {
            				english = true;
            			}
            			if (english) {
            				if (line.startsWith("***START OF") || line.startsWith("*** START OF")) {
            					skip = false;
            				}
            				if (line.startsWith("***END OF")) {
            					break;
            				}
            				if (line.startsWith("*** END OF")) {
            					break;
            				}
            			}
                    
            		}
            		c++;
            		br.close();
            		br2.close();
            		out.close();
            	}  catch (UnsupportedEncodingException e3) {
            		c++;
            	}
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        catch (IOException e2) {
            e2.printStackTrace();
        }
        System.out.println("Pulizia dei file terminata!");
    }
    
    public static void main(final String[] args) {
        final long inizio = System.currentTimeMillis();
        final GUTStrip pulizia = new GUTStrip();
        pulizia.strip();
        final long fine = System.currentTimeMillis();
        final long tempoVero = (fine - inizio) / 1000L;
        System.out.println(" Impiegati " + tempoVero + " secondi ");
    }
}
