package com.mazhe.dao;


import com.mazhe.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor {

//	@Query(value = "SELECT count(1) sum FROM   tpoltran WHERE CREATEDATE >=:start AND CREATEDATE <=:end  ORDER BY CREATEDATE DESC", nativeQuery = true)
//	Object[] findTNumByCreateDate(@Param("start") String start, @Param("end") String end);
//
//	@Query(value = "SELECT SUM(PRICE) sum FROM   tpoltran WHERE CREATEDATE >=:start AND CREATEDATE <=:end  ORDER BY CREATEDATE DESC", nativeQuery = true)
//	Object[] findTPriceByCreateDate(@Param("start") String start, @Param("end") String end);
//
//	@Query(value = "select * from tpoltran  WHERE CREATEDATE>=:start And CREATEDATE <=:end union  all select * from tpoltranhis WHERE CREATEDATE>=:start And CREATEDATE <=:end", nativeQuery = true)
//	List<PolTranHis> findAllAndHis(@Param("start") String start, @Param("end") String end);
//
//
//	@Query(value = "select hour(CREATEDATE) as hours,count(1) as counts from tpoltran where DATE_FORMAT(CREATEDATE,'%Y-%m-%d') = :start  group by hours", nativeQuery = true)
//	Object[][] findTodayByGroupHour(@Param("start") String start);
//
//	@Query(value = "select distinct BIZOWNER from tpoltran union select distinct BIZOWNER from tpoltranhis", nativeQuery = true)
//	Object[] findAllBZOwner();

   Optional<User> findOneByOpenId(String openId);


}
