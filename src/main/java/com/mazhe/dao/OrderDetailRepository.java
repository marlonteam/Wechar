package com.mazhe.dao;



import com.mazhe.domain.OrderDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long>, JpaSpecificationExecutor {

   List<OrderDetail> findAllByOrderSeqAndStatus(String orderSqe,String status);

	//List<OrderDetail> findAllByOpenIdOrderByCreateDateDesc(String openId);

    List<OrderDetail> findAllByOpenIdAndStatusOrderByCreateDateDesc(String openId,String status, Pageable pageable);

    List<OrderDetail> findAllByOpenIdAndStatusAndCreateDateBetweenOrderByCreateDateDesc(String openId,String status,String start,String end);

}
