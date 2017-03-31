package by.epam.naumovich.film_ordering.bean;

import java.sql.Date;
import java.sql.Time;

/**
 * This bean class describes an order entity
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class Order {

	private int ordNum;
	private int userId;
	private int filmId;
	private Date date;
	private Time time;
	private float price;
	private int discount;
	private float payment;
	
	public int getOrdNum() {
		return ordNum;
	}
	public void setOrdNum(int ordNum) {
		this.ordNum = ordNum;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getFilmId() {
		return filmId;
	}
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public float getPayment() {
		return payment;
	}
	public void setPayment(float payment) {
		this.payment = payment;
	}
	
	@Override
	public int hashCode() {
		int hash = 11;
	    hash = 7 * hash + ordNum;
	    hash = 13 * hash + userId;
	    hash = 31 * hash + filmId;
	    hash = 67 * hash + ((null != date) ? date.hashCode() : 0);
	    hash = 47 * hash + ((null != time) ? time.hashCode() : 0);
	    hash = 3 * hash + Float.floatToIntBits(price);
	    hash = 11 * hash + discount;
	    hash = 19 * hash + Float.floatToIntBits(payment);
	    return hash;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true; 
		}
		if (null == obj) { 
			return false; 
		}
		if (obj.getClass() != getClass()) { 
			return false; 
		}
		
		Order order = (Order)obj;
		if (ordNum != order.ordNum) { 
			return false; 
		}
		if (userId != order.userId) { 
			return false; 
		}
		if (filmId != order.filmId) { 
			return false; 
		}
		if (Float.floatToIntBits(price) != Float.floatToIntBits(order.price)) { 
			return false; 
		}
		if (discount != order.discount) { 
			return false; 
		}
		if (Float.floatToIntBits(payment) != Float.floatToIntBits(order.payment)) { 
			return false; 
		}
		if ((null == date) ? (order.date != null) : !date.equals(order.date)) {
			return false;
		}
		if ((null == time) ? (order.time != null) : !time.equals(order.time)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

	    result.append(this.getClass().getSimpleName() + " Object {");
	    result.append(" OrdNum: " + ordNum);
	    result.append(", UserID: " + userId);
	    result.append(", FilmID: " + filmId);
	    result.append(", Date: " + date);
	    result.append(", Time: " + time);
	    result.append(", Price: " + price);
	    result.append(", Discount: " + discount);
	    result.append(", Payment: " + payment);
	    result.append("}");

		return result.toString();
	}
}
