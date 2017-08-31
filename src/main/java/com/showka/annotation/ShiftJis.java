package com.showka.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Asserts that the annotated string: encoding Windows-31j or not
 *
 * @author 樋岡真菜美
 */
@Documented
@Constraint(validatedBy = ShiftJisValidator.class)
@Target({ FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
@NotNull
@Size(min = 1)
public @interface ShiftJis {
	String message() default "Text is not Windows-31j.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
