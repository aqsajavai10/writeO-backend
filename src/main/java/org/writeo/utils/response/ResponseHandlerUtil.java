package org.writeo.utils.response;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.writeo.exceps.CustomInvalidFormatException;
import org.writeo.utils.CommonConstants.CommonConstants;

import jakarta.validation.ConstraintViolationException;
import org.writeo.utils.exceps.*;

@Log4j2
public final class ResponseHandlerUtil {
    private ResponseHandlerUtil(){

    }
    public static ResponseEntity<BackendResponse> handleException(Exception e) {
        String errorCode;
        String errorMessage;
        HttpStatus httpStatus;

        if (e instanceof CustomNullPointerException) {
            log.warn(log.isWarnEnabled() ? "#########SYSTEM MALFUNCTION#######\n" +  e.getMessage(): null);
            errorCode = CommonConstants.SYSTEM_MALFUNCTION_CODE;
            errorMessage = "System malfunction: " + e.getMessage();
            httpStatus=HttpStatus.BAD_REQUEST;
        }
        else {
            log.warn(log.isWarnEnabled() ? "#########Exception#######\n" +  e.getMessage(): null);
            errorCode = CommonConstants.SYSTEM_MALFUNCTION_CODE;
            errorMessage = CommonConstants.INTERNAL_SERVER_ERROR_MESSAGE + ": Refer logs.";
            log.info(log.isInfoEnabled() ? "Internal Server Error: "+ e.getStackTrace() : "");
            httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
        }

        BackendResponse response = new BackendResponse(CommonConstants.OPERATION_FAILED, errorMessage, errorCode);

        return handleResponse(response,httpStatus);
    }

    public static ResponseEntity<BackendResponse> handleValidationsException(Exception e) {
        String errorCode;
        String errorMessage;
        HttpStatus httpStatus;
        if (e instanceof CustomInvalidFormatException) {
            log.warn(log.isWarnEnabled() ? "#########INVALID FORMAT#######\n" +  e.getMessage(): null);
            errorCode = CommonConstants.INVALID_FORMAT_CODE;
            errorMessage = CommonConstants.INVALID_FORMAT_MESSAGE + ": " + e.getMessage();
            httpStatus=HttpStatus.OK;
        }else if(e instanceof HttpMessageNotReadableException){
            log.warn(log.isWarnEnabled() ? "#########HttpMessageNotReadableException#######\n" +  e.getMessage(): null);
            errorCode = CommonConstants.INVALID_FORMAT_CODE;
            errorMessage = CommonConstants.INVALID_FORMAT_MESSAGE;
            httpStatus=HttpStatus.OK;
        }
        else if(e instanceof MethodArgumentTypeMismatchException){
            log.warn(log.isWarnEnabled() ? "#########MethodArgumentTypeMismatchException#######\n" +  e.getMessage(): null);
            errorCode = CommonConstants.INVALID_FORMAT_CODE;
            errorMessage = CommonConstants.INVALID_FORMAT_MESSAGE;
            httpStatus=HttpStatus.OK;
        }else if (e instanceof CustomValidationException) {
            log.warn(log.isWarnEnabled() ? "#########INVALID BODY FROM USER#######\n" +  e.getMessage(): null);
            errorCode = CommonConstants.INVALID_REQUEST_CODE;
            errorMessage = CommonConstants.INVALID_REQUEST_MESSAGE + ": " + e.getMessage();
            httpStatus=HttpStatus.UNPROCESSABLE_ENTITY;
        }else if (e instanceof ConstraintViolationException){
            log.warn(log.isWarnEnabled() ? "######### ConstraintViolationException #######\n" +  e.getMessage() : null);
            errorCode = CommonConstants.INVALID_FORMAT_CODE;
            errorMessage = CommonConstants.INVALID_FORMAT_MESSAGE + ": " +     e.getMessage() ;
            httpStatus=HttpStatus.OK;
        }else{
            log.warn(log.isWarnEnabled() ? "#########Error#######\n" +  e.getMessage() : null);
            errorCode = CommonConstants.INVALID_FORMAT_CODE;
            errorMessage = CommonConstants.INVALID_FORMAT_MESSAGE + ": Unidentified Error" ;
            log.info(log.isInfoEnabled() ? "Internal Server Error: "+ e.getCause() : "");
            httpStatus=HttpStatus.OK;
        }
        BackendResponse response = new BackendResponse(CommonConstants.OPERATION_FAILED, errorMessage, errorCode);

        return handleResponse(response,httpStatus);
    }

    public static ResponseEntity<BackendResponse> handleRecordsException(Exception e) {
        String errorCode;
        String errorMessage;
        HttpStatus httpStatus;
        if (e instanceof CustomNotFoundException) {
            log.warn(log.isWarnEnabled() ? "#########NO RECORD FOUND#######\n" +  e.getMessage(): null);
            errorCode = CommonConstants.RECORD_NOT_FOUND_CODE;
            errorMessage = CommonConstants.RECORD_NOT_FOUND_MESSAGE + ": " + e.getMessage();
            httpStatus=HttpStatus.OK;
        } else if (e instanceof CustomNoSuchRecordExistsException) {
            log.warn(log.isWarnEnabled() ? "#########NO RECORD FOUND#######\n" +  e.getMessage(): null);
            errorCode = CommonConstants.RECORD_NOT_FOUND_CODE;
            errorMessage = CommonConstants.RECORD_NOT_FOUND_MESSAGE + ": " + e.getMessage();
            httpStatus=HttpStatus.OK;
        } else if (e instanceof CustomRecordAlreadyExistsException) {
            log.warn(log.isWarnEnabled() ? "#########RECORD ALREADY ADDED#######\n" +  e.getMessage(): null);
            errorCode = CommonConstants.DUPLICATE_RECORD_CODE;
            errorMessage = CommonConstants.DUPLICATE_RECORD_MESSAGE + ": " + e.getMessage();
            httpStatus=HttpStatus.OK;
        } else if(e instanceof DataAccessException){
            log.warn(log.isWarnEnabled() ? "#########DATABASE PROCESSING ERROR#######\n" + e.getCause().getCause().getLocalizedMessage() : null);
            errorCode = CommonConstants.DATABASE_ERROR_CODE;
            errorMessage = CommonConstants.DATA_ACCESS_ERROR + ": " +    e.getCause().getCause().getLocalizedMessage() ;
            httpStatus=HttpStatus.OK;
        } else if(e instanceof HttpRequestMethodNotSupportedException){
            log.warn(log.isWarnEnabled() ? "#########DATABASE PROCESSING ERROR#######\n" +  e.getMessage() : null);
            errorCode = CommonConstants.SYSTEM_MALFUNCTION_CODE;
            errorMessage = CommonConstants.SYSTEM_MALFUNCTION_MESSAGE + ": " +     e.getMessage() ;
            httpStatus=HttpStatus.OK;
        }
        else{
            log.warn(log.isWarnEnabled() ? "#########ERROR#######\n" +  e.getMessage() : null);
            errorCode = CommonConstants.SYSTEM_MALFUNCTION_CODE;
            errorMessage = CommonConstants.SYSTEM_MALFUNCTION_MESSAGE + ": Unidentified Error";
            log.info(log.isInfoEnabled() ? "Internal Server Error: "+ e.getCause() : "");
            httpStatus = HttpStatus.OK;
        }
        BackendResponse response = new BackendResponse(CommonConstants.OPERATION_FAILED, errorMessage, errorCode);

        return handleResponse(response,httpStatus);

    }


    public static ResponseEntity<BackendResponse> handleResponse(Object data,String message,String code,HttpStatus status) {
        BackendResponse response = new BackendResponse(data, message, code);
        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<BackendResponse> handleResponse(BackendResponse response,HttpStatus status) {

        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<BackendResponse> handleSuccess(Object data) {
        return ResponseHandlerUtil.handleResponse(data,CommonConstants.SUCCESSFUL_MESSAGE, CommonConstants.SUCCESSFUL_CODE, HttpStatus.OK);

    }


    public static ResponseEntity<BackendResponse> handleError(String errorMessage, HttpStatus httpStatus) {
        BackendResponse backendResponse = new BackendResponse();
        backendResponse.setResponseCode(CommonConstants.DATA_ACCESS_ERROR);
        backendResponse.setResponseDesc(errorMessage);
        backendResponse.setResponsePayload(null); // Usually, we don't set a payload for errors
        return new ResponseEntity<>(backendResponse, httpStatus);
    }
}












