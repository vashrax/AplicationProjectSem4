package application;

import javax.swing.*;
import java.io.*;

public class FileOperations {	

	private static String filename = "Tips.tips";
		
	static boolean createFile(){
		File file = new File(filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}else
			return true;
	}
	
	static void SaveTipsToFile(Tips tips) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(tips);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static Tips LoadTipsFromFile() {
		Tips tips = new Tips();
		if(createFile()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
				tips = (Tips)ois.readObject();
				ois.close();
			} catch(EOFException e){
				tips.addTip("0", "Nie posiadam żadnych porad dla Ciebie kolego.");
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return tips;
	}
	
}
