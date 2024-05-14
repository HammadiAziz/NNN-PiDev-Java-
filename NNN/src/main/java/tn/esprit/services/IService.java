package tn.esprit.services;

import tn.esprit.entities.Livraison;
import tn.esprit.entities.SuiviLiv;

import java.util.List;
import java.util.Set;

public interface IService<T> {
    public void ajouter(T t);
    public void modifier(T t);
    public void supprimer(int id);
    public Set<T> getAll();
    public T getOneByID(int id);
    public List<Livraison> getAllLivraisonsByUserId(int userId);
    public SuiviLiv getSuiviLivByLivraisonId(int livraisonId);


}
