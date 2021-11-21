package info.somrat.rest.exception;

import info.somrat.rest.response.ApiResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@RestController
public class ExceptionErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ApiResponse onConstraintValidationException(ConstraintViolationException exception) {
            return new ApiResponse(false, "Something goes wrong!");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ApiResponse> entityNotFoundException(EntityNotFoundException exception) {
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
