import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;


public class Installer {
	private static String user = "";
	private static ButtonGroup group = new ButtonGroup();
	private static JFrame frame = new JFrame("Select user");
	private static String startupPath;
	
	public static void main(String[] args) {
		String[] filenames = {"WinfoCleaner.jar", "RecycleBinCleaner.cmd"}; //Name der Files die Installiert werden sollen
		setUser();
		startupPath = "C:\\Users\\"+user+"\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
		
		String sourcePath = findInstallerPath(filenames);
		
		try {
			for(String filename : filenames)
				Files.copy(Paths.get(sourcePath, filename), Paths.get(startupPath, filename));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
		
		printStartupContent();
	}
	
	private static void printStartupContent(){
		File startup = new File(startupPath);
		JOptionPane.showMessageDialog(null, startup.list());
		System.exit(0);
	}
	
	private static void setUser(){
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		File usersDir= new File("C:\\Users");
		JRadioButton button;
		frame.getContentPane().setLayout(new GridLayout(usersDir.listFiles().length+2, 1));
		
		frame.getContentPane().add(new JLabel("Bitte User w\u00e4hlen f\u00fcr den die Programme installiert werden sollen"));
		
		for(File file : usersDir.listFiles()){
			button = new JRadioButton(file.getName());
			group.add(button);

			frame.getContentPane().add(button);
		}
		
		JButton button2 = new JButton("Ok");
		
		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Enumeration<AbstractButton> enu = group.getElements();
				
				while(enu.hasMoreElements()){
					AbstractButton button = enu.nextElement();
					
					if(button.isSelected())
						user = button.getText();
				}
				frame.setVisible(false);
			}
		});

		frame.getContentPane().add(button2);
		frame.pack();
		frame.setVisible(true);
		while(frame.isVisible());
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
