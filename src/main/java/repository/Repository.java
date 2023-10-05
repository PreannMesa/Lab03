package repository;

import java.util.List;

public interface Repository<T> {
    public boolean add(T o);
    public T get(String id);
    public List<T> getAll();
    public boolean remove(String id);
    public boolean remove(T o);
    public boolean update(T o);
}
