package prog3;

import java.io.IOException;

import prog3.Subtitles.SomethingBad;

public class Main {
	public static void main(String[] arg){
		Subtitles sub = new Subtitles();
		
		try {	
				sub.delay("C:\\Users\\Jack\\Desktop\\gravity.txt",
				"C:\\Users\\Jack\\Desktop\\napisy.txt",-12, 24);
				 
			} catch (IOException e) {
				System.out.println("Blad otwierania pliku");
				System.exit(0);
			} catch (SomethingBad e) {
				System.out.println(e.getMessage());
				System.exit(0);
			} catch (Exception e) {
				System.out.println("Unknown exception");
				System.exit(0);
			}
	}
}
