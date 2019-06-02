package com.mazhe.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2019/5/15.
 */
@ApiModel(value = "OrderBase", description = "订单信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderBase")
@JsonAutoDetect
public class OrderBase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(dataType="Long",value = "id唯一标识")
    private Long ID;

    //openId
    @Column(name = "openId",columnDefinition=("varchar(225)  COMMENT '小程序用户唯一标识'"))
    @ApiModelProperty(value = "小程序用户唯一标识")
    private String openId;

    //订单号
    @Column(name = "orderSeq",columnDefinition=("varchar(225)  COMMENT '订单号'"))
    @ApiModelProperty(value = "订单号")
    private String orderSeq;

    //订单状态 1进入购物车,2 订单用户确认（现在为最终）
    @Column(name = "status",columnDefinition=("varchar(225)  COMMENT '订单状态 1进入购物车,2 订单用户确认'"))
    @ApiModelProperty(value = "订单状态 1进入购物车,2 订单用户确认")
    private String status;

    //订单总金额  decimal(17,2)
    @Column(name = "totalPrice",columnDefinition=("decimal(17,2)  COMMENT '订单总金额'"))
    @ApiModelProperty(value = "订单总金额")
     private BigDecimal totalPrice;

    //邮费

    //留言
    @Column(name = "guestMessage",columnDefinition=("longtext  COMMENT '留言'"))
    @ApiModelProperty(value = "留言 ")
    private String guestMessage;


    //地址ID
    @Column(name = "AddressId",columnDefinition=("longtext  COMMENT '地址ID'"))
    @ApiModelProperty(value = "地址ID ")
    private Long AddressId;

    @Column(name = "adress",columnDefinition=("varchar(225)  COMMENT '收货地址'"))
    @ApiModelProperty(value = "收货地址")
    @JsonIgnore
    private String adress;

    @Column(name = "userName",columnDefinition=("varchar(225)  COMMENT '联系人'"))
    @ApiModelProperty(value = "联系人")
    @JsonIgnore
    private String userName;

    @Column(name = "phone",columnDefinition=("varchar(225)  COMMENT '联系电话'"))
    @ApiModelProperty(value = "联系电话")
    @JsonIgnore
    private String phone;


    @Column(name = "message",columnDefinition=("varchar(225)  COMMENT '备注信息'"))
    @ApiModelProperty(value = "备注信息")
    private String message;

    @Column(name="createDate")
    @ApiModelProperty(value = "下单时间")
    private Timestamp createDate;
}
