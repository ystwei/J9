package com.weikun.service;

import com.weikun.dao.IUserDAO;
import com.weikun.dao.UserDAOImpl;
import com.weikun.vo.BBSUser;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2016/11/6.
 */
public class UserServiceImpl implements  IUserService {

    private Map<String,String> picType=new HashMap<String,String>();
    private File tmpDir;
    private File saveDir;
    public UserServiceImpl(){
        //允许上传的文件类型
        picType.put("image/jpeg", ".jpg");
        picType.put("image/gif", ".gif");
        picType.put("image/x-ms-bmp", ".bmp");
        picType.put("image/png", ".png");



        String savePath = "" + System.getProperty("file.separator") + "upload";

        saveDir = new File(savePath);//完毕后 保存路径

        if (!saveDir.isDirectory())
            saveDir.mkdir();

    }
    private IUserDAO dao=new UserDAOImpl();
    public BBSUser login(BBSUser user) {
        return dao.login(user);
    }

    public boolean register(BBSUser user) {
        return dao.register(user);
    }

    /**
     *
     * @param :spath:你的项目所在的路径
     * @return:一个完整的进行保存的bbsuser对象。
     */
    public BBSUser uploadPic(FileItemIterator fii,String spath) {
        BBSUser user=new BBSUser();
        try {//看文件遍历器中的每个项目
            while(fii.hasNext()){
                FileItemStream fs=fii.next();
                String name=fs.getFieldName();
                System.out.println(name);
                //是文件域，并且已经选中了文件
                String type=fs.getContentType();//上传文件类型
                InputStream stream=fs.openStream();
                if(!fs.isFormField()&&fs.getName().length()>0){//是文本框那些可输入的吗？

                    if(!picType.containsKey(type)){//是我们想要的文件类型
                        return null;

                    }


                    BufferedInputStream bis=new BufferedInputStream(stream);
                    UUID uuid=UUID.randomUUID();//文件名
                    String fileName=spath+"\\upload\\"+uuid.toString()+picType.get(type);
                    System.out.println(fileName);
                    BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(new File(fileName)));

                    Streams.copy(bis,bos,true);//拷贝完毕,上传成功
                    user.setPicPath(fileName);

                }else{//是了
                    switch(name){
                        case "reusername":
                            user.setUsername(Streams.asString(stream,"utf-8"));
                            break;
                        case "repassword":
                            user.setPassword(Streams.asString(stream,"utf-8"));
                            break;


                    }

                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public byte[] queryPicByid(int id) {
        return dao.queryPicByid(id);
    }
}
