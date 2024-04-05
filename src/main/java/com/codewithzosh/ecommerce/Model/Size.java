package com.codewithzosh.ecommerce.Model;

import lombok.Data;

@Data
public class Size {

	private String name;
	private int quantity;
	public Size(String name, int quantity) {
		super();
		this.name = name;
		this.quantity = quantity;
	}
	
}
