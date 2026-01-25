package com.exp.backend.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * className - ModelAOP
 * This is an aspect class.
 * It will help in debugging through logging,security and transactional events.
 * it has advices which decides when to log event.
 * Join Points are the methods are executing for which we are applying advice.
 * Point Cuts are the expressions which indicate which class methods would under come this proxy object which is wrapped the actual object.
 */
@Aspect
@Component
public class ModelAOP {
    private static final Logger logger = LoggerFactory.getLogger(ModelAOP.class);

    /**
     * MethodName - entryRegister
     * @param result indication of returning response from the controller. It is a HashMap response sending to client.
     */
    @AfterReturning(
            pointcut = "execution(* com.exp.backend.service.UserRegistrationService.*(..))",
            returning = "result"
    )
    protected void entryRegister(Object result) {
        logger.info("Method returning response");
    }

    /**
     * methodName - checker
     * Here we don't know the actual Exception class.so, we are catching using Throwable class.
     *
     *
     * @param pjp ProceedingJoinPoint helps to control the Join Points
     * @param messageInterface It is an interface,which helps to get the message from the Joint Point to know which class we are currently in.
     * @return Object It returns the ProceedingJoinPoint object which is caught after calling the actual object
     * @throws Throwable It is the parent class of Runtime and Error Exception class.
     */

    @Around("@annotation(messageInterface)")
    protected Object checker(ProceedingJoinPoint pjp,messageInterface messageInterface) throws Throwable{
        logger.info(messageInterface.value());
        Object res = pjp.proceed();
        logger.info("controller exit");
        return res;
    }
}
