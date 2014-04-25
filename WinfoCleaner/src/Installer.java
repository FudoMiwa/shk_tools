import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;


public class Installer {
	private final static String STARTUP_PATH = "C:\\Users\\Pohligpool\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
	
	public static void main(String[] args) {
		String[] filenames = {"WinfoCleaner.jar", "RecycleBinCleaner.cmd"}; //Name der Files die Installiert werden sollen
		
		String sourcePath = findInstallerPath(filenames);
		
		try {
			for(String filename : filenames)
				Files.copy(Paths.get(sourcePath, filename), Paths.get(STARTUP_PATH, filename));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);;
			e.printStackTrace();
		}
		
		printStartupContent();
	}
	
	private static void printStartupContent(){
		File startup = new File(STARTUP_PATH);
		JOptionPane.showMessageDialog(null, startup.list());
	}
	
	/**
	 * Gibt den Pfad zurueck indem sich die zu installierenden Files befinden.
	 * @return
	 */
	private static String findInstallerPath(String[] filenames){
		String ret = "";
		boolean run = true;
		File[] drives = File.listRoots();
		File[] subDirs;
		
		for(int i = 0; i < drives.length && run; i++){
			subDirs = drives[i].listFiles();
		
			if(subDirs != null){
				for(int j = 0; j < subDirs.length; j++){
					if(subDirs[i].getName() == filenames[0]){
						ret = drives[i].getName();
						run = false;
					}
				}
			}
		}
		
		return ret;
	}
}
