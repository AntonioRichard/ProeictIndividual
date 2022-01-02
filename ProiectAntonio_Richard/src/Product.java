
public class Product {
	String name;
	double price;
	int qty,id;
	byte[] pic;
	
	Product(int id,String name, double price, int qty,byte[] pic){
		this.name=name;
		this.price=price;
		this.qty=qty;
		this.pic=pic;
		this.id=id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return name+" "+"pret:"+price+"ron stoc:"+qty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	
	
}
