package prog3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Subtitles {

	private int counter=1;
	
	public void delay(String in, String out, int delay, int fps) throws IOException, SomethingBad{
		File input = new File(in);
		File output = new File(out);
		
		if((!input.exists()) || (!output.exists()))
			throw new SomethingBad("0", counter, "file doesn't exist");
		
		FileReader fr = new FileReader(input);
		BufferedReader br = new BufferedReader(fr);
		
		FileWriter fw = new FileWriter(output);
		BufferedWriter bw = new BufferedWriter(fw);
		String line;
		
		int howMany = delay*fps;
		
		try{
			while((line=br.readLine())!=null){
				line = change(line, howMany);
				bw.write(line);
				bw.write(System.getProperty( "line.separator" ));
				counter++;
			}
		}
		finally{
			bw.flush();
			bw.close();
			fw.close();
			br.close();
			fr.close();
			counter=1;
		}
	}
	
	private String change(String line, int snapShots) throws SomethingBad{

		if(!line.matches("[\\{][0-9]*[\\}][\\{][0-9]*[\\}].*")) throw new SomethingBad(line, counter, "regex doesn't match");
		
		String pattern = "[\\{]([0-9]*)[\\}][\\{]([0-9]*)[\\}](.*)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(line);
		int first=0, second=0;
		
		if(m.find())
		{
			first = Integer.parseInt(m.group(1));
			second = Integer.parseInt(m.group(2));
			
			if(first>=second)
				throw new SomethingBad(line, counter, "Subtitle ends before it starts");
			first+=snapShots;
			second+=snapShots;
			if(first<0)
				first=0;
			if(second<0)
				second=0;
		}
		String s = "{"+first+"}{"+second+"}"+m.group(3);
		
		return s;
	}
	
	
	class SomethingBad extends Exception{
		private String message;
		private String type;
		public SomethingBad(String s, int line, String type){
			message = "line: "+line+" ["+type+"]   "+s;
			this.type=type;
		}
		public String getMessage(){
			return message;
		}
	}
}
