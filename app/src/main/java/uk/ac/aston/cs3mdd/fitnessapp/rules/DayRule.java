package uk.ac.aston.cs3mdd.fitnessapp.rules;

import com.mobsandgeeks.saripaar.AnnotationRule;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import uk.ac.aston.cs3mdd.fitnessapp.annotations.ValidDay;

public class DayRule extends AnnotationRule<ValidDay, String> {
    /**
     * Constructor. It is mandatory that all subclasses MUST have a constructor with the same
     * signature.
     *
     * @param validDay The rule {@link Annotation} instance to which
     *                 this rule is paired.
     */
    protected DayRule(ValidDay validDay) {
        super(validDay);
    }

    @Override
    public boolean isValid(String s) {
        String days[] = mRuleAnnotation.days();
        boolean found = false;
        for (String day:days){
            if (day.equals(s)){
                found = true;
            }
        }
        return found;
    }
}
