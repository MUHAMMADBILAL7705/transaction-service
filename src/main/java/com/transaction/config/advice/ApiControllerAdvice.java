package com.transaction.config.advice;

import com.transaction.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;

@ControllerAdvice
@RestControllerAdvice
public class ApiControllerAdvice implements ResponseBodyAdvice<Object> {

    private final static Logger logger = LoggerFactory.getLogger(ApiControllerAdvice.class);

    @ExceptionHandler({AccountNotFoundException.class})
    public ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException ex, WebRequest request) {
        ApiError error = new ApiError(ex.getMessage(), 0);
        ApiResponseEnvelope envelope = new ApiResponseEnvelope(false);
        envelope.addError(error);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(envelope, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        ApiError error = new ApiError(ex.getMessage(), 0);
        ApiResponseEnvelope envelope = new ApiResponseEnvelope(false);
        envelope.addError(error);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(envelope, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({InSufficientBalanceException.class})
    public ResponseEntity<Object> handleInSufficientBalanceException(InSufficientBalanceException ex, WebRequest request) {
        ApiError error = new ApiError(ex.getMessage(), 0);
        ApiResponseEnvelope envelope = new ApiResponseEnvelope(false);
        envelope.addError(error);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(envelope, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DuplicateAccountException.class})
    public ResponseEntity<Object> handleDuplicateAccountException(DuplicateAccountException ex, WebRequest request) {
        ApiError error = new ApiError(ex.getMessage(), 0);
        ApiResponseEnvelope envelope = new ApiResponseEnvelope(false);
        envelope.addError(error);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(envelope, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DuplicateWalletException.class})
    public ResponseEntity<Object> handleDuplicateWalletException(DuplicateWalletException ex, WebRequest request) {
        ApiError error = new ApiError(ex.getMessage(), 0);
        ApiResponseEnvelope envelope = new ApiResponseEnvelope(false);
        envelope.addError(error);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(envelope, HttpStatus.BAD_REQUEST);
    }


    public boolean supports(MethodParameter mp, Class<? extends HttpMessageConverter<?>> type) {
        return mp.getMethod() == null || mp.getMethod().getDeclaredAnnotation(IgnoreResponseEnvelopeAdvice.class) == null;
    }

    public Object beforeBodyWrite(Object body, MethodParameter mp, MediaType mt, Class<? extends HttpMessageConverter<?>> type, ServerHttpRequest shr, ServerHttpResponse shr1) {
        return body instanceof ApiResponseEnvelope ? this.cleanXSSObjectFields(body) : new ApiResponseEnvelope(true, this.cleanXSSObjectFields(body));
    }

    protected Object cleanXSSObjectFields(Object body) {
        if (body == null) {
            return null;
        } else {
            Field[] fields = body.getClass().getDeclaredFields();
            Field[] var3 = fields;
            int var4 = fields.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Field f = var3[var5];
                f.setAccessible(true);

                try {
                    Object value = f.get(body);
                    if (value != null && value instanceof String && !((String) value).isEmpty()) {
                        Object cleanValue = this.cleanXSS((String) value);
                        f.set(body, cleanValue);
                    }
                } catch (IllegalAccessException var9) {
                    logger.error(var9.getMessage(), var9);
                }
            }

            return body;
        }
    }

    private String cleanXSS(String value) {
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']", "\"\"");
        value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
        value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
        value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
        value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
        return value;
    }
}
