package com.mazhe.controller;


import com.alibaba.fastjson.JSON;
import com.mazhe.domain.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/order")
@Slf4j
@Api(description = "swaggerTestController相关api")
public class OrderController {


    @ApiOperation(value="Get测试", notes="测试")
    @GetMapping(value = "/status")
    public Object checkStatus(HttpServletRequest request ) {
        log.info("addOrder - start");
    	return new ResponseEntity<>("OrderBase API is running!", HttpStatus.OK);
    }
    
    @PostMapping(value = "/test")
    public Product call(HttpServletRequest requestBack,@RequestBody  Product product ) {
    	log.info("addOrder - start{}", JSON.toJSONString(product));
    	return null;
    	//return new ResponseEntity<>("OrderBase API is running!", HttpStatus.OK);
    }
    
}
