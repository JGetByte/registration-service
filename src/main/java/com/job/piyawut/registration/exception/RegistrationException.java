package com.job.piyawut.registration.exception;

import com.job.piyawut.registration.constant.ErrorCode;
/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
public class RegistrationException extends Exception {

    private final ErrorCode errorCode;
    private final String message;

    public RegistrationException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
