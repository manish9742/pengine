package com.app.pengine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import java.util.Map.Entry;

import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.model.Cart;
import com.app.pengine.model.CheckOut;

@Service
public class PromotionEngineCombinedRule {
	
	
	public List<CheckOut> executeCombinedRule(List<Cart> cart, Map<String, ActivePromotionModel> promotionMap)  {

		List<CheckOut> chekcOutList = new ArrayList<>();

		CheckOut checkOut = null;
		for (Entry<String, ActivePromotionModel> mapObj : promotionMap.entrySet()) {
		 

			Optional<List<Cart>> chek = getCombineRule(cart).entrySet().stream()
					.filter(e -> e.getKey().equalsIgnoreCase(String.valueOf(mapObj.getKey()))).map(Map.Entry::getValue)
					.findFirst();

			if (chek.isPresent()) {
				if (chek.get().get(0).getQunatity() == chek.get().get(1).getQunatity()) {

					checkOut = new CheckOut(true,
							mapObj.getValue().getPromotionPrice() * chek.get().get(1).getQunatity(),
							mapObj.getValue().getPromotionQuantity(), mapObj.getValue().getPromotionSKU());
				} else {
					Map<String, Integer> item1Map = getMapForCombinedRules(mapObj, chek.get().get(0).getQunatity());
					Map<String, Integer> item2Map = getMapForCombinedRules(mapObj, chek.get().get(1).getQunatity());

					if (item1Map.size() > item2Map.size()) {

						checkOut = new CheckOut(true,
								getCombinedPrice(item1Map, item2Map, mapObj, chek.get().get(0)).floatValue(),
								chek.get().get(0).getQunatity() + chek.get().get(1).getQunatity(),
								mapObj.getValue().getPromotionSKU());
					} else {

						checkOut = new CheckOut(true,
								getCombinedPrice(item2Map, item1Map, mapObj, chek.get().get(1)).floatValue(),
								chek.get().get(0).getQunatity() + chek.get().get(1).getQunatity(),
								mapObj.getValue().getPromotionSKU());
					}

				}
				chekcOutList.add(checkOut);
			}

		}
		return chekcOutList;
	}
	
	Map<String, Integer> getMapForCombinedRules(Entry<String, ActivePromotionModel> mapObj, int quantity) {

		List<Integer> itemUnitList = new ArrayList<>();
		int tempQuantity = quantity;
		itemUnitList.add(mapObj.getValue().getPromotionQuantity());
		while (mapObj.getValue().getPromotionQuantity() < tempQuantity) {
			tempQuantity = tempQuantity - mapObj.getValue().getPromotionQuantity();
			if (mapObj.getValue().getPromotionQuantity() <= tempQuantity) {
				itemUnitList.add(mapObj.getValue().getPromotionQuantity());

			}
		}
		Map<String, Integer> returnMap = new HashMap<String, Integer>();
		int flag = 1;
		for (Integer i : itemUnitList) {
			returnMap.put(flag + "#" + i, i);
			flag++;
		}
		return returnMap;

	}
	
	public Integer getCombinedPrice(Map<String, Integer> item1Map, Map<String, Integer> item2Map,
			Entry<String, ActivePromotionModel> promotionMap, Cart cart) {
		List<Float> priceList = new ArrayList<>();
		for (Entry<String, Integer> mapObjs : item1Map.entrySet()) {

			Integer x = item2Map.get(mapObjs.getKey());

			if (x != null) {

				priceList.add(x * promotionMap.getValue().getPromotionPrice());
			} else {

				priceList.add((float) (mapObjs.getValue().intValue() * cart.getPrice()));
			}
		}

		return priceList.stream().mapToInt(e -> e.intValue()).sum();
	}
	
	public Map<String, List<Cart>> getCombineRule(List<Cart> cart) {

		Map<String, List<Cart>> combileRuleCheck = new HashMap<String, List<Cart>>();
		for (int i = 0; i <= cart.size() - 1; i++) {

			List<Cart> innerList = null;
			 
			for (int j = 0; j <= cart.size() - 1; j++) {
				innerList = new ArrayList<>();
				innerList.add(cart.get(i));
				innerList.add(cart.get(j));
				if(cart.get(i)!=cart.get(j)) {
				combileRuleCheck.put( cart.get(i).getSkuUnit().concat("&").concat(cart.get(j).getSkuUnit()), innerList);
				}

			}
			
		}
		return combileRuleCheck;
	}
}
