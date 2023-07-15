package com.myfood.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.myfood.common.R;
import com.myfood.entity.User;
import com.myfood.service.UserService;
import com.myfood.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/send_msg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhoneNumber();

        if(StringUtils.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);

            //SMSUtils.sendMessage("MyFood","",phone,code);

            session.setAttribute(phone,code);

            return R.success("Send code successfully");
        }

        return R.error("Send code failed");
    }


    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());

        String phoneNumber = map.get("phoneNumber").toString();

        String code = map.get("code").toString();

        Object codeInSession = session.getAttribute(phoneNumber);

        if(codeInSession != null && codeInSession.equals(code)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhoneNumber,phoneNumber);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                user = new User();
                user.setPhoneNumber(phoneNumber);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("Login failed");
    }

}
