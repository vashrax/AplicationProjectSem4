package application;

import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;

/**
 * Klasa odpowiedzialna za tworzenie oraz manewrowanie plikiem tekstowym do zapisu danych z tabeli.
 *
 *  @author Grzegorz Ciosek (VashRaX)
 * 	@version 1.1.0
 *
 */

public class FileOperations {
	/**
	 * Zmienna przechowująca właściwości pliku.
	 */
	static private File file = new File("Number.txt");

	/**
	 * Metoda odpowiedzialna za tworzenie pliku w razie jego braku.
	 * @param logger obiekt klasy Logger - pozwala na zapis danych do pliku log
	 */
	static void createFile(Logger logger) {
		if(!file.exists())

		{
			try {
				file.createNewFile();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Błąd podczas tworzenia pliku");
				logger.error(e1 +" Błąd podczas tworzenia pliku.");
			}
		}
	}

	/**
	 * 	 Klasa odpowiedzialna za zapis wartości ,przekazanej jako zmiennej "value", do pliku.
	 *
	 * @param value wartość "do zapisania".
	 * @param logger obiekt klasy Logger - pozwala na zapis danych do pliku log
	 */
	static void saveToFile(String value, Logger logger) {
		if (file.exists())
			file.delete();

		try {
			file.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(file);
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOut));
			bufferedWriter.write(value);
			bufferedWriter.close();
			logger.info("Zapisano wartość "+ value +" do pliku.");
			JOptionPane.showMessageDialog(null,"Zapisano wartość "+ value +" do pliku.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Nie udało się zapisać wartości do pliku.");
			logger.error(e + " Nie udało się zapisać wartości do pliku.");
		}
	}

	/**
	 * <p>Wczytywanie wartości z pliku, w razie pustego lub nie istniejącego pliku zwróci wartość przesłaną jako "oldValue".</p></>
	 * @param oldValue wartość do zwrócenia w razie problemów.
	 * @param logger obiekt klasy Logger - pozwala na zapis danych do pliku log
	 * @return wartość z pliku lub oldValue.
	 */

	static String loadFromFile(String oldValue, Logger logger) {
		String value = null;
		if (file.exists()) {
			try {
				FileInputStream fileIn = new FileInputStream(file);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileIn));
				value = bufferedReader.readLine();
				Double.parseDouble(value);
				bufferedReader.close();
				JOptionPane.showMessageDialog(null,"Wczytano wartość "+value +" z pliku.");
				logger.info("Wczytano wartość "+value +" z pliku.");
				return value;
			} catch(NumberFormatException e1){
				logger.error(e1 +" Nieprawidłowy format liczby zapisanej w pliku.");
				JOptionPane.showMessageDialog(null, "Plik zostanie nadpisany z powodu braku zachowania formatu wartości liczby.");
				saveToFile("0",logger);
				return oldValue;
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null,"Nie udało się wczytać wartości z pliku.");
				logger.error(e + " Nie udało się wczytać wartości z pliku.");
				return oldValue;
			}

		}
		else {
			JOptionPane.showMessageDialog(null,"Nie udało się wczytać wartości z pliku.");
			logger.error(" Nie udało się wczytać wartości z pliku - Plik nie istnieje.");
			return oldValue;
		}
	}

}
