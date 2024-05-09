package tn.esprit.interfaces;

import tn.esprit.entitiesModel.Livraison;
import tn.esprit.entitiesModel.SuiviLiv;

import java.util.List;
public interface IService<T>{
    //CRUD
    public void add(T t);
    public void Update( T t);
    public void Delete(int id);

    public T getOneByID(int id);

    public SuiviLiv getSuiviLivByLivraisonId(int livraisonId);


}
