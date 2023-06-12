package BusinessLayer.InventoryModule;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Formatter;
import java.util.List;

public class DamagedExpiredReport implements Report {
    @Override
    public Formatter createReport(Branch branch) {
        Formatter fmt = new Formatter();
        fmt.format("%15s %25s %15s %15s %15s %25s %15s\n", "Catalog Number", "Description","Manufacturer", "ID", "Product Integrity", "Damage Type", "Expiry Date");
        branch.getDamagedItems().forEach((itemCN, item) -> {
            fmt.format("%15s %25s %15s %15s %15s %25s %15s\n", item.getCatalogNum(), item.getName(),item.getManufacturer(), item.getID(), item.getDamage(), item.getDamageType(), item.getExpireDate());
        });
        branch.getStoreInventory().forEach((itemCN, item) -> {
            if(item.getDamage() == ProductIntegrity.Expired){
                fmt.format("%15s %25s %15s %15s %15s %25s %15s\n", item.getCatalogNum(), item.getName(),item.getManufacturer(), item.getID(), item.getDamage(), item.getDamageType(), item.getExpireDate());
            }
            if(item.getDamage() == ProductIntegrity.Null && (LocalDate.now().until(item.getExpireDate(), ChronoUnit.DAYS) <= 7 )){
                fmt.format("%15s %25s %15s %15s %15s %25s %15s\n", item.getCatalogNum(), item.getName(),item.getManufacturer(), item.getID(), item.getDamage(), item.getDamageType(), item.getExpireDate());
            }
        });
        branch.getWarehouseInventory().forEach((itemCN, item) -> {
            if(item.getDamage() == ProductIntegrity.Expired){
                fmt.format("%15s %25s %15s %15s %15s %25s %15s\n", item.getCatalogNum(), item.getName(),item.getManufacturer(), item.getID(), item.getDamage(), item.getDamageType(), item.getExpireDate());
            }
            if(item.getDamage() == ProductIntegrity.Null && item.getExpireDate()!= null && (LocalDate.now().until(item.getExpireDate(), ChronoUnit.DAYS) <= 7 )){
                fmt.format("%15s %25s %15s %15s %15s %25s %15s\n", item.getCatalogNum(), item.getName(),item.getManufacturer(), item.getID(), item.getDamage(), item.getDamageType(), item.getExpireDate());
            }
        });
        return fmt;
    }
    @Override
    public Formatter createReportByCategory(Branch branch, List<Category> cList){
        Formatter fmt = new Formatter();
        fmt.format("%15s %15s %25s %15s %15s %15s %25s %15s\n", "Category's Name", "Catalog Number", "Description","Manufacturer", "ID", "Product Integrity", "Damage Type", "Expiry Date");
        for (int i = 0; i < cList.size(); i++) {
            Category now = cList.get(i);
            branch.getStoreInventory().forEach((itemCN, item) -> {
                if(item.getDamage() == ProductIntegrity.Expired && item.getItemCategories().contains(now)){
                    fmt.format("%15s %15s %25s %15s %15s %15s %25s %15s\n",now.getcName(), item.getCatalogNum(), item.getName(),item.getManufacturer(), item.getID(), item.getDamage(), item.getDamageType(), item.getExpireDate());
                }
                if(item.getDamage() == ProductIntegrity.Damaged && item.getItemCategories().contains(now)){
                    fmt.format("%%15s %15s %25s %15s %15s %15s %25s %15s\n",now.getcName(), item.getCatalogNum(), item.getName(),item.getManufacturer(), item.getID(), item.getDamage(), item.getDamageType(), item.getExpireDate());
                }
                if(item.getDamage() == ProductIntegrity.Null && (LocalDate.now().until(item.getExpireDate(), ChronoUnit.DAYS) <= 7 ) && item.getItemCategories().contains(now)){
                    fmt.format("%15s %15s %25s %15s %15s %15s %25s %15s\n",now.getcName(), item.getCatalogNum(), item.getName(),item.getManufacturer(), item.getID(), item.getDamage(), item.getDamageType(), item.getExpireDate());
                }
            });
            branch.getWarehouseInventory().forEach((itemCN, item) -> {
                if(item.getDamage() == ProductIntegrity.Expired && item.getItemCategories().contains(now)){
                    fmt.format("%15s %15s %25s %15s %15s %15s %25s %15s\n",now.getcName(), itemCN, item.getName(),item.getManufacturer(), item.getID(), item.getDamage(), item.getDamageType(), item.getExpireDate());
                }
                if(item.getDamage() == ProductIntegrity.Damaged && item.getItemCategories().contains(now)){
                    fmt.format("%15s %15s %25s %15s %15s %15s %25s %15s\n",now.getcName(), itemCN, item.getName(),item.getManufacturer(), item.getID(), item.getDamage(), item.getDamageType(), item.getExpireDate());
                }
                if(item.getDamage() == ProductIntegrity.Null && (LocalDate.now().until(item.getExpireDate(), ChronoUnit.DAYS) <= 7 ) && item.getItemCategories().contains(now)){
                    fmt.format("%15s %15s %25s %15s %15s %15s %25s %15s\n",now.getcName(), itemCN, item.getName(),item.getManufacturer(), item.getID(), item.getDamage(), item.getDamageType(), item.getExpireDate());
                }
            });
        }
        return fmt;
    }
}
