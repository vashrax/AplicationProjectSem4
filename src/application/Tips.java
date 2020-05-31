package application;

import java.util.LinkedHashSet;

/**
 * Obiekt tej klasy zaweira listę tipów wyświetlanych w aplikacji okienkowej.
 *
 *  @author Grzegorz Ciosek (VashRaX)
 * 	@version 1.1.0
 *
 */

public class Tips{


	private LinkedHashSet<SingleTip> tips = new LinkedHashSet<>();
	
	Tips(){
		tips.add(new SingleTip("1","PIERWSZE I NAJWAŻNIEJSZE :\n" +
				"Format wczytywanej z klawiatury liczby:\n" +
				"      **.**      \n" +
				"Liczba zmiennoprzecinkowa z kropką.\n" +
				"Przecinek spowoduje wywołanie błędu."));

		tips.add(new SingleTip("2","Skróty klawiszowe :\n" +
				"F1 - Pomoc\n" +
				"F2 - Zapis do pliku\n" +
				"F3 - Wczytywanie z pliku\n" +
				"F4 - Zamknięcie apliacji\n" +
				"F5 - Zerowanie tabeli\n" +
				"F6 - Wartości losowe\n" +
				"Home - Informacje o autorze"));
		tips.add(new SingleTip("3","Do pliku zapisywana jest pojedyńcza wartość, nie cała tabela!!!"));
		tips.add(new SingleTip("4", "Okienko pomocy wyświetla dokument wygenerowany automatycznie przez JavaDoc."));
	}

	/**
	 * Getter. Zwraca listę tipów [Obiekt]
	 * @return tips
	 */
	public LinkedHashSet<SingleTip> getTips() {
		return tips;
	}

	/**
	 * Setter. Ustawia wartość dla obiektu tips
	 * @param tips - wartość, która zostanie przypisana danemu obiektowi.
	 */
	public void setTips(LinkedHashSet<SingleTip> tips) {
		this.tips = tips;
	}

	/**
	 * Dodawanie pojedyńczego tipa do listy.
	 * @param title - Tytuł
	 * @param content - Tekst wyświetlany dla użytkownika w okienku TipOfTheDay.
	 */
	public void addTip(String title, String content) {
		tips.add(new SingleTip(title, content));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tips == null) ? 0 : tips.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tips other = (Tips) obj;
		if (tips == null) {
			if (other.tips != null)
				return false;
		} else if (!tips.equals(other.tips))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tips [tips=" + tips + "]";
	}
	

}
