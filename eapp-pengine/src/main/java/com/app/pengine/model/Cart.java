package com.app.pengine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	
	private int id;
	private int qunatity;
	private String skuUnit;
	private Float price;
	
	
	
	 
	 
	

}
