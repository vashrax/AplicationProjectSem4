package application;

import java.io.Serializable;
import java.util.LinkedHashSet;

public class Tips implements Serializable{

	private static final long serialVersionUID = 6139406885688913810L;
	private LinkedHashSet<SingleTip> tips = new LinkedHashSet<>();
	
	Tips(){
		
	}
	
	public void saveTips() {
		FileOperations.SaveTipsToFile(this);
	}
	
	public void loadTips() {
		setTips(FileOperations.LoadTipsFromFile().getTips());
	}

	public LinkedHashSet<SingleTip> getTips() {
		return tips;
	}

	public void setTips(LinkedHashSet<SingleTip> tips) {
		this.tips = tips;
	}
	
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
