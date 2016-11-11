package com.weikun.service;

import com.weikun.vo.Article;
import com.weikun.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;

import java.util.List;

/**
 * Created by Administrator on 2016/11/6.
 */
public interface IUserService {
    BBSUser login(BBSUser user);
    public boolean register(BBSUser user);
    public BBSUser uploadPic(FileItemIterator fii,String spath);
    public byte[] queryPicByid(int id) ;

}
