package com.ride.goeasy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Userr {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private long mobno;
	private String password;
	private String role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getMobno() {
		return mobno;
	}

	public void setMobno(long mobno) {
		this.mobno = mobno;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Userr(long mobno, String password, String role) {
		super();
		this.mobno = mobno;
		this.password = password;
		this.role = role;
	}

	public Userr() {
		super();
	}

	@Override
	public String toString() {
		return "Userr [id=" + id + ", mobno=" + mobno + ", password=" + password + ", role=" + role + "]";
	}

}
