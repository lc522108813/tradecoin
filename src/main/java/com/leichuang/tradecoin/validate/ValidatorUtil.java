package com.leichuang.tradecoin.validate;


import com.leichuang.tradecoin.entity.Result;
import com.leichuang.tradecoin.exception.CoinException;
import com.leichuang.tradecoin.exception.ErrorCodes;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValidatorUtil {

    public static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T obj){
        //快速校验，如果有问题，直接抛出异常就好
        Map<String, String> validateResult = null;
        /** 进行校验，获取校验结果 **/
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (set != null && set.size() > 0) {
            validateResult = new HashMap<>();
            String property = null;
            for (ConstraintViolation constraintViolation : set) {
                property = constraintViolation.getPropertyPath().toString();
                throw new CoinException(ErrorCodes.INVALID_PARAM_ERROR.getCode(),property+": "+constraintViolation.getMessage());
            }

        }
        return ;
    }

}
