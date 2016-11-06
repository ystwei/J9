package com.weikun.dao;

import com.weikun.vo.BBSUser;

/**
 * Created by Administrator on 2016/11/6.
 */
public interface IUserDAO {
    boolean login(BBSUser user);
}
