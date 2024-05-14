package tn.esprit.interfaces;

import java.util.List;

public interface IService <T>{
    //CRUD
    //1
    void add(T t);
    //2
    void update(int id,T t);
    //3

    void delete(int id);

    //4 :All
    List<T> getAll();
    //5 :one
    T getOne(int id);
    //6: by criteria






}
