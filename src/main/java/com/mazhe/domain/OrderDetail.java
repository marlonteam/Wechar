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

    //订单号
    @Column(name = "openId",columnDefinition=("varchar(225)  COMMENT '小程序用户唯一标识'"))
    @ApiModelProperty(value = "小程序用户唯一标识")
    private String openId;

    //金额

    //状态

    //商品id

    //商品名称

    //url






    @Column(name = "message",columnDefinition=("varchar(225)  COMMENT '备注信息'"))
    @ApiModelProperty(value = "备注信息")
    private String message;

    @Column(name="createdate")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;
}
