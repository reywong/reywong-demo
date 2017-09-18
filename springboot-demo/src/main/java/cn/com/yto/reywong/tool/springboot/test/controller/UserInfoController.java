package cn.com.yto.reywong.tool.springboot.test.controller;


import cn.com.yto.reywong.tool.springboot.test.business.IUserInfoBiz;
import cn.com.yto.reywong.tool.springboot.test.domain.UserInfo;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangrui on 2017/7/31.
 */
@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoController {
    private Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private IUserInfoBiz userInfoBizImpl;


    @ApiOperation(value = "获取用户列表", httpMethod = "GET", notes = "调用test get")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户ID", paramType = "path", required = true, dataType = "String")
//            @ApiImplicitParam(name = "model", value = "用户详细实体user", required = true, dataType = "Model")
//    })
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public String getUserInfo() {
        UserInfo userInfo = userInfoBizImpl.getUserInfo(1);
        return JSON.toJSONString(userInfo);
    }

//    @RequestMapping(value = "/getUserInfo/{id}")
//    public String getUserInfo(@PathVariable Integer id, Model model) {
//        UserInfo userInfo = userInfoBizImpl.getUserInfo(id);
//        model.addAttribute("userInfo", userInfo);
//        return "index";
//    }
}
