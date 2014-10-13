package example.jpa;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "todolist")
/*
 * define O-R mapping of todolist table
 */
public class ITEM {
	@Id //primary key
	@Column(name = "L_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	@Basic
	@Column(name = "C_ITEMNUMBER")
	String itemNumber;
	
	@Basic
	@Column(name = "C_NAME")
	String name;
	
	@Basic
	@Column(name = "C_IMGSRC")
	String imgsrc;
	
	@Basic
	@Column(name = "C_DESC")
	String description;
	
	@Basic
	@Column(name = "C_PRICE")
	String price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getImgSrc() {
		return imgsrc;
	}
	
	public void setImgSrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long pk) {
		id = pk;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
		
	}
	
	public String getItemNumber(){
		return itemNumber;
	}
	
	@Override
	public String toString() {
		return String.format("{\"id\": \"%d\", \"itemNumber\": \"%s\", \"name\": \"%s\",  \"price\": \"%s\", \"description\": \"%s\", \"imgsrc\": \"%s\"}", id, itemNumber, name, price,  description, imgsrc);
	}
}
