package com.app.pengine.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.model.Cart;
import com.app.pengine.model.CheckOut;
import com.app.pengine.model.StockStoreModel;

@Service
public class PromotionEngineFixedRuleService {

	 
	ActivePromotionService activePromotionService;
	
	StockStoreService stockStoreImpl;

	public PromotionEngineFixedRuleService(ActivePromotionService activePromotionService, StockStoreService stockStoreImpl) {
		this.activePromotionService = activePromotionService;
		this.stockStoreImpl = stockStoreImpl;
	}


	public List<CheckOut> executeFixedPricePromotion(List<Cart> cartList,
			Map<String, ActivePromotionModel> promotionMap, List<StockStoreModel> stockList)   {

		List<CheckOut> returnList = new ArrayList<>();
		for (Cart cart : cartList) {
			CheckOut checkOut = null;
			ActivePromotionModel prmos = promotionMap.get(cart.getSkuUnit());
			if (prmos != null && cart.getQunatity() >= prmos.getPromotionQuantity()) {
				checkOut = priceForFixedPromotion(cart.getQunatity(), prmos, stockList);

			} else {
				checkOut = new CheckOut(false, cart.getPrice(), cart.getQunatity(), cart.getSkuUnit());
			}
			returnList.add(checkOut);
		}

		return returnList;
	}

	CheckOut priceForFixedPromotion(int quantity, ActivePromotionModel prmos, List<StockStoreModel> stockList)   {

		List<String> itemUnitList = new ArrayList<>();
		CheckOut checkOut = null;
		if (quantity % prmos.getPromotionQuantity() == 0) {

			checkOut = new CheckOut(true, prmos.getPromotionPrice() * (quantity / prmos.getPromotionQuantity()),
					quantity, prmos.getPromotionSKU());

		} else {

			itemUnitList.add(String.valueOf(prmos.getPromotionQuantity()).concat("-").concat(prmos.getPromotionSKU()));
			int tempQuantity = quantity;
			while (prmos.getPromotionQuantity() < tempQuantity) {
				tempQuantity = tempQuantity - prmos.getPromotionQuantity();

				if (prmos.getPromotionQuantity() < tempQuantity) {
					itemUnitList.add(
							String.valueOf(prmos.getPromotionQuantity()).concat("-").concat(prmos.getPromotionSKU()));
				} else {

					itemUnitList.add(String.valueOf(tempQuantity).concat("-").concat(prmos.getPromotionSKU()));
				}
			}

			checkOut = new CheckOut(true, getFixedPrmotionPrice(itemUnitList, stockList).floatValue(), quantity,
					prmos.getPromotionSKU());

		}

		return checkOut;
	}

	public Integer getFixedPrmotionPrice(List<String> list, List<StockStoreModel> stockList)   {

		Map<String, Float> originialPrice = new HashMap<String, Float>();
		List<Float> priceList = new ArrayList<>();

		Map<String, Float> promotionMap = new HashMap<String, Float>();
		for (ActivePromotionModel prmos : getAllActivePromotions()) {
			promotionMap.put(String.valueOf(prmos.getPromotionQuantity()).concat("-").concat(prmos.getPromotionSKU()),
					prmos.getPromotionPrice());
		}

		for (StockStoreModel prmos : stockList) {
			originialPrice.put(prmos.getSkuName(), prmos.getSkuPrice());
		}

		if (originialPrice != null && !originialPrice.isEmpty()) {
			for (String str : list) {
				Float temp_str = promotionMap.get(str);

				if (temp_str != null) {
					priceList.add(temp_str);
				} else {
					priceList.add(originialPrice.get(str.split("-")[1]) * Integer.parseInt(str.split("-")[0]));
				}

			}
		}

		return priceList.stream().mapToInt(e -> e.intValue()).sum();

	}


	public Map<String, ActivePromotionModel> getAllActivePromotionsMap() throws Exception {
		Map<String, ActivePromotionModel> promotionMap = new HashMap<String, ActivePromotionModel>();
		for (ActivePromotionModel prmos : activePromotionService.getAllActivePromotions()) {
			promotionMap.put(prmos.getPromotionSKU(), prmos);
		}
		return promotionMap;
	}

	public List<ActivePromotionModel> getAllActivePromotions()   {
		return activePromotionService.getAllActivePromotions();
	}

	public List<StockStoreModel> getAllStockItems() {
		return stockStoreImpl.getAllStockItems();
	}

}
