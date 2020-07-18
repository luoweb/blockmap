package com.nowcoder.community.service;

import com.nowcoder.community.dao.ConfirmInformationPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.ConfirmInformationPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfirmInformationPostService {
    @Autowired
    ConfirmInformationPostMapper confirmInformationPostMapper;
    UserMapper userMapper;
   @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)  //事务
    public int addConfirmInformation(ConfirmInformationPost confirmInformationPost){   //上报确诊信息
        int id=101;
        int a= confirmInformationPostMapper.postConfirmInformation(confirmInformationPost);
        //userMapper.updateUserStatus(id,0);    //将用户状态设置为确诊，但是由于目前无法获取用户id,暂时无法实现
        return a;
    }
}
