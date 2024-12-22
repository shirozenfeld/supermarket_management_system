package BusinessLayer.InventoryModule;

import DataAccessLayer.InventoryModule.CategoryDAO;

import java.util.ArrayList;
import java.util.Formatter;

public class ReportMaker {
    private Branch branch;
    public ReportMaker(Branch branch) {
        this.branch = branch;
    }
    public Formatter generateReportByCategory(int choice, ArrayList<Integer> cats){
        Report report = null;
        switch (choice) {
            case 1:
                report = new InventoryReport();
                break;
            case 2:
                report = new ShortageReport();
                break;
            case 3:
                report = new DamagedExpiredReport();
                break;
            default:
                return null;
        }
        ArrayList<Category> categories = new ArrayList<>();
        Formatter fmt = new Formatter();
        for (int i=0; i<cats.size(); i++){
            int cID = cats.get(i);
            //Category cat = branch.categoryIsInStore(cID);
            Category cat = (Category)CategoryDAO.getInstance().getById(cID);
            categories.add(cat);
            if (cat == null) {
                return fmt.format("");
            }
        }
        return report.createReportByCategory(branch, categories);
    }
    public Formatter generateReport(int choice) {
        Report report = null;
        switch (choice) {
            case 1:
                report = new InventoryReport();
                return report.createReport(branch);
            case 2:
                report = new ShortageReport();
                return report.createReport(branch);
            case 3:
                report = new DamagedExpiredReport();
                return report.createReport(branch);
            default:
                return null;
        }
    }
}
