package tn.esprit.interfaces;

import java.util.List;

public interface IReponse<T> {

	public void add_Rep(T t);

	public void update_Rep(T t);

	public void delete_Rep(int id);

	public List<T> getAll_Rep(int id);

	public T getOne_Rep(int id);

}
