package com.hbyd.parks.client.aspect;

import com.google.common.base.Joiner;
import com.hbyd.parks.client.util.ThreadLog;
import com.hbyd.parks.common.log.Module;
import com.hbyd.parks.common.log.Operation;
import com.hbyd.parks.dto.managesys.OperLogDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.ws.managesys.OperLogWS;
import org.aspectj.lang.annotation.Before;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;


@Aspect
@Component
public class LogAspect {
    public static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Resource
    private OperLogWS operLogWS;

    @Before("@annotation(com.hbyd.parks.common.log.Operation)")
    public void beforeOperation(JoinPoint jp) {
//        生成日志DTO
        final OperLogDTO operLogDTO = new OperLogDTO();

//        1. 操作时间
        operLogDTO.setOperTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

//        操作所属模块
        Object target = jp.getTarget();
        String module = target.getClass().getAnnotation(Module.class).description();

//        操作对应方法
        Class[] argClasses = new Class[jp.getArgs().length];
        for (int i = 0; i < jp.getArgs().length; i++){
            argClasses[i] = jp.getArgs()[i].getClass();
        }
        Method method = null;
        try {
            method = target.getClass().getMethod(jp.getSignature().getName(), argClasses);
        } catch (NoSuchMethodException e) {
//            目标方法，能拦到，就能反射到，因此这里理论上不会出现异常，即便出现，记下日志即可
            logger.info(e.getMessage(), e);
            e.printStackTrace();
        }
        String operation = method.getAnnotation(Operation.class).type();

//        2.操作类型
        operLogDTO.setOperType(Joiner.on(":").join(module, operation));

//        3.操作者
        UserDTO u = (UserDTO) ServletActionContext.getRequest().getSession().getAttribute("user");
        operLogDTO.setOperUser(u.getUserName());

//        4.分发日志
//        4.1 调用日志服务存储日志，不能异步处理服务调用，因为 operLog 要将 id 也存储到 ThreadLocal 中
        OperLogDTO saved = operLogWS.save(operLogDTO);
//
//        4.2 将日志DTO 绑定到 ThreadLocal，以便它用，例如：调用通信服务接口，可能需要将日志DTO 作为参数传递
        ThreadLog.set(saved);
    }

    @After("@annotation(com.hbyd.parks.common.log.Operation)")
    public void afterOperation(){
        ThreadLog.remove();//移除当前线程上的日志变量，防止内存溢出
    }
}
