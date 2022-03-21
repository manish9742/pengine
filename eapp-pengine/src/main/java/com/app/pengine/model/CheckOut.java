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
public class CheckOut {
	
	private boolean isPapplicable;
	private Float price;
	private int totalQuantity;
	private String SKUunit;
	
}
