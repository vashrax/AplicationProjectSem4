package application;

import java.io.Serializable;

/**
 * Klasa reprezentująca pojedyńczy "tip".
 *
 *  @author Grzegorz Ciosek (VashRaX)
 * 	@version 1.0.0
 *
 */

public class SingleTip{

	private String title;
	private String content;


	/**
	 * Konstruktor tworzący obiekt typu SingleTip.
	 * @param title - Tytuł
	 * @param content - Tekst wyświetlany dla użytkownika w okienku TipOfTheDay.
	 */
	public SingleTip(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}

	/**
	 * Getter zwracający tytuł.
	 * @return tytuł
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter ustalający tytuł.
	 * @param title - wartość przypisywana przez setter.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter zwracający ciało SingleTip'a
	 * @return Tekst wyświetlany użytkownikowi.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Setter ustalający ciało SingleTip'a.
	 * @param content - Wartość przypisywana dla ciała przez setter.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		SingleTip other = (SingleTip) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SingleTip [title=" + title + ", content=" + content + "]";
	}

	
}
