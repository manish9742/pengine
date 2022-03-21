package com.app.pengine.test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.model.Cart;
import com.app.pengine.model.CheckOut;
import com.app.pengine.model.StockStoreModel;
import com.app.pengine.service.PromotionEngineCombinedRule;

@RunWith(MockitoJUnitRunner.class)
public class PromotionEngineCombinedRuleTest {
	
PromotionEngineCombinedRule promotionEngineService = org.mockito.Mockito.mock(PromotionEngineCombinedRule.class);
List<Cart> cartList=new ArrayList<Cart>();
List<CheckOut> checkOutList=new ArrayList<CheckOut>();	  
	  
	@BeforeEach
	public void setUp() throws Exception {
		 MockitoAnnotations.initMocks(this);
		cartList=Arrays.asList(new Cart(1,1,"A",50f),new Cart(2,1,"B",30f),new Cart(1,1,"c",20f));
		checkOutList=Arrays.asList(new CheckOut(true,50f, 1, "A"),new CheckOut(true,30f, 1, "B"),new CheckOut(true,20f, 1, "C"));
		   
	}
	
	@Test
	public void  promotion_engine_combined_item_rule() throws Exception {
		
		List<StockStoreModel>  stockStoremodel= new ArrayList<StockStoreModel>();
		 stockStoremodel.add(new StockStoreModel(1,"A",50f));
		 stockStoremodel.add(new StockStoreModel(2,"B",30f));
		 stockStoremodel.add(new StockStoreModel(3,"C",20f));
		 stockStoremodel.add(new StockStoreModel(4,"D",15f));
		
		
		List<CheckOut> checkOutList=new ArrayList<CheckOut>();
		checkOutList.add(new CheckOut(true,250f, 1, "A"));
		checkOutList.add(new CheckOut(true,150f, 1, "B"));
		checkOutList.add(new CheckOut(true,20f, 1, "C"));
		
		List<ActivePromotionModel> activePromotionList = new ArrayList<ActivePromotionModel>();
		activePromotionList.add(new ActivePromotionModel(1, "A", 3, 130f, "3 of A for 130"));
		activePromotionList.add(new ActivePromotionModel(2, "B", 2, 45f, "2 of B for 45"));
		activePromotionList.add(new ActivePromotionModel(3, "C&D", 1, 30f, "C &  D for 30"));
		
		
		Map<String, ActivePromotionModel> promotionMap = new HashMap<String, ActivePromotionModel>();
		for (ActivePromotionModel prmos : activePromotionList) {
			promotionMap.put(prmos.getPromotionSKU(), prmos);
		}
		
		 
		 
		// act and assert
	    given(promotionEngineService.executeCombinedRule(cartList, promotionMap)).willReturn(checkOutList);
	     
	    List<CheckOut> checkList=  promotionEngineService.executeCombinedRule(cartList, promotionMap);
	    
	    assertThat(checkOutList.stream().mapToInt(e ->e.getPrice().intValue()).sum()).isEqualTo(checkList.stream().mapToInt(e ->e.getPrice().intValue()).sum());
		
	}

}
