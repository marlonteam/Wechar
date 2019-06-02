package com.mazhe.dao;


import com.mazhe.domain.OrderBase;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderBaseRepository extends CrudRepository<OrderBase, Long>, JpaSpecificationExecutor {

    OrderBase findOneByOpenIdOrderByCreateDateAsc(String openId);
	
}
