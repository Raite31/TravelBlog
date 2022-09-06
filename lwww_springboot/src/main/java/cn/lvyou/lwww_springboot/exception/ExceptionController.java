package cn.lvyou.lwww_springboot.exception;

import cn.lvyou.lwww_springboot.entiy.ResponseBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public ResponseBean myException(Exception e){
        return new ResponseBean(401,e.getMessage(),null);
    }
}
