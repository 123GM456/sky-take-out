package com.GM.service;

import com.GM.dto.UserLoginDTO;
import com.GM.entity.User;
import com.GM.vo.UserLoginVO;

public interface UserService {

    /**
     * 用户登录。
     */
    User WeChatLogin(UserLoginDTO userLoginDTO);
}
