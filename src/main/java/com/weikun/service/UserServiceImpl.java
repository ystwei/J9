package com.weikun.service;

import com.weikun.dao.IUserDAO;
import com.weikun.dao.UserDAOImpl;
import com.weikun.vo.BBSUser;

/**
 * Created by Administrator on 2016/11/6.
 */
public class UserServiceImpl implements  IUserService {
    private IUserDAO dao=new UserDAOImpl();
    public boolean login(BBSUser user) {


        return dao.login(user);
    }
}
