package pl.lodz.p.pas.repository;

import java.util.List;
import java.util.Optional;

public abstract class Repo<T> {

    public abstract long add(T item);

    public abstract void remove(T item);

    public abstract Optional<T> findByID(Long id);

    public abstract void update(Long id, T item);

    public abstract List<T> getItems();

}
