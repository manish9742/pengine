package com.app.pengine.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.app.pengine.model.ActivePromotionModel;
import com.app.pengine.repository.ActivePromotionRepository;
import com.app.pengine.service.ActivePromotionService;

public class ActivePromotionServiceTest {

	private ActivePromotionService activePromotionService;

	@Mock
	private ActivePromotionRepository activePromotionRepository;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		activePromotionService = new ActivePromotionService(activePromotionRepository);
	}

	@Test
	public void getPromotion_should_return_all_Promotion_detils() throws Exception {

		List<ActivePromotionModel> activePromotionList = new ArrayList<ActivePromotionModel>();
		activePromotionList.add(new ActivePromotionModel(1, "A", 3, 130f, "3 of A for 130"));
		activePromotionList.add(new ActivePromotionModel(2, "B", 2, 45f, "2 of B for 45"));
		activePromotionList.add(new ActivePromotionModel(3, "C&D", 1, 30f, "C &  D for 30"));

		// arrange 
		given(activePromotionRepository.findAll()).willReturn(activePromotionList);

		// act and assert
		List<ActivePromotionModel> allitems = activePromotionService.getAllActivePromotions();

		assertThat(allitems.size()).isEqualTo(3);
		
		verify(activePromotionRepository).findAll();

	}

	@Test
	public void addPromotion_should_return_added_addPromotion() throws Exception {

		ActivePromotionModel model = new ActivePromotionModel(1, "A", 3, 130f, "3 of A for 130");
		// arrange
		given(activePromotionRepository.save(model))
				.willReturn(new ActivePromotionModel(1, "A", 3, 130f, "3 of A for 130"));
		 
		// act and assert
		ActivePromotionModel modelObject = activePromotionService.addActivePromotion(model);

		assertThat(modelObject.getPromotionId()).isEqualTo(model.getPromotionId());
		assertThat(modelObject.getPromotionSKU()).isEqualTo(model.getPromotionSKU());
		assertThat(modelObject.getDescription()).isEqualTo(model.getDescription());
		assertThat(modelObject.getPromotionPrice()).isEqualTo(model.getPromotionPrice());
		assertThat(modelObject.getPromotionQuantity()).isEqualTo(model.getPromotionQuantity());
		
		verify(activePromotionRepository).save(model);

	}

	
	 
}
