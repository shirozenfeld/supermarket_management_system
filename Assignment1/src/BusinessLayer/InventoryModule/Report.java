package BusinessLayer.InventoryModule;

import java.util.Formatter;
import java.util.List;

public interface Report {
    Formatter createReport(Branch branch);
    Formatter createReportByCategory(Branch branch, List<Category> cList);
}
