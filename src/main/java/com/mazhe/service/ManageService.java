package com.mazhe.service;

import com.mazhe.config.PayOrderSeq;
import com.mazhe.dao.AdressRepository;
import com.mazhe.dao.OrderBaseRepository;
import com.mazhe.dao.OrderDetailRepository;
import com.mazhe.dao.UserRepository;
import com.mazhe.domain.*;
import com.mazhe.util.DateUtil;
import com.mazhe.util.JsonUtilities;
import com.mazhe.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Administrator on 2019/5/16.
 */
@Slf4j
@Service
public class ManageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdressRepository adressRepository;

    @Autowired
    private OrderBaseRepository orderBaseRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    /**
     * 小程序用户添加
     * @param openId
     * @return
     */
    public BaseMessage<User> userAdd(String openId){
        String OrderSqe= PayOrderSeq.getOrderSeq(null);
        log.info("用户:{}进入小程序  ，，每次进入唯一订单号：{}",openId,OrderSqe);
        List<String> Ids=new ArrayList();
        User userModel=userRepository.findOneByOpenId(openId).orElse(new User());
        userModel.setOrderSeq(OrderSqe);
        if(userModel.getID()==null){
            userModel.setCreateDate(DateUtil.getCurTimestamp());
            userModel.setOpenId(openId);
        }
        else{
            String json=userModel.getOrderSeqs();
            if(!StringUtil.isEmpty(json)){
                Ids=JsonUtilities.readValue(json,List.class);
            }
        }
        Ids.add(OrderSqe);
        userModel.setOrderSeqs(JsonUtilities.toJSon(Ids));
        User user = userRepository.save(userModel);
        //添加订单号
        if(user!=null){
            log.info("用户登录小程序保存入库 openId{}",openId);
        }
       return  BaseMessage.Success(user);

    }

    //用户地址添加
    public BaseMessage adressAdd(Adress adress){

        if(null!=adress.getID()){
            //更新
            Optional<Adress> adress1= adressRepository .findById(adress.getID());
            if(adress1.isPresent()){
                BeanUtils.copyProperties(adress,adress1.get());
                adressRepository.save(adress1.get());
                return  BaseMessage.Success(adress1.get());
            }
            else{
                return  BaseMessage.Null(null);
            }
        }
        else{
            //添加
            adress.setID(null);
            adress.setCreateDate(DateUtil.getCurTimestamp());
            Adress adressModel= adressRepository.save(adress);
            if(adressModel!=null&&adressModel.getID()!=null){
                return  BaseMessage.Success(adressModel);
            }
            return  BaseMessage.Null(null);
        }

    }

    //查询用户的地址，按默认地址排序
    public BaseMessage adressByOpenId(String openId) {
      List<Adress> adressList= adressRepository.findAllByOpenIdOrderByDefaultFlagAsc(openId);
      if(null!=adressList&&adressList.size()>0){
          return  BaseMessage.Success(adressList);
      }
      else{
          return  BaseMessage.Null(null);
      }
    }

    //根据Id查询用户的地址
    public BaseMessage adressById(String Id) {
        Optional<Adress> adress= adressRepository.findById(Long.valueOf(Id));
        if(adress.isPresent()){
            return  BaseMessage.Success(adress.get());
        }
        else{
            return  BaseMessage.Null(null);
        }
    }


    //添加购物车
    public  BaseMessage addShop(List<OrderDetail> orderDetails){
        //1 加入购物车校验
        Optional<User> user=null;
        for(OrderDetail orderDetail:orderDetails){
          if(StringUtils.isEmpty(orderDetail.getOpenId())){
              return BaseMessage.Fail("加入购物车的用户openId不能为空");
          }
          if(!user.isPresent()){
                user= userRepository.findOneByOpenId(orderDetail.getOpenId());
              if(!user.isPresent()){
                  return BaseMessage.Fail("加入购物车的用户openId查询为空，请重新登陆");
              }

          }
          orderDetail.setOrderSeq(user.get().getOrderSeq());
          if(null==orderDetail.getProduceId()||orderDetail.getProduceId()<=0){
                return BaseMessage.Fail("请检查商品ID  produceI");
            }
            if(null==orderDetail.getPrice()||orderDetail.getPrice().doubleValue()<=0){
                return BaseMessage.Fail("请检查商品价格  price");
            }
            if(StringUtils.isEmpty(orderDetail.getStatus())||!"1".equals(orderDetail.getStatus())){
                return BaseMessage.Fail("加入购物车状态 status 必须为1");
            }

            orderDetail.setCreateDate(DateUtil.getCurTimestamp());
        }
        //2 购物车入库保存
        orderDetails= (List<OrderDetail>)orderDetailRepository.saveAll(orderDetails);
        return BaseMessage.Success(orderDetails);
    }

    //用户openId查询最新的购物车集合
    public  BaseMessage queryShopByOrderSqe(String openId){
       Optional<User> user= userRepository.findOneByOpenId(openId);
        List<OrderDetail>  orderDetails= orderDetailRepository.findAllByOrderSeqAndStatus(user.get().getOrderSeq(),"1");
        if(null==orderDetails||orderDetails.size()<=0){
          return  BaseMessage.Null(null);
        }
        return BaseMessage.Success(orderDetails);
    }


    //订单确认下单
    public  BaseMessage orderCreate(OrderCreate orderCreate){
        //1 加入购物车校验
        Optional<User> user=null;
        if(StringUtils.isEmpty(orderCreate.getOrderBase().getOpenId())){
            return BaseMessage.Fail("加入购物车的用户openId不能为空");
        }
        if(!user.isPresent()){
            user= userRepository.findOneByOpenId(orderCreate.getOrderBase().getOpenId());
            if(!user.isPresent()){
                return BaseMessage.Fail("加入购物车的用户openId查询为空，请重新登陆");
            }

        }
        orderCreate.getOrderBase().setOrderSeq(user.get().getOrderSeq());
        if(StringUtils.isEmpty(orderCreate.getOrderBase().getStatus())||!"2".equals(orderCreate.getOrderBase().getStatus())){
            return BaseMessage.Fail("确认订单下单状态 status 必须为2");
        }
        if(null==orderCreate.getOrderBase().getAddressId()||orderCreate.getOrderBase().getAddressId()<=0){
            return BaseMessage.Fail("加入购物车的用户收获地址AdressId不能为空");
        }

        for(OrderDetail orderDetail:orderCreate.getOrderDetail()) {
            if(StringUtils.isEmpty(orderDetail.getOpenId())){
                return BaseMessage.Fail("加入购物车的用户openId不能为空");
            }
            orderDetail.setOrderSeq(user.get().getOrderSeq());
            if (null == orderDetail.getProduceId() || orderDetail.getProduceId() <= 0) {
                return BaseMessage.Fail("请检查商品ID  produceI");
            }
            if (null == orderDetail.getPrice() || orderDetail.getPrice().doubleValue() <= 0) {
                return BaseMessage.Fail("请检查商品价格  price");
            }
            if (StringUtils.isEmpty(orderDetail.getStatus()) || !"2".equals(orderDetail.getStatus())) {
                return BaseMessage.Fail("确认订单下单状态 status 必须为2");
            }
            orderDetail.setCreateDate(DateUtil.getCurTimestamp());
        }


        //2 购物车入库保存
        orderCreate.setOrderDetail((List<OrderDetail>) orderDetailRepository.saveAll(orderCreate.getOrderDetail()));
        orderCreate.setOrderBase(orderBaseRepository.save(orderCreate.getOrderBase()));

        return BaseMessage.Success(orderCreate);
    }

    //顾客历史订单查询
    public  BaseMessage queryOrderByOpenIdPage(String openId ,int pageNumber,int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<OrderDetail>  orderDetails=orderDetailRepository.findAllByOpenIdAndStatusOrderByCreateDateDesc(openId,"2",pageRequest);
        if(null!=orderDetails&&orderDetails.size()>0){
            return BaseMessage.Success(orderDetails);
        }
        return BaseMessage.Null(null);
    }

    //顾客昨日订单查询
    public  BaseMessage queryOrderByOpenIdYestdate(String openId){
        //1 构造昨日开始结束时间
        Date t1=DateUtil.dateAdvance_Day(new Date(),-1);
        String date=DateUtil.format(t1,DateUtil.FORMAT_DATE);
        String start=date+DateUtil.TIME_00;
        String end=date+DateUtil.TIME_24;
        orderDetailRepository.findAllByOpenIdAndStatusAndCreateDateBetweenOrderByCreateDateDesc(openId,"2",start,end);

        return null;
    }



}
