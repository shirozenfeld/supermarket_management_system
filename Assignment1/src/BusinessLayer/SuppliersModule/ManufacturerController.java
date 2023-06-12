package BusinessLayer.SuppliersModule;
import DataAccessLayer.SuppliersModule.*;
import java.util.List;
import java.util.Map;

public class ManufacturerController
{
        DataAccessLayer.SuppliersModule.ManufacturerDAO manufacturerDAO;
        DataAccessLayer.SuppliersModule.SuperProductDAO superProductDAO;
        private static ManufacturerController instance=null;

        private ManufacturerController()
        {
            this.manufacturerDAO = ManufacturerDAO.getManufacturerInstance();
            this.superProductDAO = SuperProductDAO.getSuperProductInstance();
        }

        public static ManufacturerController getInstance()
        {
            if (instance == null) {
                instance = new ManufacturerController();
            }
            return instance;
        }
        public void AddManufacturer(String name)
        {
            if (!doesManufacturerExist(name)) {
                Manufacturer manufacturer = new Manufacturer(name);
                this.manufacturerDAO.add(manufacturer);
            }
        }
        public boolean doesManufacturerExist(String name)
        {
            if (manufacturerDAO.doesManufacturerExist(name))
                return true;
            return false;

        }

        public Manufacturer getManufacturerByName(String name)
        {
            return manufacturerDAO.getManufacturerByManufacturerName(name);
        }

        public Map<String,SuperProduct> getAllSuperProducts(){
            return superProductDAO.getAllSuperProducts();
        }


        public void removeAllManufacturers()
        {
        manufacturerDAO.removeAllManufacturers();
        }
}
