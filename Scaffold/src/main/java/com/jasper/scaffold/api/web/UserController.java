package com.jasper.scaffold.api.web;


import com.jasper.scaffold.api.entity.User;
import com.jasper.scaffold.api.service.IUserService;
import com.jasper.scaffold.common.mvc.BaseController;
import com.jasper.scaffold.common.mvc.Result;
import com.jasper.scaffold.common.mvc.ResultGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * 用户 模块相关接口-
 *
 * @author codegenerator
 * @since 2020-06-28
 * @index 1
 * @book 用户模块
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;


    /**
     * 查询全量用户列表
     * @return
     */
    @GetMapping("list")
    public Result<List<User>> getUserList() {
        return ResultGenerator.genSuccessResult(userService.list());
    }

    /**
     * 根据Id查询用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        return ResultGenerator.genSuccessResult(userService.getById(id));
    }
}

