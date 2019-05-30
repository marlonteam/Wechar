package com.mazhe.service;

import com.mazhe.config.PayOrderSeq;
import com.mazhe.dao.AdressRepository;
import com.mazhe.dao.UserRepository;
import com.mazhe.domain.Adress;
import com.mazhe.domain.BaseMessage;
import com.mazhe.domain.User;
import com.mazhe.util.DateUtil;
import com.mazhe.util.JsonUtilities;
import com.mazhe.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * 小程序用户添加
     * @param openId
     * @return
     */
    public BaseMessage userAdd(String openId){
        String OrderSqe= PayOrderSeq.getOrderSeq(null);
        log.info("用户:{}进入小程序  ，，每次进入唯一订单号：{}",openId,OrderSqe);
        List<String> Ids=new ArrayList();
        User userModel=userRepository.findOneByOpenId(openId).orElse(new User());
        userModel.setShopId(OrderSqe);
        if(userModel.getID()==null){
            userModel.setCreateDate(DateUtil.getCurTimestamp());
            userModel.setOpenId(openId);
        }
        else{
            String json=userModel.getShopIds();
            if(!StringUtil.isEmpty(json)){
                Ids=JsonUtilities.readValue(json,List.class);
            }
        }
        Ids.add(OrderSqe);
        userModel.setShopIds(JsonUtilities.toJSon(Ids));
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
}
