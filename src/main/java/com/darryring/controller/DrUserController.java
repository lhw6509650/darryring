package com.darryring.controller;

import com.darryring.pojo.DrUser;
import com.darryring.pojo.Dr_user_address;
import com.darryring.pojo.Dr_user_area;
import com.darryring.service.DrUserService;
import com.darryring.service.Dr_user_addressService;
import com.darryring.service.Dr_user_areaService;
import com.darryring.util.ImageCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SessionAttributes({"user"})
@Controller
public class DrUserController {

    @Autowired
    ImageCreate imageCreate;

    @Autowired
    private DrUserService dus;

    @Autowired
    private Dr_user_addressService duas;

    @Autowired
    private Dr_user_areaService dua;

    @GetMapping(value = "/regist")
    public String regist(){
        return "qianduan/register";
    }
    @GetMapping(value = "/login")
    public String login(){
        return "qianduan/login";
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
            return "qianduan/login";
        }
        return "qianduan/register";
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
        return "qianduan/login";
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

    //查看个人资料
    @RequestMapping("/usershow")
    public ModelAndView usershow(ModelAndView mav, HttpSession session){
        System.out.println("usershow...");
        DrUser du = (DrUser)session.getAttribute("user");
        DrUser user = dus.findAll(du.getUserId());
        if(user!=null){
            mav.addObject("user",user);
        }
        mav.setViewName("qianduan/usershow");
        return mav;
    }

    //修改个人资料
    @RequestMapping("/improve")
    public ModelAndView improve(String nickname,
                                String realname,
                                String sex,
                                String identityCode,
                                String email,
                                String lovesign,
                                ModelAndView mav,
                                HttpSession session){
        DrUser du = new DrUser();
        DrUser usr = (DrUser)session.getAttribute("user");
        du.setUserId(usr.getUserId());
        du.setUserName(realname);
        du.setLoginName(nickname);
        du.setSex((sex));
        du.setIdentityCode(identityCode);
        du.setEmail(email);
        du.setLoveWord(lovesign);
        int iii = dus.improve(du);
        mav.setViewName("forward:/usershow");
        System.out.println("修改："+iii);
        return mav;
    }

    //修改密码
    @ResponseBody
    @RequestMapping("/resetPwd")
    public Integer resetPwd(String oldPwd,String resetPwd,HttpSession session){
        Integer flag = 0;
        DrUser user = (DrUser) session.getAttribute("user");
        System.out.println("userId:"+user.getUserId());
        List<DrUser> use = dus.queryByPwd(oldPwd,user.getUserId());
        if (use.size()<1){
            System.out.println("原密码输入错误");
            flag = 1;
        }else {
            DrUser uu = new DrUser();
            uu.setUserId(user.getUserId());
            uu.setPassword(resetPwd);
            int reset = dus.resetPwd(uu);
            if (reset>0){
                System.out.println("密码修改成功");
                flag = 2;
            }else{
                System.out.println("密码修改失败");
                flag = 3;
            }
        }
        return flag;
    }

    @ResponseBody
    @RequestMapping("/islogin")
    public Boolean islogin(HttpSession session){
        Boolean flag = false;
        DrUser user = (DrUser)session.getAttribute("user");
        if (user!=null)
            flag = true;
        return flag;
    }



    @RequestMapping("/password")
    public ModelAndView password(ModelAndView mav,HttpSession session){
        DrUser user = (DrUser)session.getAttribute("user");
        if(user==null){
            mav.setViewName("forward:/login");
            return mav;
        }
        mav.setViewName("qianduan/password");
        return mav;
    }

    //地址管理
    @RequestMapping("/queryAddress")
    public ModelAndView queryAddress(ModelAndView mav,HttpSession session){
        DrUser user = (DrUser)session.getAttribute("user");
        if(user==null){
            mav.setViewName("forward:/login");
            return mav;
        }
        List<Dr_user_address> address = duas.queryAddress(user.getUserId());
        if(address.size()>0){
            System.out.println("queryAddress......");
            mav.addObject("addressList",address);
        }else{
            mav.addObject("addressList",null);
        }
        mav.setViewName("qianduan/useraddress");
        return mav;
    }

    //查询所有省
    @ResponseBody
    @RequestMapping("/queryAllSheng")
    public List<Dr_user_area> qureyAllSheng(ModelAndView mav) {
        System.out.println("queryAllSheng......");
        return dua.queryAllSheng();
    }

    //根据省份查询所有城市
    @ResponseBody
    @RequestMapping("/qureyShiBySheng")
    public List<Dr_user_area> qureyShiBySheng(int sheng,ModelAndView mav) {
        System.out.println("qureyShiBySheng......");
        return dua.queryShiBySheng(sheng);
    }

    //根据城市查询所有区县
    @ResponseBody
    @RequestMapping("/qureyQuByShi")
    public List<Dr_user_area> qureyQuByShi(int shi, ModelAndView mav) {
        System.out.println("qureyQuByShi......");
        return dua.queryQuByShi(shi);
    }

    //添加用户地址
    @ResponseBody
    @RequestMapping("/insertAddress")
    public ModelAndView insertAddress(String receiver,String detailadd,String phone,ModelAndView mav,HttpSession session){
        DrUser user = (DrUser)session.getAttribute("user");
        List<Dr_user_address> add = duas.queryAddress(user.getUserId());
        int isDefault = 0;
        if(add.size()>0){
            isDefault=1;
        }
        int i = duas.insertAddress(user.getUserId(),detailadd,receiver,phone,isDefault);
        if (i>0)
            System.out.println("地址添加成功。。。");
        else
            System.out.println("地址添加失败。。。");
        mav.setViewName("forward:/queryAddress");
        return mav;
    }

    //删除用户地址
    @ResponseBody
    @RequestMapping("/removeAddress")
    public ModelAndView removeAddress(Integer addressId,ModelAndView mav){
        int i = duas.removeAddress(addressId);
        if (i>0)
            System.out.println("用户地址删除成功。。。");
        else
            System.out.println("用户地址删除失败。。。");
        mav.setViewName("forward:/queryAddress");
        return mav;
    }

    //根据addressId查询该条地址信息
    @ResponseBody
    @RequestMapping("/queryAddressByAid")
    public List<Dr_user_address> queryAddressByAid(Integer aid){
        Dr_user_address add = duas.queryAddressByAid(aid);
        List<Dr_user_address> adds = new ArrayList<Dr_user_address>();
        adds.add(add);
        return adds;
    }

    //修改用户地址
    @ResponseBody
    @RequestMapping("/updateAddress")
    public ModelAndView updateAddress(int upaddId,Dr_user_address address,ModelAndView mav){

        Dr_user_address add = new Dr_user_address();
        add.setAddressId(upaddId);
        add.setAddress(address.getAddress());
        add.setPhone(address.getPhone());
        add.setReceiver(address.getReceiver());
        int update = duas.updateAddress(add);
        if (update>0)
            System.out.println("修改成功");
        else
            System.out.println("修改失败");
        mav.setViewName("forward:/queryAddress");
        return mav;
    }

    //(后台)根据多条件带分页查询用户
    @ResponseBody
    @RequestMapping("/selectusers")
    public Object selectusers(DrUser drUser,Integer page,Integer  rows,String sort,String order){
        System.out.println("后台用户名:"+drUser.getUserName());
        DrUser drUser1 = new DrUser();
        if(drUser.getUserName()!=null&&drUser.getUserName()!=""){
            drUser1.setUserName(drUser.getUserName());
        }

        Map<String,Object> map =new HashMap<String,Object>();
        drUser1.setPage((page-1)*rows);
        System.out.println("页码："+page);
        drUser1.setRows(rows);
        drUser1.setSort(sort);
        drUser1.setOrder(order);
        map.put("total",dus.selectUser());
        map.put("rows",dus.selectAllUserByCon(drUser));
        return map;
    }

    //(后台)新增用户
    @ResponseBody
    @RequestMapping("/adduser")
    public Object adduser(DrUser drUser){
        dus.insertUser(drUser);
        return drUser;
    }
    //(后台)更新用户
    @ResponseBody
    @RequestMapping("/updateUser")
    public Object updateUser(DrUser drUser){
        System.out.println("进入修改用户");
        if(drUser.getSex()!=null && drUser.getSex()!="" && drUser.getSex().equals("1")) {
              drUser.setSex("男");
        }else{
            drUser.setSex("女");
        }
        if(drUser.getUsertype()!=null && drUser.getUsertype()!="" && drUser.getUsertype().equals("0")) {
            drUser.setUsertype("管理员");
        }else{
            drUser.setUsertype("普通用户");
        }
        System.out.println("用户名："+drUser.getUserName());
        Map<String,Object> m = new HashMap<String,Object>();
        boolean flag;
        String message="";
        if(drUser.getUserId()==null){
            if (dus.insertUsers(drUser)) {
                flag=true;
                message="添加成功！";
            } else {
                flag=false;
                message="添加失败！";
            }
        }else {
            if (dus.updateUsers(drUser)) {
                flag=true;
                message="修改成功！";
            } else {
                flag=false;
                message="修改失败！";
            }
        }
        m.put("flag",flag);
        m.put("message",message);
        return m;
    }


}
