package com.app.pengine.dto;
import java.util.List;
import com.app.pengine.model.CheckOut;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CheckOutResponseDto   {
	
	private List<CheckOut> checkOut;
	private int FinalPrice;

}
