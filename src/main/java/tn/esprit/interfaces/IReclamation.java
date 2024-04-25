package tn.esprit.interfaces;
import java.util.List;

public interface IReclamation<T>{

    //CRUD
    public void add(T t);

    public void update(T t, int id);

    public void delete(int id);

    List<T> getAll();

    T getOne(int id);

}
