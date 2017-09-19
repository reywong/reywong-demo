package cn.com.yto.reywong.tool.springboot.test.controller;


import cn.com.yto.reywong.tool.springboot.test.business.IUserInfoBiz;
import cn.com.yto.reywong.tool.springboot.test.domain.UserInfo;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangrui on 2017/7/31.
 */
@RestController
@RequestMapping(value = "/userInfo")
@Api(value = "测试接口")
public class UserInfoController {
    private Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private IUserInfoBiz userInfoBizImpl;


    @ApiOperation(value = "获取用户列表", httpMethod = "GET", notes = "调用test get")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", paramType = "path", required = true, dataType = "int")
//            @ApiImplicitParam(name = "model", value = "用户详细实体user", required = true, dataType = "Model")
    })
    @RequestMapping(value = "/getUserInfo/{id}", method = RequestMethod.GET)
    public String getUserInfo(@PathVariable Integer id) {
        UserInfo userInfo = userInfoBizImpl.getUserInfo(id);
        return JSON.toJSONString(userInfo);
    }

    @RequestMapping(value = "/toIndex/{id}")
    public String toIndex(@PathVariable Integer id, Model model) {
        UserInfo userInfo = userInfoBizImpl.getUserInfo(id);
        model.addAttribute("userInfo", userInfo);
        return "index";
    }
}
