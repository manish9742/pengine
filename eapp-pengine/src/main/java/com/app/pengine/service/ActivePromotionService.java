package com.app.pengine.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.repository.ActivePromotionRepository;

@Service
public class ActivePromotionService {
	
	 
	ActivePromotionRepository activePromotionRepository;

	public ActivePromotionService(ActivePromotionRepository activePromotionRepository)   {
		this.activePromotionRepository=activePromotionRepository;
	}

	public List<ActivePromotionModel> getAllActivePromotions()  {
		return activePromotionRepository.findAll();
	}
	 
	public ActivePromotionModel addActivePromotion(ActivePromotionModel activePromotions)   {
		
		Optional<ActivePromotionModel> obj=	activePromotionRepository.findAll().stream().filter(e->e.getPromotionSKU().equals(activePromotions.getPromotionSKU())).findFirst();
		
		if(!obj.isPresent()) {
			return activePromotionRepository.save(activePromotions);
		}
		return null;
	}

}
 