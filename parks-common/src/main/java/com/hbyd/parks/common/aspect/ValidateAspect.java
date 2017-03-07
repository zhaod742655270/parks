package com.hbyd.parks.common.aspect;

import com.hbyd.parks.common.util.ValHelper;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import java.util.Arrays;
import java.util.List;

/**
 * Created by allbutone on 14-8-8.
 */
public class ValidateAspect {

    static Logger logger = Logger.getLogger(ValidateAspect.class);

    public void doValidate(JoinPoint jp) throws Throwable {
//        Returns the signature at the join point. getStaticPart().getSignature() returns the same object
        Signature signature = jp.getSignature();
//        Returns a String representing the kind of join point. This String is guaranteed to be interned. getStaticPart().getKind() returns the same object.
        String kind = jp.getKind();
        logger.debug("kind = " + kind);
//        kind = method-execution

//        Returns the target object. This will always be the same object as that matched by the target pointcut designator.
        Object target = jp.getTarget();
        logger.debug("target.getClass().getSimpleName() = " + target.getClass().getSimpleName());
//        target.getClass().getSimpleName() = HolidayWSImpl

//        Returns the currently executing object. This will always be the same object as that matched by the this pointcut designator.
        Object aThis = jp.getThis();
        logger.debug("aThis.getClass().getSimpleName() = " + aThis.getClass().getSimpleName());
//        aThis.getClass().getSimpleName() = $Proxy27

//      查看方法签名提供的信息
        String methodName = signature.getName();
        logger.debug("methodName = " + methodName);
//        methodName = delByID

        String signatureLongString = signature.toLongString();
        logger.debug("signatureLongString = " + signatureLongString);
//        signatureLongString = public abstract void BaseWS.delByID(java.lang.String)

        String signatureShortString = signature.toShortString();
        logger.debug("signatureShortString = " + signatureShortString);
//        signatureShortString = BaseWS.delByID(..)

        Class declaringType = signature.getDeclaringType();
        logger.debug("declaringType.getSimpleName() = " + declaringType.getSimpleName());
//        declaringType.getSimpleName() = BaseWS

        String declaringTypeName = signature.getDeclaringTypeName();
        logger.debug("declaringTypeName = " + declaringTypeName);
//        declaringTypeName = BaseWS

        String modifiers = java.lang.reflect.Modifier.toString(signature.getModifiers());
        logger.debug("modifiers = " + modifiers);
//        modifiers = public abstract

//      方法参数 NOT NULL 验证
        Object[] args = jp.getArgs();//无参方法参数个数为 0

        List<String> whiteList = Arrays.asList("updateDayShiftForEmp", "getPage.*");

        if(!includedInWhiteList(whiteList, methodName)){//如未在白名单，需要验证参数
//            validateArgs(args);//取消参数验证，因为一个方法可能将自身的参数传递给另一个方法，这样，即使放行 caller, callee 的参数验证也可能不通过
        }
    }

    /**
     * 方法名称是否在白名单内
     *
     * @param whiteList  白名单（放行名单）, 元素为正则表达式
     * @param methodName 方法名称
     * @return 如果在白名单内，返回true, 否则 false
     */
    private boolean includedInWhiteList(List<String> whiteList, String methodName) {
        if(whiteList != null && whiteList.size()>0){
            for (String item : whiteList) {
                if(methodName.matches(item)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *验证方法参数
     * @param args 参数数组
     */
    public void validateArgs(Object[] args) {
        for (int i = 0; i < args.length; i++) {
            try {
                Object arg = args[i];
                if(arg instanceof Object[]){
                    continue;//数组类型的参数可以为 NULL
                }
                ValHelper.notNull(arg, i);
            } catch (IllegalArgumentException e) {
               throw e;
            }
        }
    }
}
