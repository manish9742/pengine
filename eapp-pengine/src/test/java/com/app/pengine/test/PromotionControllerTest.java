package com.app.pengine.test;

 
 
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.app.pengine.controller.PromotionEngineController;
import com.app.pengine.dto.CheckOutResponseDto;
import com.app.pengine.model.Cart;
import com.app.pengine.model.CheckOut;
import com.app.pengine.model.Request;
import com.app.pengine.service.ActivePromotionService;
import com.app.pengine.service.CheckOutService;
import com.app.pengine.service.StockStoreService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest(PromotionEngineController.class)
public class PromotionControllerTest {
	
	
	 
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private CheckOutService checkOutService;
	
	@MockBean
	StockStoreService stockStoreModel;
	
	@MockBean
	ActivePromotionService activePromotion;
	
	List<Cart> cartList=new ArrayList<Cart>();
	List<CheckOut> checkOutList=new ArrayList<CheckOut>();
	
	@BeforeAll
	public void setup() {
	    // register a bean name with mock expression manager
		 
		cartList=Arrays.asList(new Cart(1,1,"A",50f),new Cart(2,1,"B",30f),new Cart(1,1,"c",20f));
		checkOutList=Arrays.asList(new CheckOut(true,50f, 1, "A"),new CheckOut(true,30f, 1, "B"),new CheckOut(true,20f, 1, "C"));
	    
	}

	
	@Test
	public void fixed_price_promotion_scenarioA() throws Exception {
		
		List<Cart> cartList=new ArrayList<Cart>();
		cartList=Arrays.asList(new Cart(1,1,"A",50f),new Cart(2,1,"B",30f),new Cart(1,1,"c",20f));
		
		List<CheckOut> checkOutList=new ArrayList<CheckOut>();
		checkOutList=Arrays.asList(new CheckOut(true,50f, 1, "A"),new CheckOut(true,30f, 1, "B"),new CheckOut(true,20f, 1, "C"));
		 
		 
		given(checkOutService.proceedCheckOut(cartList)).willReturn(new CheckOutResponseDto(checkOutList,100));
		
		Request<Cart> reqObj=new Request<Cart>();
		reqObj.setValueObjList(cartList);
		
		// act & assert
		 mockMvc.perform(post("/pengine/checkout")
	               .contentType(MediaType.APPLICATION_JSON)
	               .content(asJsonString(reqObj))
	               .accept(MediaType.APPLICATION_JSON))
	               .andExpect(status().isOk());
		 
	}
	
	@Test
	public void fixed_price_promotion_scenarioB() throws Exception {
		
		given(checkOutService.proceedCheckOut(cartList)).willReturn(new CheckOutResponseDto(checkOutList,370));
		
		Request<Cart> reqObj=new Request<Cart>();
		reqObj.setValueObjList(cartList);
		
		// act & assert
		 mockMvc.perform(post("/pengine/checkout")
	               .contentType(MediaType.APPLICATION_JSON)
	               .content(asJsonString(reqObj))
	               .accept(MediaType.APPLICATION_JSON))
	               .andExpect(status().isOk());
		 
	}
	
	@Test
	public void fixed_price_promotion_scenarioC() throws Exception {
		 
		given(checkOutService.proceedCheckOut(cartList)).willReturn(new CheckOutResponseDto(checkOutList,280));
		
		Request<Cart> reqObj=new Request<Cart>();
		reqObj.setValueObjList(cartList);
		
		// act & assert
		 mockMvc.perform(post("/pengine/checkout")
	               .contentType(MediaType.APPLICATION_JSON)
	               .content(asJsonString(reqObj))
	               .accept(MediaType.APPLICATION_JSON))
	               .andExpect(status().isOk());
		 
	}
	
  
	
	public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
