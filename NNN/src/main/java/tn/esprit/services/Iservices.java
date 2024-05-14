package tn.esprit.services;

import java.util.List;
import java.util.Set;

public interface Iservices<T>{
    int Create(T a);
    List<T> Read();
    int Update(T a);
    int Delete(int id);
    public Set<T> getAll();
    public T getOneByID(int id);

}
