package com.weikun.dao;

import com.weikun.vo.BBSUser;

/**
 * Created by Administrator on 2016/11/6.
 */
public interface IUserDAO {
    BBSUser login(BBSUser user);
    boolean register(BBSUser user);
    byte[] queryPicByid(int id);//通过id查找头像

}
