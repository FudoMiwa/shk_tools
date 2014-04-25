import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class CreateUser {

	public static void main(String[] args) {
		BufferedReader br;
		File output = new File("sqlCommand.txt");
		FileWriter fw;
		
		try {
			output.createNewFile();
			fw = new FileWriter("sqlCommand.txt");
			br = new BufferedReader(new FileReader("teampasswds.txt"));
			int j = 0;
			while(br.ready()){
				String s = br.readLine();
				int i = s.indexOf(':');
				String user = s.substring(0, i);
				String pw = s.substring(i+1);
				
				fw.write("CREATE USER '"+user+"'@'localhost' IDENTIFIED BY '"+pw+"';");
				fw.write("GRANT USAGE ON * . * TO '"+user+"'@'localhost' IDENTIFIED BY '"+pw+"' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;");
				fw.write("CREATE DATABASE IF NOT EXISTS `"+user+"` ;");
				fw.write("GRANT ALL PRIVILEGES ON `"+user+"` . * TO '"+user+"'@'localhost';");
				fw.flush();
			}
		} catch (Exception e) {e.printStackTrace();}
	}
}
