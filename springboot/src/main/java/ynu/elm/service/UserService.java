package ynu.elm.service;

import ynu.elm.entity.User;

public interface UserService {

    public User getUserByIdByPass(String userId,String password);

    public int getUserById(String userId);

    public int saveUser(User user);
}
