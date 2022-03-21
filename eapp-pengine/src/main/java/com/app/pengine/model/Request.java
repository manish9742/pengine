package com.app.pengine.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

 
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Request<T> {

	private T valueObj;
	private List<T> valueObjList;

	 
}

