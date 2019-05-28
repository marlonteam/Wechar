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
import java.sql.Timestamp;

/**
 * Created by Administrator on 2019/5/15.
 */
@ApiModel(value = "Product", description = "商品对象")
@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
public class User   implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(dataType="Long",value = "id唯一标识")
    private Long ID;

    //openId
    @Column(name = "openId",columnDefinition=("varchar(225)  COMMENT '小程序用户唯一标识'"))
    @ApiModelProperty(value = "小程序用户唯一标识")
    private String openId;


    @Column(name = "message",columnDefinition=("varchar(225)  COMMENT '备注信息'"))
    @ApiModelProperty(value = "备注信息")
    private String message;

    @Column(name = "role",columnDefinition=("varchar(225)  COMMENT '角色'"))
    @ApiModelProperty(value = "角色")
    @JsonIgnore
    private String role;

    @Column(name="createdate")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;
}
