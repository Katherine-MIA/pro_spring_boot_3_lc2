package com.apress.my.retro.advice;

import com.apress.my.retro.board.RetroBoard;
import com.apress.my.retro.exception.RetroBoardNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j // creates an instance of a logger; "In this case is just logging the name of the method
// (by calling the proceedingJoinPoint.getSignature().getName() method) being intercepted."
@Component // marks class as spring bean(component) to allow usage/injection when/where needed.
@Aspect // marks a class as "Aspect" that contains an Advice(ex: Before, After, Around, AfterReturn, AfterThrowing)
// "  that will be intercepting a particular method depending on the matcher declaration.
// Behind the scenes, Spring creates proxies for these classes and applies everything related to AOP."
public class RetroBoardAdvice {
    @Around("execution(* com.apress.myretro.persistence.RetroBoardRepository.findById(java.util.UUID))")
    /* @Around -> Intercepts the execution of the method specified in the "execution()" declaration.
    According to the advice used (@Around in this case) it will execute the annotated method logic
    alongside the call to the "watched/listened to" method.
    * "In this case, it will intercept the call (based on the execution declaration that matches
    the method) before it gets executed, create an instance of the ProceedingJoinPoint,
    and execute the method you marked with this annotation and execute your logic. Then,
    you can execute the actual call, do some more logic, and return the result."
    * @Before -> "execute your method logic before the actual call happens"
    * @After -> "execute your method logic after the call"
    * @AfterReturn -> executes the method logic after the method call we expect returns something
    to the caller but that result should also be used somewhere else
    * execution() -> " This keyword needs a pattern matching that identifies the method to be advised.
     In this case, we are looking for every method with any return type (*) and looking for that
     specific method in the com.apress.myretro.repository.RetroBoardRepository.findById that has
     as a parameter the UUID. In this case, this is very straightforward, but we can have an
     expression like this: * com.apress.*..*.find*(*). This means finding any class that is
     between the package apress and up and any class that has the find prefix for the method that
     accepts any number of parameters, no matter the type."
    * ProceedingJoinPoint -> "This is an interface, and its implementation knows how to get the
    object that is being advised (RetroBoardRepository.findById); it has the actual object, and we
    can call the proceed() that will execute it, and we can get the result and return it. See that
    we can manipulate the result or even the parameters that we are sending. Only the @Around Advice
    must have this ProceedingJoinPoint." */
    public Object checkFindRetroBoard(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        log.info("[ADVICE] findRetroBoardById");
        Optional<RetroBoard> retroBoard = (Optional<RetroBoard>) proceedingJoinPoint
                .proceed(new Object[]{
                        UUID.fromString(proceedingJoinPoint.getArgs()[0].toString())
                });
        //So the actual call to findById(UUID) will happen here on proceed and the result will be
        //caught here. If it is empty then it throws the custom exception for controller advice
        // to handle, else it returns the object to the initial caller.
        if(retroBoard.isEmpty())
            throw new RetroBoardNotFoundException();
        return retroBoard;
    }
}
/*
* "With this Advice we are isolating our concern about a check, avoiding any repetition
of code (tangling and scattering), and making our code more understandable and
cleaner."
* Or so the book claims, however I believe the necessity of this AOP handled call is simply
* to integrate AOP theory into the book as checks for null can be done at any level and can throw
* the custom exception from anywhere; the ControllerAdvice will catch it.
* "This is just a small example of the power of the AOP paradigm. You can use your
own custom annotation and advice on methods. For example, you can create a @Cache
annotation and use the Around Advice for every method that uses that annotation."
* */