package repository;

import java.util.List;
import java.util.Optional;

import model.Deck;

public interface Repository<T,ID> {
	T save(T obj);
	Optional<T> findById(ID id);
	List<T> findAll();
	void deleteById(ID id);
}
