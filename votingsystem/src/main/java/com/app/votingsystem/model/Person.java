package com.app.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
//	private Person currUser;

	@JsonProperty("username")
	private String name;

	@JsonProperty("age")
	private byte age;

	public Person() {
		
	}

	public Person(String name, byte age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getAge() {
		return age;
	}

	public void setAge(byte age) {
		this.age = age;
	}

}
