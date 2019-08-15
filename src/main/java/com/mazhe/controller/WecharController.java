package com.mazhe.controller;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.mazhe.domain.*;
import com.mazhe.service.ManageService;
import com.mazhe.service.ProductService;
import com.mazhe.service.UploadService;
import com.mazhe.token.CheckToken;
import com.mazhe.token.LoginToken;
import com.mazhe.util.HttpsUntils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/wechar")
@Slf4j
@Api(description = "微信小程序接口api")
public class WecharController {

    @Value("${wx.appId}")
    private String wxappid;

    @Value("${wx.secret}")
    private String wxsecret;

    @Value("${wx.url}")
    private String wxurl;


    @Autowired
    private ProductService productService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ManageService manageService;


    //jwt 登录token验证
    @ApiOperation(value="jwt 登录token验证", notes="jwt 登录token验证")
    @GetMapping(value = "/get/token")
    @LoginToken
    public Object gettoken(HttpServletRequest request, HttpServletResponse response) {

        return new ResponseEntity<>(manageService.getToken(response), HttpStatus.OK);
    }


    @ApiOperation(value="获取所有商品（produce）明细", notes="获取所有商品（produce）明细")
    @GetMapping(value = "product/find/all")
    public Object findAllProduct(HttpServletRequest request ) {
        log.info("find all product - start");
        return new ResponseEntity<>(productService.productFindAll(), HttpStatus.OK);
    }


    @ApiOperation(value="根据id获取商品", notes="根据类型获取商品")
    @GetMapping(value = "product/find/type/{typeId}")
    public Object findProductByType(HttpServletRequest request ,@PathVariable("typeId") Long typeId ) {
        return new ResponseEntity<>(productService.productFindByTypeId(typeId), HttpStatus.OK);
    }

    @ApiOperation(value="根据类型获取商品", notes="根据类型获取商品")
    @GetMapping(value = "product/find/{Id}")
    public Object findProductById(HttpServletRequest request ,@PathVariable("Id") Long id ) {

        return new ResponseEntity<>(productService.productFindById(id), HttpStatus.OK);
    }

    @ApiOperation(value="添加商品商品produce（添加时ID设置为空）,更新时Id不为空", notes="添加商品商品produce（添加时ID设置为空），更新时Id不为空")
    @PostMapping(value = "product/add")
    public  Object  addProduct(HttpServletRequest requestBack, @RequestBody Product product){
        return new ResponseEntity<>( productService.productSave(product), HttpStatus.OK);
    }


    @ApiOperation(value="获取所有商品类别（produceType）", notes="获取所有商品类别（produceType）")
    @GetMapping(value = "productype/find/all")
    public Object findAllProductType(HttpServletRequest request ) {
        log.info("find all productType - start");
        return new ResponseEntity<>(productService.productTypeFindAll(), HttpStatus.OK);
    }

    @ApiOperation(value="添加商品类别produceType（添加时ID设置为空），更新时Id不为空", notes="添加商品商品produceType（添加时ID设置为空），更新时Id不为空")
    @PostMapping(value = "productype/add")
    public  Object  addProductType(HttpServletRequest requestBack, @RequestBody ProductType productType){
        return new ResponseEntity<>( productService.productTypeSave(productType), HttpStatus.OK);
    }

    /**
     * 实现文件上传
     * */
    @ApiOperation(value="文件上传", notes="文件上传")
    @PostMapping("fileUpload")
    public Object fileUpload(@RequestParam("fileName") MultipartFile file){
        if(file.isEmpty()){
            return new ResponseEntity<>( "文件不能为空", HttpStatus.OK);
        }

        return new ResponseEntity<>(BaseMessage.Success("URL:"+uploadService.saveFile(file)), HttpStatus.OK);
    }





    //微信登陆获取openid 和session_key
    @ApiOperation(value="微信登陆获取openid", notes="微信登陆获取openid")
    @GetMapping(value = "/wechar/login")
    @LoginToken
    public Object wecharlogin(@RequestParam(name = "code", required = false, defaultValue = "") String code,HttpServletResponse response) {
        log.info("微信code:{}", code);
        Map map = new HashMap();
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("code", 0);
            map.put("msg", "code 不能为空");
            return new ResponseEntity<>(BaseMessage.Null( map), HttpStatus.OK);
        }
        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = wxappid;
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = wxsecret;
        //授权（必填）
        String grant_type = "authorization_code";

        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpsUntils.sendGet(wxurl, params);
        //解析相应内容（转换成json对象）
        log.info("微信返回：{}", sr);
        if (StringUtils.isEmpty(sr)) {
            map.put("code", 0);
            map.put("msg", "微信返回为空");
            return new ResponseEntity<>(BaseMessage.Null( map), HttpStatus.OK);
        }
        JSONObject json = JSONObject.parseObject(sr);
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        //用户登录保存

        //获取会话密钥（session_key）
        String session_key = (String) json.get("session_key");

        //获取会话密钥（errcode）
        String errcode = "";
        if (null != json.get("errcode")) {
            errcode = json.get("errcode").toString();
        }
        //获取会话密钥（errmsg）
        if(StringUtils.isNotBlank(openid)){
            //保存用户openId到数据库
            BaseMessage user=manageService.userAdd(openid,response);
            return new ResponseEntity<>( BaseMessage.Success(user), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( BaseMessage.Fail("微信返回无openId"), HttpStatus.OK);
        }

    }

    @ApiOperation(value="修改更新用户地址，添加是ID为null,更新时Id为地址id", notes="修改更新用户地址，添加是ID为null,更新时Id为地址id")
    @PostMapping(value = "adress/save")
    public  Object  addAdress(HttpServletRequest requestBack, @RequestBody Adress adress){
        return new ResponseEntity<>( manageService.adressAdd(adress), HttpStatus.OK);
    }



    @ApiOperation(value="根据openId查询所有地址", notes="根据openId查询所有地址")
    @GetMapping(value = "adress/findbyuser")
    @CheckToken
    public Object findAdressByOpenId(HttpServletRequest request ) {
        log.info("find all productType - start");
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String openId;
        try {
            openId = JWT.decode(token).getClaim("id").asString();
        } catch (JWTDecodeException j) {
            throw new RuntimeException("访问异常！");
        }
        return new ResponseEntity<>(manageService.adressByOpenId(openId), HttpStatus.OK);
    }

    //用户添加购物车
    @ApiOperation(value="用户添加购物车", notes="用户添加购物车")
    @PostMapping(value = "add/shop")
    public  Object  guestAddShop(HttpServletRequest requestBack, @RequestBody List<OrderDetail> orderDetailList){
        return new ResponseEntity<>( manageService.addShop(orderDetailList), HttpStatus.OK);
    }

    //用户确认下单
    @ApiOperation(value="用户确认下单", notes="用户确认下单")
    @PostMapping(value = "order/confirm")
    public  Object  orderConfirm(HttpServletRequest requestBack, @RequestBody OrderCreate orderCreate){
        return new ResponseEntity<>( manageService.orderCreate(orderCreate), HttpStatus.OK);
    }

    //用户历史订单查询
    @ApiOperation(value="用户历史订单查询", notes="用户历史订单查询")
    @GetMapping(value = "order/queryallbyuser/{pagenumber}/{pagesize}")
    @CheckToken
    public Object orderQueryAll(HttpServletRequest request ,@PathVariable("pagesize") int pagesize
            ,@PathVariable("pagenumber") int pagenumber) {
        log.info("find all productType - start");
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String openId;
        try {
            openId = JWT.decode(token).getClaim("id").asString();
        } catch (JWTDecodeException j) {
            throw new RuntimeException("访问异常！");
        }
        return new ResponseEntity<>(manageService.queryOrderByOpenIdPage(openId,pagenumber,pagesize), HttpStatus.OK);
    }

    //用户昨日订单查询
    @ApiOperation(value="用户昨日订单查询", notes="用户昨日订单查询")
    @GetMapping(value = "order/queryestdate/user")
    @CheckToken
    public Object orderQueryYestdate(HttpServletRequest request ) {
        log.info("find all productType - start");
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String openId;
        try {
            openId = JWT.decode(token).getClaim("id").asString();
        } catch (JWTDecodeException j) {
            throw new RuntimeException("访问异常！");
        }
        return new ResponseEntity<>(manageService.queryOrderByOpenIdYestdate(openId), HttpStatus.OK);
    }

    //用户最新购物车查询
    @ApiOperation(value="用户最新购物车查询", notes="用户最新购物车查询")
    @GetMapping(value = "order/shopbyuser")
    @CheckToken
    public Object orderShopQuery(HttpServletRequest request ) {
        log.info("find all productType - start");
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        String openId;
        try {
            openId = JWT.decode(token).getClaim("id").asString();
        } catch (JWTDecodeException j) {
            throw new RuntimeException("访问异常！");
        }
        return new ResponseEntity<>(manageService.queryShopByOrderSqe(openId), HttpStatus.OK);
    }

}
