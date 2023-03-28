package com.kangjun.common.mapstruct;

import com.kangjun.common.mapstruct.mapstruct.UserConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangjun
 * @date 2023年03月28日 13:42
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {


    @PostMapping("/users/reg")
    public void reg(@RequestBody UserRegisterReq userRegisterReq) {
        // 省略password 与 confirmPassword等值判断
        User user = new User();
        user.setEmail(userRegisterReq.getEmail());
        user.setPassword(userRegisterReq.getPassword());
        user.setUsername(userRegisterReq.getUsername());
        // 保存user...

    }

    @PostMapping("/users/reg")
    public void reg2(@RequestBody UserRegisterReq userRegisterReq) {
        // 省略password 与 confirmPassword等值判断
        User user = UserConverter.INSTANCE.toUser(userRegisterReq);
        // 保存user...
    }

}
