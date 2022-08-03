package com.wukong.handler;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 错误页面拦截类
 * @ControllerAdvice 会拦截到所有标注 @Controller的类
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 异常处理
     * @ExceptionHandler(Exception.class) 异常处理注解，里面的参数是拦截Exception级别的错误信息
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {

        logger.error("Request URL: {}, Exception: {}",request.getRequestURL(),e);

        //如果异常的状态被改变 则抛出 exception
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        //添加异常信息
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        //设置返回页面
        mv.setViewName("error/error");
        return  mv;
    }
}
