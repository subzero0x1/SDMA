package ru.svalov.ma.data;

public class EmployeeProviderException extends RuntimeException {
    public EmployeeProviderException() {

    }

    public EmployeeProviderException(String message) {
        super(message);
    }

    public EmployeeProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeProviderException(Throwable cause) {
        super(cause);
    }

    public EmployeeProviderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
