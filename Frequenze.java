import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
	
public class Frequenze {
	static String inputDir = "D:\\Download\\pgdvd042010";
	static String outputDir = "D:\\Documenti\\Università\\Informatica Umanistica\\Programmazione e analisi di dati - Romani 1819\\out\\";
	final static String delim =" ,.;?!\"\'"; // i delimitatori delle parole
	private Vector<Integer> vocabularySize = new Vector<> ();
	private Vector<Integer> corpusSize = new Vector<> ();
	public int nToken=0;
	public Frequenze() throws IOException{
		String indirizzoFile=inputDir+"\\"+ "Freq.txt";
		String indizizzoZipf=inputDir+"\\"+ "Zipf.txt";
		String indirizzoHeaps=inputDir+"\\"+ "Heaps.txt";
		PrintWriter outFile = new PrintWriter(new BufferedWriter(new
				FileWriter(indirizzoFile, true)));
		Map<Object,Integer> h = new HashMap<Object,Integer>();
		ScanDirectory sd = new ScanDirectory(outputDir,".txt");
		for(String fileName : sd.getList()) read(fileName, h);
		
		Map<Object, Integer> frequencyDictionarySorted = h.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry
				.comparingByValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e2, LinkedHashMap::new));
		
		try {
			for (Map.Entry e : frequencyDictionarySorted.entrySet()) {
				outFile.write(e.getKey() + "\t" + e.getValue() + "\n");
			}
		} catch(Exception e) {
				e.printStackTrace();
		}
		
		outFile.close();
		
		try {
			PrintWriter Zipf = new PrintWriter(new BufferedWriter(new
					FileWriter(indizizzoZipf, true)));
			
			final Vector<Integer> valori = new Vector<> ();
			
			for (Map.Entry e : frequencyDictionarySorted.entrySet()) {
				valori.add((int)e.getValue());
			}
			
			
			int frequenza = 1;
			int i = 0;
			while (i < valori.size()) {
				if (i != valori.size()-1) {
					if ((int)valori.get(i) == (int)valori.get(i+1)) {
						frequenza++;
						i++;
					} else {
						Zipf.write(frequenza + "\t" + valori.get(i) + "\n");
						frequenza = 1;
						i++;
					}
				} else {
					Zipf.write(frequenza + "\t" + valori.get(i) + "\n");
					frequenza = 1;
					i++;	
				}
					
			}
				
			Zipf.close();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
			
			
		
		try {
			PrintWriter Heap = new PrintWriter(new BufferedWriter(new
					FileWriter(indirizzoHeaps, true)));
			
			for (int i = 0; i < vocabularySize.size();i++) {
				Heap.write(vocabularySize.get(i) + "\t" + corpusSize.get(i) + "\n");
			}
			Heap.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	void add(Map<Object,Integer> m, Object v){
		Integer o = m.get(v);
		if(o==null) m.put(v,1);
		else m.put(v,o+1);
	}
	
	void read(String inp, Map<Object,Integer> h){
	try {
		BufferedReader in = new BufferedReader(new FileReader(outputDir+"/"+inp));
		String line = in.readLine();
		while(line!=null){
			StringTokenizer st = new StringTokenizer(line, delim);
			while(st.hasMoreTokens()){
				add(h, st.nextToken());
				nToken++;
			}
			line = in.readLine();
		}
		
		vocabularySize.add(h.size());
		corpusSize.add(nToken);
		
	} catch(IOException e) {
		e.printStackTrace();
	}
	}
	public static void main (String [] args) throws IOException {
	 Frequenze f=new Frequenze();
	}
}
