package scu.zpf.seckill.validator;

import org.apache.commons.lang3.StringUtils;
import scu.zpf.seckill.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsPhoneValidator implements ConstraintValidator<IsPhone, String> {

    private boolean required = false;

    @Override
    public void initialize(IsPhone constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidatorUtil.isPhone(s);
        }else {
            if (StringUtils.isEmpty(s)) {
                return true;
            }else {
                return ValidatorUtil.isPhone(s);
            }
        }
    }
}
