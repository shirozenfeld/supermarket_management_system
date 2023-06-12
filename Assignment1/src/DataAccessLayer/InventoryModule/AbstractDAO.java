package DataAccessLayer.InventoryModule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractDAO {
    protected Connection con;
    public abstract void update(Object obj);
    public abstract void add(Object obj);
    public abstract void remove(int id);
    public abstract ArrayList<Object> getAll();
    public abstract Object getById(int id);
    public abstract void removeAll();
}
