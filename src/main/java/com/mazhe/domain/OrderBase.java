package com.mazhe.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
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

    //订单总金额


    //订单状态 1进入购物车,2 订单用户确认（现在为最终）

    //订单金额

    //留言

    //地址ID

    //收获地址

    //联系方式

    //收货人



    @Column(name = "message",columnDefinition=("varchar(225)  COMMENT '备注信息'"))
    @ApiModelProperty(value = "备注信息")
    private String message;

    @Column(name="createdate")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;
}
