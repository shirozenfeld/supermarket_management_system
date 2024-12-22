package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.*;
import DataAccessLayer.InventoryModule.BasicItemDAO;
import DataAccessLayer.InventoryModule.CategoryDAO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Formatter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import static java.lang.Integer.parseInt;

public class GenerateReport extends JFrame {
    private Branch branch;
    private InventoryManagment imanage;
    ReportMaker reportMaker;
    private JPanel contentPane;

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    public GenerateReport() {
        //set contentPane
        imanage = InventoryManagment.getInstance();
        setTitle("Generate Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(0, 0, 1200, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //set main label
        JLabel storeManagerMenu = new JLabel("Generate Report");
        storeManagerMenu.setHorizontalAlignment(SwingConstants.CENTER);
        storeManagerMenu.setFont(new Font("Tahoma", Font.BOLD, 24));
        storeManagerMenu.setBounds(200, 0 , 613, 59);
        contentPane.add(storeManagerMenu);
        //set categories checkBox
        JCheckBox checkBox = new JCheckBox();
        checkBox.setText("Would you like to filter by Categories?");
        checkBox.setFocusable(false);
        checkBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
        checkBox.setBackground(SystemColor.activeCaption);
        checkBox.setBounds(400, 100, 350, 33);
        contentPane.add(checkBox);
        //set inventory categories textField
        JTextField inviCatText = new JTextField();
        inviCatText.setPreferredSize(new Dimension(250,40));
        inviCatText.setBounds(400, 150, 130, 30);
        inviCatText.setEnabled(false);
        contentPane.add(inviCatText);
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inviCatText.setEnabled(checkBox.isSelected());
            }
        });
        //set inventory button
        JButton inviReportButton = new JButton("Inventory Report");
        inviReportButton.setBounds(600, 150, 200,30);
        final Formatter[] fmt = new Formatter[1];
        inviReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                branch = imanage.getBranch();
                reportMaker = new ReportMaker(branch);
                String[] column;
                String[][] data;
                if(!checkBox.isSelected()){
                    column = new String[]{"Catalog Number", "Description", "Manufacturer", "Amount In Store", "Amount In Warehouse", "Total Amount", "Counted Amount"};
                    data = new String[branch.getInventoryItems().size()][7];
                    AtomicInteger i= new AtomicInteger();
                    branch.getInventoryItems().forEach((itemCN, invi) -> {
                        BasicItem bi = (BasicItem) BasicItemDAO.getInstance().getById(itemCN);
                        String manuName = bi.getManufacturer();
                        String name = bi.getName();
                        data[i.get()][0] = String.valueOf(itemCN);
                        data[i.get()][1]= name;
                        data[i.get()][2]= manuName;
                        data[i.get()][3] = String.valueOf(invi.getAmountInStore());
                        data[i.get()][4] = String.valueOf(invi.getAmountInWareHouse());
                        data[i.get()][5] = String.valueOf(invi.getTotalAmount());
                        data[i.get()][6] = String.valueOf(invi.getCountedAmount());
                        i.getAndIncrement();
                    });
                }
                else{
                    column = new String[]{"Category's Name", "Catalog Number", "Description", "Manufacturer", "Amount In Store", "Amount In Warehouse", "Total Amount", "Counted Amount"};
                    data = new String[branch.getInventoryItems().size()][8];
                    AtomicInteger h= new AtomicInteger();
                    ArrayList<Category> categories = new ArrayList<>();
                    String inputText = inviCatText.getText();
                    String[] splittedText = inputText.split(",");
                    for (int i=0; i<splittedText.length; i++) {
                        int cID = Integer.parseInt(splittedText[i]);
                        Category cat = (Category) CategoryDAO.getInstance().getById(cID);
                        categories.add(cat);
                    }
                    for (int i = 0; i < categories.size(); i++) {
                        Category now = categories.get(i);
                        for (int j = 0; j < now.getcItems().size(); j++) {
                            InventoryItem invi = now.getcItems().get(j);
                            BasicItem bi = (BasicItem) BasicItemDAO.getInstance().getById(invi.getCatalogNum());
                            String manuName = bi.getManufacturer();
                            String name = bi.getName();
                            data[h.get()][0] =  now.getcName();
                            data[h.get()][1] = String.valueOf(invi.getCatalogNum());
                            data[h.get()][2] = name;
                            data[h.get()][3] = manuName;
                            data[h.get()][4] = String.valueOf(invi.getAmountInStore());
                            data[h.get()][5] = String.valueOf(invi.getAmountInWareHouse());
                            data[h.get()][6] = String.valueOf(invi.getTotalAmount());
                            data[h.get()][7] = String.valueOf(invi.getCountedAmount());
                            h.getAndIncrement();
                        }
                    }
                }
                if(data.length != 0) {
                    JTable table = new JTable(data, column);
                    table.setBackground(SystemColor.activeCaption);
                    table.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                    JScrollPane sp = new JScrollPane(table);
                    sp.setBounds(0, 350, 1300, 150);
                    sp.getViewport().setBackground(SystemColor.activeCaption);
                    sp.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    JTableHeader header = table.getTableHeader();
                    header.setBackground(SystemColor.activeCaption);
                    header.setFont(new Font("Tahoma", Font.BOLD, 14));
                    header.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
                    header.setResizingAllowed(false);
                    header.setReorderingAllowed(false);
                    table.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
                    table.setGridColor(SystemColor.activeCaption);
                    table.setEnabled(false);
                    Border lineBorder = BorderFactory.createLineBorder(SystemColor.activeCaption, 2);
                    sp.setBorder(lineBorder);
                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                    table.setDefaultRenderer(Object.class, centerRenderer);
                    //contentPane.add(sp);
                    JFrame newWindow = new JFrame("Inventory Report");
                    Container newContentPane = newWindow.getContentPane();
                    newWindow.setVisible(true);
                    newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    newWindow.setBounds(0, 0, 1400, 300);
                    newWindow.setBackground(SystemColor.activeCaption);
                    newWindow.setForeground(SystemColor.activeCaption);
                    newContentPane.add(sp);
                }
                else
                    JOptionPane.showMessageDialog(getComponent(0), "No Inventory Report to Show", "Notice", JOptionPane.PLAIN_MESSAGE);
            }
        });
        contentPane.add(inviReportButton);
        //set shortage categories textField
        JTextField shortageCatText = new JTextField();
        shortageCatText.setPreferredSize(new Dimension(250,40));
        shortageCatText.setBounds(400, 200, 130, 30);
        shortageCatText.setEnabled(false);
        contentPane.add(shortageCatText);
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shortageCatText.setEnabled(checkBox.isSelected());
            }
        });
        //set shortage button
        JButton shortageReportButton = new JButton("Shortage Report");
        shortageReportButton.setBounds(600, 200, 200,30);
        shortageReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                branch = imanage.getBranch();
                reportMaker = new ReportMaker(branch);
                String[] column;
                String[][] data;
                if(!checkBox.isSelected()){
                    column = new String[]{"Catalog Number", "Description","Manufacturer", "Total Amount", "Minimum Amount"};
                    data = new String[branch.getInventoryItems().size()][5];
                    AtomicInteger i= new AtomicInteger();
                    branch.getShortageItems().forEach((itemCN, invi) -> {
                        BasicItem bi = (BasicItem) BasicItemDAO.getInstance().getById(itemCN);
                        String manuName = bi.getManufacturer();
                        String name = bi.getName();
                        data[i.get()][0] = String.valueOf(itemCN);
                        data[i.get()][1]= name;
                        data[i.get()][2]= manuName;
                        data[i.get()][3] = String.valueOf(invi.getTotalAmount());
                        data[i.get()][4] = String.valueOf(invi.getMinimumAmount());
                        i.getAndIncrement();
                    });
                }
                else{
                    column = new String[]{"Category's Name", "Catalog Number", "Description","Manufacturer", "Total Amount", "Minimum Amount"};
                    data = new String[branch.getInventoryItems().size()][6];
                    AtomicInteger h= new AtomicInteger();
                    ArrayList<Category> categories = new ArrayList<>();
                    String inputText = shortageCatText.getText();
                    String[] splittedText = inputText.split(",");
                    for (int i=0; i<splittedText.length; i++) {
                        int cID = Integer.parseInt(splittedText[i]);
                        Category cat = (Category) CategoryDAO.getInstance().getById(cID);
                        categories.add(cat);
                    }
                    for (int i = 0; i < categories.size(); i++) {
                        Category now = categories.get(i);
                        for (int j = 0; j < now.getcItems().size(); j++) {
                            InventoryItem invi = now.getcItems().get(j);
                            BasicItem bi = (BasicItem) BasicItemDAO.getInstance().getById(invi.getCatalogNum());
                            String manuName = bi.getManufacturer();
                            String name = bi.getName();
                            if (invi.getTotalAmount() <= invi.getMinimumAmount()) {
                                data[h.get()][0] = now.getcName();
                                data[h.get()][1] = String.valueOf(invi.getCatalogNum());
                                data[h.get()][2] = name;
                                data[h.get()][3] = manuName;
                                data[h.get()][4] = String.valueOf(invi.getTotalAmount());
                                data[h.get()][5] = String.valueOf(invi.getMinimumAmount());
                                h.getAndIncrement();
                            }
                        }
                    }
                }
                if(data.length != 0) {
                    JTable table = new JTable(data, column);
                    table.setBackground(SystemColor.activeCaption);
                    table.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                    JScrollPane sp = new JScrollPane(table);
                    sp.setBounds(0, 350, 1300, 150);
                    sp.getViewport().setBackground(SystemColor.activeCaption);
                    sp.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    JTableHeader header = table.getTableHeader();
                    header.setBackground(SystemColor.activeCaption);
                    header.setFont(new Font("Tahoma", Font.BOLD, 14));
                    header.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
                    header.setResizingAllowed(false);
                    header.setReorderingAllowed(false);
                    table.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
                    table.setGridColor(SystemColor.activeCaption);
                    table.setEnabled(false);
                    Border lineBorder = BorderFactory.createLineBorder(SystemColor.activeCaption, 2);
                    sp.setBorder(lineBorder);
                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                    table.setDefaultRenderer(Object.class, centerRenderer);
                    //contentPane.add(sp);
                    JFrame newWindow = new JFrame("Shortage Report");
                    Container newContentPane = newWindow.getContentPane();
                    newWindow.setVisible(true);
                    newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    newWindow.setBounds(0, 0, 1400, 300);
                    newWindow.setBackground(SystemColor.activeCaption);
                    newWindow.setForeground(SystemColor.activeCaption);
                    newContentPane.add(sp);
                }
                else
                    JOptionPane.showMessageDialog(getComponent(0), "No Shortage Report to Show", "Notice", JOptionPane.PLAIN_MESSAGE);
            }
        });
        contentPane.add(shortageReportButton);
        //set Damaged Expired categories textField
        JTextField DamagedExpiredCatText = new JTextField();
        DamagedExpiredCatText.setPreferredSize(new Dimension(250,40));
        DamagedExpiredCatText.setBounds(400, 250, 130, 30);
        DamagedExpiredCatText.setEnabled(false);
        contentPane.add(DamagedExpiredCatText);
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DamagedExpiredCatText.setEnabled(checkBox.isSelected());
            }
        });
        //set Damaged Expired button
        JButton DamagedExpiredReportButton = new JButton("Damaged Expired Report");
        DamagedExpiredReportButton.setBounds(600, 250, 200,30);
        DamagedExpiredReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                branch = imanage.getBranch();
                reportMaker = new ReportMaker(branch);
                String[] column;
                String[][] data;
                if(!checkBox.isSelected()){
                    column = new String[]{"Catalog Number", "Description","Manufacturer", "ID", "Product Integrity", "Damage Type", "Expiry Date"};
                    data = new String[branch.getInventoryItems().size()][7];
                    AtomicInteger i= new AtomicInteger();
                    branch.getDamagedItems().forEach((itemCN, item) -> {
                        data[i.get()][0] = String.valueOf(item.getCatalogNum());
                        data[i.get()][1]= item.getName();
                        data[i.get()][2]= item.getManufacturer();
                        data[i.get()][3] = String.valueOf(item.getID());
                        data[i.get()][4] = String.valueOf(item.getDamage());
                        data[i.get()][5] = item.getDamageType();
                        data[i.get()][6] = String.valueOf(item.getExpireDate());
                        i.getAndIncrement();
                    });
                    branch.getStoreInventory().forEach((itemCN, item) -> {
                        if(item.getDamage() == ProductIntegrity.Expired || (item.getExpireDate() != null && item.getDamage() == ProductIntegrity.Null && (LocalDate.now().until(item.getExpireDate(), ChronoUnit.DAYS) <= 7 ))){
                            data[i.get()][0] = String.valueOf(item.getCatalogNum());
                            data[i.get()][1]= item.getName();
                            data[i.get()][2]= item.getManufacturer();
                            data[i.get()][3] = String.valueOf(item.getID());
                            data[i.get()][4] = String.valueOf(item.getDamage());
                            data[i.get()][5] = item.getDamageType();
                            data[i.get()][6] = String.valueOf(item.getExpireDate());
                            i.getAndIncrement();
                        }
                    });
                    branch.getWarehouseInventory().forEach((itemCN, item) -> {
                        if(item.getDamage() == ProductIntegrity.Expired || (item.getExpireDate() != null && item.getDamage() == ProductIntegrity.Null && (LocalDate.now().until(item.getExpireDate(), ChronoUnit.DAYS) <= 7 ))){
                            data[i.get()][0] = String.valueOf(item.getCatalogNum());
                            data[i.get()][1]= item.getName();
                            data[i.get()][2]= item.getManufacturer();
                            data[i.get()][3] = String.valueOf(item.getID());
                            data[i.get()][4] = String.valueOf(item.getDamage());
                            data[i.get()][5] = item.getDamageType();
                            data[i.get()][6] = String.valueOf(item.getExpireDate());
                            i.getAndIncrement();
                        }
                    });
                }
                else{
                    column = new String[]{"Category's Name", "Catalog Number", "Description","Manufacturer", "ID", "Product Integrity", "Damage Type", "Expiry Date"};
                    data = new String[branch.getInventoryItems().size()][8];
                    AtomicInteger h= new AtomicInteger();
                    ArrayList<Category> categories = new ArrayList<>();
                    String inputText = DamagedExpiredCatText.getText();
                    String[] splittedText = inputText.split(",");
                    for (int i=0; i<splittedText.length; i++) {
                        int cID = Integer.parseInt(splittedText[i]);
                        Category cat = (Category) CategoryDAO.getInstance().getById(cID);
                        categories.add(cat);
                    }
                    for (int i = 0; i < categories.size(); i++) {
                        Category now = categories.get(i);
                        branch.getStoreInventory().forEach((itemCN, item) -> {
                            if((item.getDamage() == ProductIntegrity.Expired && item.getItemCategories().contains(now))
                                    || (item.getDamage() == ProductIntegrity.Damaged && item.getItemCategories().contains(now))
                                    || (item.getDamage() == ProductIntegrity.Null && (item.getExpireDate() != null && LocalDate.now().until(item.getExpireDate(), ChronoUnit.DAYS) <= 7 ) && item.getItemCategories().contains(now))){
                                data[h.get()][0] = now.getcName();
                                data[h.get()][1] = String.valueOf(item.getCatalogNum());
                                data[h.get()][2] = item.getName();
                                data[h.get()][3] = item.getManufacturer();
                                data[h.get()][4] = String.valueOf(item.getID());
                                data[h.get()][5] = String.valueOf(item.getDamage());
                                data[h.get()][6] = item.getDamageType();
                                data[h.get()][7] = String.valueOf(item.getExpireDate());
                                h.getAndIncrement();
                            }
                        });
                        branch.getWarehouseInventory().forEach((itemCN, item) -> {
                            if((item.getDamage() == ProductIntegrity.Expired && item.getItemCategories().contains(now))
                                    || (item.getDamage() == ProductIntegrity.Damaged && item.getItemCategories().contains(now))
                                    || (item.getDamage() == ProductIntegrity.Null && (item.getExpireDate() != null && LocalDate.now().until(item.getExpireDate(), ChronoUnit.DAYS) <= 7 ) && item.getItemCategories().contains(now))){
                                data[h.get()][0] = now.getcName();
                                data[h.get()][1] = String.valueOf(item.getCatalogNum());
                                data[h.get()][2] = item.getName();
                                data[h.get()][3] = item.getManufacturer();
                                data[h.get()][4] = String.valueOf(item.getID());
                                data[h.get()][5] = String.valueOf(item.getDamage());
                                data[h.get()][6] = item.getDamageType();
                                data[h.get()][7] = String.valueOf(item.getExpireDate());
                                h.getAndIncrement();
                            }
                        });
                    }
                }
                if(data.length != 0) {
                    JTable table = new JTable(data, column);
                    table.setBackground(SystemColor.activeCaption);
                    table.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                    JScrollPane sp = new JScrollPane(table);
                    sp.setBounds(0, 350, 1300, 150);
                    sp.getViewport().setBackground(SystemColor.activeCaption);
                    sp.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    JTableHeader header = table.getTableHeader();
                    header.setBackground(SystemColor.activeCaption);
                    header.setFont(new Font("Tahoma", Font.BOLD, 14));
                    header.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
                    header.setResizingAllowed(false);
                    header.setReorderingAllowed(false);
                    table.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
                    table.setGridColor(SystemColor.activeCaption);
                    table.setEnabled(false);
                    Border lineBorder = BorderFactory.createLineBorder(SystemColor.activeCaption, 2);
                    sp.setBorder(lineBorder);
                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                    table.setDefaultRenderer(Object.class, centerRenderer);
                    //contentPane.add(sp);
                    JFrame newWindow = new JFrame("Damaged Expired Report");
                    Container newContentPane = newWindow.getContentPane();
                    newWindow.setVisible(true);
                    newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    newWindow.setBounds(0, 0, 1400, 300);
                    newWindow.setBackground(SystemColor.activeCaption);
                    newWindow.setForeground(SystemColor.activeCaption);
                    newContentPane.add(sp);
                }
                else
                    JOptionPane.showMessageDialog(getComponent(0), "No Damaged Or Expired to Show", "Notice", JOptionPane.PLAIN_MESSAGE);
            }
        });
        contentPane.add(DamagedExpiredReportButton);
    }
}

