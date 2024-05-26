package com.nscorp.obis.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
public @interface NullOrNotBlank {
	String message() default "{javax.validation.constraints.NullOrNotBlank.message}";
	
	int max() default Integer.MAX_VALUE;
	
	int min() default 0;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {
	
	int max;
	int min;

	public void initialize(NullOrNotBlank parameters) {
		max=parameters.max();
		min=parameters.min();
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		return (value==null) || ((!value.trim().isEmpty()) && (value.length()>=min && value.length()<=max));
	}
}
