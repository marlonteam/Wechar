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
@ApiModel(value = "Adress", description = "用户信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "adress")
@JsonAutoDetect
public class Adress   implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @ApiModelProperty(dataType="Long",value = "id唯一标识")
    private Long ID;

    //openId
    @Column(name = "openId",columnDefinition=("varchar(225)  COMMENT '小程序用户唯一标识'"))
    @ApiModelProperty(value = "小程序用户唯一标识")
    private String openId;

    @Column(name = "adress",columnDefinition=("varchar(225)  COMMENT '收货地址'"))
    @ApiModelProperty(value = "收货地址")
    private String adress;

    @Column(name = "userName",columnDefinition=("varchar(225)  COMMENT '联系人'"))
    @ApiModelProperty(value = "联系人")
    private String userName;

    @Column(name = "phone",columnDefinition=("varchar(225)  COMMENT '联系电话'"))
    @ApiModelProperty(value = "联系电话")
    private String phone;

    @Column(name = "defaultFlag",columnDefinition=("varchar(225)  COMMENT '默认收获地址 true为默认，false为普通地址'"))
    @ApiModelProperty(value = "默认收获地址 true为默认，false为普通地址")
    private String defaultFlag;


    @Column(name = "message",columnDefinition=("varchar(225)  COMMENT '备注信息'"))
    @ApiModelProperty(value = "备注信息")
    private String message;

    @Column(name="createDate")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;


}
