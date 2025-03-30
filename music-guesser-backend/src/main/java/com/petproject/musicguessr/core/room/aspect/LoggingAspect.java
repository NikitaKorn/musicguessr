package com.petproject.musicguessr.core.room.aspect;

import com.petproject.musicguessr.core.room.handler.AbstractSessionRoomHandler;
import com.petproject.musicguessr.core.room.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around("execution(* com.petproject.musicguessr.core.room.handler.AbstractSessionRoomHandler.onConnectionOpened(..))")
    public void logOnConnectionOpenedMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args[0] instanceof Player player) {
            AbstractSessionRoomHandler sessionRoomHandler = (AbstractSessionRoomHandler) joinPoint.getTarget();
            log.debug("Player {} with session {} trying connect to {}", player.getName(), player.getSession(), sessionRoomHandler.getRoomId());
            joinPoint.proceed();
            log.debug("Player {} with session {} successfully connect to {}", player.getName(), player.getSession(), sessionRoomHandler.getRoomId());
        } else {
            joinPoint.proceed();
        }
    }
}
