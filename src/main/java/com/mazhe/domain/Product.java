package com.mazhe.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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

@ApiModel(value = "Product", description = "商品对象")
@Data
@Entity
@Table(name = "Product")
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
public class Product  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(dataType="Long",value = "id唯一标识")
    private Long ID;

    @NotNull
    @Column(name = "productname",columnDefinition=("varchar(225)  COMMENT '产品名称'"))
    @ApiModelProperty(value = "产品名称")
    private String productName;

    @Column(name = "message",columnDefinition=("varchar(225)  COMMENT '备注信息'"))
    @ApiModelProperty(value = "备注信息")
    private String message;


    @Column(name = "imageurl",columnDefinition=("varchar(225)  COMMENT '图片地址'"))
    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @NotNull
    @Column(name = "typeid",columnDefinition=("int  COMMENT '产品类型ID'"))
    @ApiModelProperty(value = "产品类型ID")
    private Long typeId;

    @NotNull
    @Column(name = "price",precision = 17, scale = 2 ,columnDefinition=("decimal(17,2)  COMMENT '价格'"))
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @Column(name="createdate")
    @ApiModelProperty(dataType="String",value = "创建时间")
    private Timestamp createDate;


}
