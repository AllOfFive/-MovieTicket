package edu.nju.ar.model;

public class Detail {
	private int id;
	private int movieId;
	private int cinemaId;
	private String hall;
	private String date;
	private String startAt;
	private String endAt;
	private double yPrice;
	private double tPrice;
	private double bPrice;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public int getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(int cinemaId) {
		this.cinemaId = cinemaId;
	}
	public String getHall() {
		return hall;
	}
	public void setHall(String hall) {
		this.hall = hall;
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
	public double getyPrice() {
		return yPrice;
	}
	public void setyPrice(double yPrice) {
		this.yPrice = yPrice;
	}
	public double gettPrice() {
		return tPrice;
	}
	public void settPrice(double tPrice) {
		this.tPrice = tPrice;
	}
	public double getbPrice() {
		return bPrice;
	}
	public void setbPrice(double bPrice) {
		this.bPrice = bPrice;
	}
	
	
}
