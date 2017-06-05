package edu.nju.ar.model;

public class session {
    int id;
	cinema c;
	movie m;
	String hall;
	String date;
	String startAt;
	String endAt;
	double price;
	public cinema getC() {
		return c;
	}
	public void setC(cinema c) {
		this.c = c;
	}
	public movie getM() {
		return m;
	}
	public void setM(movie m) {
		this.m = m;
	}
	public String getHall() {
		return hall;
	}
	public void setHall(String hall) {
		this.hall = hall;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStartAt() {
		return startAt;
	}
	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}
	public String getEndAt() {
		return endAt;
	}
	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}
	
	
}
