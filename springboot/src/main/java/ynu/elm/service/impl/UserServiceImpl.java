package ynu.elm.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import ynu.elm.entity.User;
import ynu.elm.service.UserService;
import ynu.elm.mapper.IUserMapper;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private IUserMapper userMapper;

    @Override
    public User getUserByIdByPass(String userId,String password){
        return userMapper.getUserByIdByPass(userId,password);
    }

    @Override
    public int getUserById(String userId){
        return userMapper.getUserById(userId);
    }

    @Override
    public int saveUser(User user){
        return userMapper.saveUser(user);
    }
}
