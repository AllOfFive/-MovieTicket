package edu.nju.ar.model;

public class User {
	 public int id;
	 public String name;
	 public String password;
	 public int role;
	 
	
	 public int getId(){
		 return id;
	 }
	 public void setId(int id){
		 this.id=id;
	 }
	 
	 public String getName(){
		 return name;
	 }
	 public void setName(String name){
		 this.name=name;
	 }
	 public String getPassword(){
		 return password;
	 }
	 public void setPassword(String password){
		 this.password=password;
	 }
	 public int getRole(){
		 return role;
	 }
	 public void setRole(int role){
		 this.role=role;
	 }
	
	
}
