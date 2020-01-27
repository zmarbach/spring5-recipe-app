package guru.springframework.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j

//VERY USEFUL!!!
// This tells Spring to use these methods for ALL controllers
// We use id value as URL variable in many controllers so don't have to write this method in all of them... just ONCE!
//Example - if there is a NumberFormatException for ANY controller mapping, they will all handle it the same way
@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(Exception e){
        log.error("handling number format exception");
        log.error(e.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", e);
        return modelAndView;
    }
}
