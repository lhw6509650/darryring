package com.darryring.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.PrimitiveIterator;

/*
 * 用户表
 */
@Setter
@Getter
public class DrUser {

	private Integer userId; // 用户id

	private String userName; // 真实姓名

	private String loginName; // 用户昵称

	private String password; // 密码

	private String sex; // 性别

	private String identityCode; // 身份证号码

	private String email; // 邮箱

	private String phone; // 手机号码

	private String touRoute; // 用户头像

	private String loveWord; // 真爱宣言

	private String usertype;//用户类型

	private Integer page;//页码

	private Integer rows;//行数

	private String sort;//排序

	private String order;//排序

}
