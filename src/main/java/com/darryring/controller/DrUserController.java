package com.darryring.controller;

import com.darryring.pojo.DrUser;
import com.darryring.service.DrUserService;
import com.darryring.util.ImageCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SessionAttributes({"user"})
@Controller
public class DrUserController {

    @Autowired
    ImageCreate imageCreate;

    @Autowired
    private DrUserService dus;

    @GetMapping(value = "/regist")
    public String regist(){
        return "register";
    }
    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    //验证注册
    @ResponseBody
    @RequestMapping("/vCode")
    public String vCode(String code,String password,String repwd,HttpServletRequest  request){
        System.out.println("进入验证注册方法。。。。。33");
        String verifyCode = (String)request.getSession().getAttribute("verificationCode");
        if(password.equals(repwd) && code.equalsIgnoreCase(verifyCode)){
            return "true";
        }
        return "false";
    }

    //注册
    @PostMapping("/regist")
    public String regist(DrUser user){
        if(dus.insertUser(user)>0){
            return "login";
        }
        return "register";
    }

    //登录
    @RequestMapping(value = "/login",method = RequestMethod.POST )
    public String login(String phone, String password,Model mo){
        System.out.println("进入登陆。。。。。");
        DrUser user = dus.findByNamePw(phone,password);
        if(user!=null){
            System.out.println("user.............");
            mo.addAttribute("user",user);
            return "redirect:/index";
        }
        return "login";
    }

    //其他登录
    @ResponseBody
    @RequestMapping(value = "/otherlogin",method = RequestMethod.POST )
    public String otherlogin(String phone, String password,Model mo){
        System.out.println("进入其他登陆。。。。。");
        DrUser user = dus.findByNamePw(phone,password);
        if(user!=null){
            System.out.println("user.............");
            mo.addAttribute("user",user);
            return "true";
        }
        return "false";
    }

    /**
     * 生成验证码
     */
    @RequestMapping(value = "/getVerify",method = RequestMethod.GET )
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            imageCreate.createImage(request, response);//输出验证码图片方法
            String verifyCode = (String)request.getSession().getAttribute("verificationCode");
            System.out.println("verifyCode:"+verifyCode);
        } catch (Exception e) {
            System.out.println("获取验证码失败！");
        }
    }


}
