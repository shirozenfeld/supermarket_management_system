package BusinessLayer.InventoryModule;

import DataAccessLayer.InventoryModule.BasicItemDAO;

import java.util.Formatter;
import java.util.List;

public class ShortageReport implements Report {
    @Override
    public Formatter createReport(Branch branch) {
       Formatter fmt = new Formatter();
       fmt.format("%15s %25s %15s %15s %15s\n", "Catalog Number", "Description","Manufacturer", "Total Amount", "Minimum Amount");
       branch.getShortageItems().forEach((itemCN, invi) -> {
           BasicItem bi = (BasicItem) BasicItemDAO.getInstance().getById(itemCN);
           String manuName = bi.getManufacturer();
           String name = bi.getName();
           fmt.format("%15s %25s %15s %15s %15s\n", itemCN, name, manuName, invi.getTotalAmount(), invi.getMinimumAmount());
       });
        return fmt;
    }
    @Override
    public Formatter createReportByCategory(Branch branch, List<Category> cList) {
        Formatter fmt = new Formatter();
        fmt.format("%15s %15s %25s %15s %15s %15s\n","Category's Name", "Catalog Number", "Description","Manufacturer", "Total Amount", "Minimum Amount");
        for (int i = 0; i < cList.size(); i++) {
            Category now = cList.get(i);
            for (int j = 0; j < now.getcItems().size(); j++) {
                InventoryItem invi = now.getcItems().get(j);
                BasicItem bi = (BasicItem) BasicItemDAO.getInstance().getById(invi.getCatalogNum());
                String manuName = bi.getManufacturer();
                String name = bi.getName();
                if (invi.getTotalAmount() <= invi.getMinimumAmount())
                    fmt.format("%15s %15s %25s %15s %15s %15s\n", now.getcName(), invi.getCatalogNum(), name, manuName, invi.getTotalAmount(), invi.getMinimumAmount());
            }
        }
        return fmt;
    }
}