package com.jasper.scaffold.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jasper.scaffold.api.entity.User;
import com.jasper.scaffold.api.mapper.UserMapper;
import com.jasper.scaffold.api.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表实体 服务实现类
 * </p>
 *
 * @author codegenerator
 * @since 2020-06-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
