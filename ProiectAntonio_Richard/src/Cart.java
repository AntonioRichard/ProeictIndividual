import java.util.ArrayList;
import java.util.List;

public class Cart {
	String nume;
	double pret;
	int id;
	static List<Product> cosFinal = new ArrayList<>();
	Cart(String name, double price,int id){
		nume=name;
		pret=price;
		this.id=id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNume() {
		return nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public double getPret() {
		return pret;
	}
	public void setPret(double pret) {
		this.pret = pret;
	}
}
