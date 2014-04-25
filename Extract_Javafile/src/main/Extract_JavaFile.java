package main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Extract_JavaFile {
	
	
	public static void main(String[] args){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.showOpenDialog(null);
		
		for(File root : fileChooser.getSelectedFile().listFiles().clone()){
			if(root.isDirectory()){
				File javaDir = new File(root.getAbsolutePath() + " - JavaFiles");
				javaDir.mkdir();
				
				for(File studentDir : root.listFiles()){
					try {
						Files.copy(studentDir.toPath(), Paths.get(javaDir.getPath(), studentDir.getName()), StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, e.toString());
					}
					
					handleSubdir(studentDir, Paths.get(javaDir.getPath(), studentDir.getName()));
				}
			}
		}
	}
	
	private static void handleSubdir(File dir, Path studentDir){
		for(File currentFile : dir.listFiles())
			if(currentFile.getName().matches(".*\\.zip"))
				extractArchiveFlat(currentFile);
		
		for(File currentFile : dir.listFiles())
			if(currentFile.getName().matches(".*\\.java")){ 
				try {
					Files.copy(currentFile.toPath(), Paths.get(studentDir + "//" + currentFile.getName()));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e);
					e.printStackTrace();
				}
			}
	}
	
	private static void extractArchiveFlat(File file){
		try {
			ZipFile zipFile = new ZipFile(file);
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			ZipEntry entry;
			InputStream is;
			
			while(entries.hasMoreElements()){
				entry = entries.nextElement();
				if(!entry.isDirectory()){
					is = zipFile.getInputStream(entry);
					String fileName = entry.getName().substring(entry.getName().lastIndexOf("/")+1);
					Files.copy(is, Paths.get(file.getParent() + "\\" + fileName), StandardCopyOption.REPLACE_EXISTING);
				}
			}
			
			zipFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.toString());
		}
	}
}
