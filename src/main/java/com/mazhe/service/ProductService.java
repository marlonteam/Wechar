package com.mazhe.service;

import com.mazhe.dao.ProductRepository;
import com.mazhe.dao.ProductTypeRepository;
import com.mazhe.domain.BaseMessage;
import com.mazhe.domain.Product;
import com.mazhe.domain.ProductType;
import com.mazhe.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Administrator on 2019/5/16.
 */
@Slf4j
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;



    public BaseMessage productSave(Product product){
        if(null!=product.getID()){
            //更新
            Optional< Product> product1=  productRepository.findById(product.getID());
            if(product1.isPresent()){
                BeanUtils.copyProperties(product,product1.get());
                productRepository.save(product1.get());
                return  BaseMessage.Success(product1.get());
            }
            else{
                return  BaseMessage.Null(null);
            }
        }
       else{
            //添加
            product.setID(null);
            product.setCreateDate(DateUtil.getCurTimestamp());
            Product productmodel= productRepository.save(product);
            if(productmodel!=null&&productmodel.getID()!=null){
                return  BaseMessage.Success(productmodel);
            }
            return  BaseMessage.Null(null);
        }
    }

    public BaseMessage productFindAll(){

        List products= (List) productRepository.findAll();

        if(products!=null&&products.size()>0){
            return  BaseMessage.Success(products);
        }

        return  BaseMessage.Null(null);
    }

    public BaseMessage productFindById(Long id){
        Optional<Product> product= productRepository.findById(id);
        if(product.isPresent()){
            return  BaseMessage.Success(product.get());
        }
        return  BaseMessage.Null(null);
    }

    public BaseMessage productFindByTypeId(Long typeId){
        List<Product> list= productRepository.findByTypeId(typeId);
        if(list.size()>0){
            return  BaseMessage.Success(list);
        }
        return  BaseMessage.Null(null);
    }

    public BaseMessage productTypeSave(ProductType productType){
        if(null!=productType.getID()){
            //更新
            Optional< ProductType> productType1=  productTypeRepository.findById(productType.getID());
            if(productType1.isPresent()){
                BeanUtils.copyProperties(productType,productType1.get());
                productTypeRepository.save(productType1.get());
                return  BaseMessage.Success(productType1.get());
            }
            else{
                return  BaseMessage.Null(null);
            }

        }
        else{
            productType.setID(null);
            productType.setCreateDate(DateUtil.getCurTimestamp());
            ProductType productType1= productTypeRepository.save(productType);
            if(productType1!=null&&productType1.getID()!=null){
                return  BaseMessage.Success(productType1);
            }
            return  BaseMessage.Null(null);

        }
    }

    public BaseMessage productTypeFindAll(){

        List productTypes= (List) productTypeRepository.findAll();

        if(productTypes!=null&&productTypes.size()>0){
            return  BaseMessage.Success(productTypes);
        }

        return  BaseMessage.Null(null);
    }

    public BaseMessage productTypeFindById(Long id){
        Optional<ProductType> productType= productTypeRepository.findById(id);
        if(productType.isPresent()){
            return  BaseMessage.Success(productType.get());
        }
        return  BaseMessage.Null(null);
    }
}
