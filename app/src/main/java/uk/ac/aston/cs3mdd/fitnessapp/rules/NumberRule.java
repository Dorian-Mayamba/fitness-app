package uk.ac.aston.cs3mdd.fitnessapp.rules;

import com.mobsandgeeks.saripaar.AnnotationRule;

import java.lang.annotation.Annotation;

import uk.ac.aston.cs3mdd.fitnessapp.annotations.ValidNumber;

public class NumberRule extends AnnotationRule<ValidNumber,String> {

    /**
     * Constructor. It is mandatory that all subclasses MUST have a constructor with the same
     * signature.
     *
     * @param number The rule {@link Annotation} instance to which
     *               this rule is paired.
     */
    protected NumberRule(ValidNumber number) {
        super(number);
    }

    @Override
    public boolean isValid(String s) {
        try{
            int number = Integer.parseInt(s);
            return true;
        }catch (NumberFormatException ex){
            return false;
        }
    }
}
