package com.mazhe.dao;


import com.mazhe.domain.Adress;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdressRepository extends CrudRepository<Adress, Long>, JpaSpecificationExecutor {


	 List<Adress> findAllByOpenIdOrderByDefaultFlagAsc (String openID);
	
}
