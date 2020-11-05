package com.deyuan.dao;

import com.deyuan.pojo.Traveller;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravellerDao {

    @Select("select * from traveller where id in(select travellerid from order_traveller where orderId=#{orderId})")
    List<Traveller> findByOrderId(String orderId);
}
