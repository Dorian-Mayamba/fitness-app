package uk.ac.aston.cs3mdd.fitnessapp.generics;

import java.util.List;

public interface IViewModel<T> {

    void updateListFromResultQuery(List<T> elements);

}
