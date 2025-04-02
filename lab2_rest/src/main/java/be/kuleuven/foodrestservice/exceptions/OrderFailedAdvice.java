package be.kuleuven.foodrestservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class OrderFailedAdvice {

    @ResponseBody
    @ExceptionHandler(OrderFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String orderFailedHandler(OrderFailedException ex) {
        return ex.getMessage();
    }
}
