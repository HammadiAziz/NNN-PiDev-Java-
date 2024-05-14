package tn.esprit.interfaces;

import java.util.List;

public interface IServiceFatma <T>{

    //crud
    public void add(T t);

    public void update(T t);

    public void delete(T t);
    List<T> getAll();

    T getOne(int id);




}
