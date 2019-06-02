package com.mazhe.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2019/5/15.
 */
@Data
@Entity
@Table(name = "productType")
@JsonAutoDetect
@ApiModel(value = "ProductType", description = "产品类别")
public class  ProductType  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(dataType="Long",value = "id唯一标识")
    private Long ID;

    @NotNull
    @Column(name = "typeName",columnDefinition=("varchar(225)  COMMENT '产品类型名称'"))
    @ApiModelProperty(value = "产品类型名称")
    private String typeName;


    @Column(name = "message",columnDefinition=("varchar(225)  COMMENT '备注信息'"))
    @ApiModelProperty(value = "备注信息")
    private String message;

    @Column(name="createDate")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;

}
