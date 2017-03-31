package by.epam.naumovich.film_ordering.bean;

import java.sql.Date;
import java.sql.Time;

/**
 * This bean class describes a discount entity
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class Discount {

	private int id;
	private int userID;
	private int amount;
	private Date stDate;
	private Time stTime;
	private Date enDate;
	private Time enTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getStDate() {
		return stDate;
	}
	public void setStDate(Date stDate) {
		this.stDate = stDate;
	}
	public Time getStTime() {
		return stTime;
	}
	public void setStTime(Time stTime) {
		this.stTime = stTime;
	}
	public Date getEnDate() {
		return enDate;
	}
	public void setEnDate(Date enDate) {
		this.enDate = enDate;
	}
	public Time getEnTime() {
		return enTime;
	}
	public void setEnTime(Time enTime) {
		this.enTime = enTime;
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

		Discount discount = (Discount) obj;
		if (id != discount.id) {
			return false;
		}
		if (userID != discount.userID) {
			return false;
		}
		if (amount != discount.amount) {
			return false;
		}
		if ((null == stDate) ? (discount.stDate != null) : !stDate.equals(discount.stDate)) {
			return false;
		}
		if ((null == stTime) ? (discount.stTime != null) : !stTime.equals(discount.stTime)) {
			return false;
		}
		if ((null == enDate) ? (discount.enDate != null) : !enDate.equals(discount.enDate)) {
			return false;
		}
		if ((null == enTime) ? (discount.enTime != null) : !enTime.equals(discount.enTime)) {
			return false;
		}
		return true;
	}
	@Override
	public int hashCode() {
		int hash = 3;
	    hash = 7 * hash + id;
	    hash = 1 * hash + userID;
	    hash = 13 * hash + amount;
	    hash = 17 * hash + ((null != stDate) ? stDate.hashCode() : 0);
	    hash = 19 * hash + ((null != stTime) ? stTime.hashCode() : 0);
	    hash = 31 * hash + ((null != enDate) ? enDate.hashCode() : 0);
	    hash = 47 * hash + ((null != enTime) ? enTime.hashCode() : 0);
	    return hash;
	}
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

	    result.append(this.getClass().getSimpleName() + " Object {");
	    result.append(" ID: " + id);
	    result.append(", UserID: " + userID);
	    result.append(", Amount: " + amount);
	    result.append(", stDate: " + stDate);
	    result.append(", stTime: " + stTime);
	    result.append(", stDate: " + stDate);
	    result.append(", stTime: " + stTime);
	    result.append(", enDate: " + enDate);
	    result.append(", enTime: " + enTime);
	    result.append("}");

		return result.toString();
	}
	
	
	
}

