package com.mazhe.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2019/5/15.
 */
@ApiModel(value = "OrderDetail", description = "订单明细")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderDetail")
@JsonAutoDetect
public class OrderDetail   implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(dataType="Long",value = "id唯一标识")
    private Long ID;

    @Column(name = "openId",columnDefinition=("varchar(225)  COMMENT '小程序用户唯一标识'"))
    @ApiModelProperty(value = "小程序用户唯一标识")
    private String openId;

    //订单号
    @Column(name = "orderSeq",columnDefinition=("varchar(225)  COMMENT '订单号'"))
    @ApiModelProperty(value = "订单号")
    @NotNull
    private String orderSeq;

    //订单状态 1进入购物车,2 订单用户确认（现在为最终）
    @Column(name = "status",columnDefinition=("varchar(225)  COMMENT '订单状态 1进入购物车,2 订单用户确认'"))
    @ApiModelProperty(value = "订单状态 1进入购物车,2 订单用户确认")
    private String status;

    //商品金额
    @Column(name = "price",columnDefinition=("decimal(17,2)  COMMENT '订单总金额'"))
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal price;

    //商品id
    @Column(name = "produceId",columnDefinition=("decimal(17,2)  COMMENT '订单总金额'"))
    @ApiModelProperty(value = "订单总金额")
    private Long produceId;

    //商品名称
    @Column(name = "productName",columnDefinition=("varchar(225)  COMMENT '产品名称'"))
    @ApiModelProperty(value = "产品名称")
    @JsonIgnore
    private String productName;

    //url
    @Column(name = "imageUrl",columnDefinition=("varchar(225)  COMMENT '图片地址'"))
    @ApiModelProperty(value = "图片地址")
    @JsonIgnore
    private String imageUrl;


    @Column(name = "message",columnDefinition=("varchar(225)  COMMENT '备注信息'"))
    @ApiModelProperty(value = "备注信息")
    private String message;

    @Column(name="createDate")
    @ApiModelProperty(value = "下单时间")
    private Timestamp createDate;
}
