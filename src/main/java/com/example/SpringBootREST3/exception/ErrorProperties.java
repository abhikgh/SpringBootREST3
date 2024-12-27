package com.example.SpringBootREST3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class ErrorProperties {

    private final Map<ErrorCode, ErrorCodeProperties> errorPropertiesMap;

    public ErrorProperties(ErrorMessageProperties errorMessageProperties,
                           ErrorInfoMessageProperties errorInfoMessageProperties) {

        errorPropertiesMap = new EnumMap<>(ErrorCode.class);

        errorPropertiesMap.put(ErrorCode.INVALID_ITEM,
                new ErrorCodeProperties(Group.CONFIGURATION_ERROR,
                        Type.CART_VALIDATION_ERROR,
                        errorMessageProperties.getValidation().getItemMismatch(),
                        errorInfoMessageProperties.getValidation().getItemMismatch(),
                        HttpStatus.BAD_REQUEST));

        errorPropertiesMap.put(ErrorCode.INVALID_ITEM_TYPE,
                new ErrorCodeProperties(Group.CONFIGURATION_ERROR,
                        Type.CART_VALIDATION_ERROR,
                        errorMessageProperties.getValidation().getItemTypeMismatch(),
                        errorInfoMessageProperties.getValidation().getItemTypeMismatch(),
                        HttpStatus.BAD_REQUEST));

        errorPropertiesMap.put(ErrorCode.INVALID_ITEM_ITEM_TYPE_MISMATCH,
                new ErrorCodeProperties(Group.CONFIGURATION_ERROR,
                        Type.CART_VALIDATION_ERROR,
                        errorMessageProperties.getDataConnection().getItemAndItemTypeMismatch(),
                        errorInfoMessageProperties.getDataConnection().getItemAndItemTypeMismatch(),
                        HttpStatus.BAD_REQUEST));
    }

    public enum ErrorCode {
        INVALID_ITEM,
        INVALID_ITEM_TYPE,
        INVALID_ITEM_ITEM_TYPE_MISMATCH
    }

    public enum Group {
        CONFIGURATION_ERROR
    }

    public enum Type {
        CART_VALIDATION_ERROR
    }

    public void throwSPEException(ErrorProperties.ErrorCode errorCode, List<String> errorInfo) {
        ErrorCodeProperties errorCodeProperties = errorPropertiesMap.get(errorCode);
        throw new SPEException(errorCodeProperties.getGroup(),
                            errorCodeProperties.getType(),
                            errorCodeProperties.getMessage(),
                            CollectionUtils.isEmpty(errorInfo)? errorCodeProperties.getInfoMessage(): String.format(errorCodeProperties.getInfoMessage(), errorInfo),
                            errorCodeProperties.getHttpStatus());
    }

}
