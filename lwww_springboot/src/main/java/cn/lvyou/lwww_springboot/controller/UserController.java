package cn.lvyou.lwww_springboot.controller;

import cn.lvyou.lwww_springboot.entiy.ResponseBean;
import cn.lvyou.lwww_springboot.entiy.User;
import cn.lvyou.lwww_springboot.service.UserService;
import com.ramostear.captcha.HappyCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@Slf4j
public class  UserController {
    @Value("${upload.imgurl}")
    private String imgurl;
    @Value("${upload.imgweb}")
    private String imgweb;

    @Autowired
    UserService userService;

    /**
     * 实现登录
     *
     * @param user 账号和密码
     * @return success
     */

    @RequestMapping("/login")
    @ResponseBody
    public ResponseBean login(@RequestBody User user, HttpServletRequest request) {
//        System.out.println(user);
        // 1. 校验验证码是否正确
        // 后端修改完代码记得重启服务器
        boolean verification = HappyCaptcha.verification(request, user.getCode(), true);
        if (!verification) {
            // 返回响应Bean
            return new ResponseBean(500, "验证码错误", null);
        }

        // 开始验证账号和密码
        User userResult = userService.login(user);
        if (userResult != null) {
            request.getSession().setAttribute("user",userResult);
            // 返回响应Bean
            return new ResponseBean(200, "登录成功", userResult);
        } else {
            // 返回响应Bean
            return new ResponseBean(500, "用户名或密码错误", null);
        }
//        return "success";
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public ResponseBean updateUser(@RequestBody User user) {
        // 调用service层
        int row = userService.updateUser(user);
        if (row > 0) {
            return new ResponseBean(200, "修改用户信息成功", null);
        } else {
            return new ResponseBean(500, "修改用户信息失败", null);
        }
    }

    /**
     * 获取文件element-ui 取file
     * multipartFile.文件
     * @return
     */
    @RequestMapping("/uploadUserImg")
    @ResponseBody
    public ResponseBean uploadUserImg(@RequestParam("file")MultipartFile multipartFile, User user) {
        //获取文件名
        String originalFilename = multipartFile.getOriginalFilename();
//         控制台打印信息
        log.info("上传头像："+originalFilename);
//        // 随机生成图片文件名
        String uuid = UUID.randomUUID().toString().replace("-", "");
//        // 获取图片拓展名
        String extension = FilenameUtils.getExtension(originalFilename);
//        // 获取新的文件名
        String newFileName = uuid+"."+extension;
//        // 生成目录，放到目录中
        File directory = new File("D:/Study/Lee/Subject/小学期/project/image/user");
        // 判断是否存在目录
        if(!directory.exists()){
            directory.mkdir();
        }
        // 开始存放
        try {
            multipartFile.transferTo(new File(directory,newFileName));
            // 删除原本的照片
            String headImgUrl = user.getHeadImg().replace("http://localhost:8081/image/","D:/Study/Lee/Subject/小学期/project/image/");
            // 删除操作
            File file = new File(headImgUrl);
            file.delete();
            // 打印日志
            log.info("原本存放的图片路径为"+headImgUrl);
            // 新的图片网络路径
            String path="http://localhost:8081/image/user/"+newFileName;
            return new ResponseBean(200,"上传头像成功",path);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseBean(500,"上传头像失败",null);
        }


    }

}
