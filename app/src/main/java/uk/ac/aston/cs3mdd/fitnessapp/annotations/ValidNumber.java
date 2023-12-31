package uk.ac.aston.cs3mdd.fitnessapp.annotations;


import com.mobsandgeeks.saripaar.annotation.ValidateUsing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import uk.ac.aston.cs3mdd.fitnessapp.rules.NumberRule;

@ValidateUsing(NumberRule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidNumber {
    public int messageResId() default -1;
    public String message() default "Please enter a valid number";

    public int sequence() default -1;
}
