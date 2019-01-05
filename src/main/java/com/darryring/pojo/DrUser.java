package com.darryring.pojo;

import lombok.Getter;
import lombok.Setter;

/*
 * 用户表
 */
@Setter
@Getter
public class DrUser {

	private int userId; // 用户id

	private String userName; // 真实姓名

	private String loginName; // 用户昵称

	private String password; // 密码

	private int sex; // 性别

	private String identityCode; // 身份证号码

	private String email; // 邮箱

	private String phone; // 手机号码

	private String touRoute; // 用户头像

	private String loveWord; // 真爱宣言

	private int usertype;//用户类型

}
