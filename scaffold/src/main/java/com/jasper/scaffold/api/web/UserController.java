package com.jasper.scaffold.api.web;


import com.jasper.scaffold.api.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.jasper.scaffold.common.mvc.BaseController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表实体 前端控制器
 * </p>
 *
 * @author codegenerator
 * @since 2020-06-28
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;


    @GetMapping("list")
    public String getUserList() {
        return userService.list().toString();
    }
}

