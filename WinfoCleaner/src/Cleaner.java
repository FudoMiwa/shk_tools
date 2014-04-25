import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Loeschscript fuer den WInfo-Pool.
 * @author Philipp Klinke
 */
public class Cleaner {
	public static void main(String[] args){
		String USER_PATH = "C:\\Users\\Pohligpool\\";
		
		try {
			recursiveDeleteContent(new File(USER_PATH + "Desktop"));
			recursiveDeleteContent(new File(USER_PATH + "workspace"));
			recursiveDeleteContent(new File(USER_PATH + "Downloads"));
		    
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * Loescht nur den Ordnerinhalt, der Ordner selbst bleibt bestehen.
	 * @param file
	 * @throws IOException
	 */
	private static void recursiveDeleteContent(File file) throws IOException{
		if(file.exists())
			for(File nextFile : file.listFiles())
				recursiveDeleteFile(nextFile);
	}
	
	/**
	 * Loescht gesamten Ordner inkl. Inhalt.
	 * @param file
	 * @throws IOException
	 */
	private static void recursiveDeleteFile(File file) throws IOException{
		if(file.exists()){
			if(file.isDirectory()){
				for(File nextFile : file.listFiles())
					recursiveDeleteFile(nextFile);
			}
			
			file.delete();
		}
	}
}
