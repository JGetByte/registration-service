package com.job.piyawut.registration.constant;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
public enum ErrorCode {
    INVALID_USER("0001"),
    DUPLICATE_USERNAME("0002"),
    SALARY_CONDITION("0003"),
    
    DATA_NOT_FOUND("1001"),
    
    VALIDATION_ERROR("2001");

    private final String code;

    private ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return "Err-" + code;
    }
}
