package com.cdk8s.tkey.server.util.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cdk8s.tkey.server.constant.GlobalVariable;

public class R {

    public static ResponseEntity<ResponseErrorObject> failure() {
        return failure(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ResponseErrorObject> failure(HttpStatus httpStatus) {
        ResponseErrorObject responseErrorObject = new ResponseErrorObject();
        responseErrorObject.setError(GlobalVariable.OAUTH_ERROR_MSG);
        responseErrorObject.setErrorDescription(GlobalVariable.OAUTH_ERROR_MSG);
        responseErrorObject.setErrorUriMsg(GlobalVariable.OAUTH_ERROR_URI_MSG);
        return ResponseEntity.status(httpStatus).body(responseErrorObject);
    }

    public static ResponseEntity<ResponseErrorObject> failure(HttpStatus httpStatus, String message) {
        ResponseErrorObject responseErrorObject = new ResponseErrorObject();
        responseErrorObject.setError(GlobalVariable.OAUTH_ERROR_MSG);
        responseErrorObject.setErrorDescription(message);
        responseErrorObject.setErrorUriMsg(GlobalVariable.OAUTH_ERROR_URI_MSG);
        return ResponseEntity.status(httpStatus).body(responseErrorObject);
    }
}