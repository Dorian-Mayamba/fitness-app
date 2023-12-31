package uk.ac.aston.cs3mdd.fitnessapp.annotations;

import com.mobsandgeeks.saripaar.annotation.ValidateUsing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import uk.ac.aston.cs3mdd.fitnessapp.rules.DayRule;

@ValidateUsing(DayRule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidDay {
    public int messageResId() default -1;
    public String message() default "Please enter a valid day";

    public int sequence() default -1;

    public String[] days() default {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
}
