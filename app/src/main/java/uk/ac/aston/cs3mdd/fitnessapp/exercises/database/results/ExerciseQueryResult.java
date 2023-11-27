package uk.ac.aston.cs3mdd.fitnessapp.exercises.database.results;

import java.util.List;

public class ExerciseQueryResult<T> {
    public ExerciseQueryResult(){}

    public static class Success<T>{

        private T content;

        public Success(T body){
            this.content = body;
        }
        public T resultBody() {
            return content;
        }
    }

    public static class Error<T>{

        private Exception exception;
        public Error(Exception e){
            this.exception = e;
        }

        public String getErrorMessage(){
            return exception.getMessage();
        }
    }
}
