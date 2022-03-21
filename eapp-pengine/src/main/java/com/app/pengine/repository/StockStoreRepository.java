package com.app.pengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.pengine.model.StockStoreModel;

 

@Repository
public interface StockStoreRepository extends JpaRepository <StockStoreModel, Integer>{
	
     

}  
