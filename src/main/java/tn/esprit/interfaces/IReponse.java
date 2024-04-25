package tn.esprit.interfaces;

import java.util.List;

public interface IReponse<T> {

	public void add_Rep(T t);

	public void update_Rep(T t, int id);

	public void delete_Rep(int id);

	public List<T> getAll_Rep();

	public T getOne_Rep(int id);

}
