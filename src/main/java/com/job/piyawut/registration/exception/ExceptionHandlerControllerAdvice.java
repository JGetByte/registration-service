package com.job.piyawut.registration.exception;

import com.job.piyawut.registration.model.ErrorMsg;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler({DataNotFoundException.class})
    public Map<String, Object> handleBadRequest(HttpServletRequest request, DataNotFoundException e) {
        return createError(e.getErrorCode().getCode(), e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({RegistrationException.class})
    public Map<String, Object> handleBadRequest(HttpServletRequest request, RegistrationException e) {
        return createError(e.getErrorCode().getCode(), e.getMessage());
    }

    private Map<String, Object> createError(String code, String message) {
        final ErrorMsg errorMsg = new ErrorMsg(code, message);

        final Map<String, Object> map = new HashMap<>();
        map.put("status", "error");
        map.put("error", errorMsg);
        return map;
    }
}
