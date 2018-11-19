package org.crown.controller;

import org.crown.common.kit.JacksonUtils;
import org.crown.emuns.UserStatusEnum;
import org.crown.framework.SuperRestControllerTest;
import org.crown.framework.test.ControllerTest;
import org.crown.model.dto.TokenDTO;
import org.crown.model.entity.User;
import org.crown.model.parm.UserInfoPARM;
import org.crown.model.parm.UserPARM;
import org.crown.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * <p>
 * UserRestController 测试类
 * </p>
 *
 * @author Caratacus
 */
public class UserRestControllerTest extends SuperRestControllerTest implements ControllerTest {

    @Autowired
    private UserRestController restController;

    @Autowired
    private IUserService userService;

    private MockMvc mockMvc;
    private TokenDTO token;

    @Before
    @Override
    public void before() {
        mockMvc = getMockMvc(restController);
        token = userService.getToken(userService.getById(1));

    }

    @Test
    public void getUserDetails1() throws Exception {
        User user = new User();
        user.setId(2);
        user.setIp("2222");
        userService.update(user, Wrappers.<User>update().set("uid", 22).eq("uid", 1));
    }

    @Test
    public void getUserDetails() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/details").header("Authorization", "Bearer " + token.getToken()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateUserInfo() throws Exception {
        UserInfoPARM userInfoPARM = new UserInfoPARM();
        userInfoPARM.setNickname("Crown");
        userInfoPARM.setEmail("caratacus@qq.com");
        userInfoPARM.setPhone("13712345678");
        mockMvc.perform(
                MockMvcRequestBuilders.put("/user/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token.getToken())
                        .content(JacksonUtils.toJson(userInfoPARM))
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user")
                        .header("Authorization", "Bearer " + token.getToken())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void get() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/1")
                        .header("Authorization", "Bearer " + token.getToken())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void resetPwd() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/user/" + token.getUid() + "/password/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token.getToken())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateStatus() throws Exception {
        UserPARM userPARM = new UserPARM();
        userPARM.setStatus(UserStatusEnum.NOMAL);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/user/" + token.getUid() + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JacksonUtils.toJson(userPARM))
                        .header("Authorization", "Bearer " + token.getToken())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void create() throws Exception {
        UserPARM userPARM = new UserPARM();
        userPARM.setLoginName("12121");
        userPARM.setNickname("222");
        userPARM.setEmail("11@qq.com");
        userPARM.setPhone("13617828937");
        userPARM.setStatus(UserStatusEnum.DISABLE);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JacksonUtils.toJson(userPARM))
                        .header("Authorization", "Bearer " + token.getToken())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void update() throws Exception {
        UserPARM userPARM = new UserPARM();
        userPARM.setLoginName("12121");
        userPARM.setNickname("222");
        userPARM.setEmail("11@qq.com");
        userPARM.setPhone("13617828937");
        userPARM.setStatus(UserStatusEnum.DISABLE);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JacksonUtils.toJson(userPARM))
                        .header("Authorization", "Bearer " + token.getToken())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}