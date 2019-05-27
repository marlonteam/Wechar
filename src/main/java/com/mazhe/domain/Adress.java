package com.mazhe.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
    @ApiModelProperty(value = "小程序用户唯一标识")
    private String adress;

    @Column(name = "defaultFlag",columnDefinition=("varchar(225)  COMMENT '默认收获地址 true为默认，false为普通地址'"))
    @ApiModelProperty(value = "小程序用户唯一标识")
    private String defaultFlag;


    @Column(name = "message",columnDefinition=("varchar(225)  COMMENT '备注信息'"))
    @ApiModelProperty(value = "备注信息")
    private String message;

    @Column(name="createdate")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createDate;


}
