package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.*;
import DataAccessLayer.InventoryModule.BasicItemDAO;
import DataAccessLayer.InventoryModule.InventoryItemDAO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class History extends JFrame {
    private JPanel contentPane;
    private Branch branch;
    private InventoryManagment imanage;

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    public History() {
        imanage = InventoryManagment.getInstance();
        setTitle("History Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //small label
        JLabel chooseLbl = new JLabel("History Report");
        chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        chooseLbl.setBounds(0, 40, 613, 59);
        contentPane.add(chooseLbl);
        //set catalog number label
        JLabel cnLabel = new JLabel("Catalog Number");
        cnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cnLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cnLabel.setBounds(170, 100, 180, 30);
        contentPane.add(cnLabel);
        //set catalog number textField
        JTextField cnText = new JTextField();
        cnText.setPreferredSize(new Dimension(250,40));
        cnText.setBounds(320, 100, 100, 30);
        contentPane.add(cnText);
        JButton btnAddSupplier = new JButton("Sell Price History");
        btnAddSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                InventoryItem invi = (InventoryItem)InventoryItemDAO.getInstance().getById(Integer.parseInt(cnText.getText()));
                String[] column;
                String[][] data;
                column = new String[]{"Date", "Sell Price"};
                data = new String[invi.getSellPriceHistory().size()][2];
                AtomicInteger i= new AtomicInteger();
                invi.getSellPriceHistory().forEach((date, price) -> {
                    data[i.get()][0] = date;
                    data[i.get()][1]= String.valueOf(price);
                    i.getAndIncrement();
                });
                if(data.length != 0) {
                    JTable table = new JTable(data, column);
                    table.setBackground(SystemColor.activeCaption);
                    table.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                    JScrollPane sp = new JScrollPane(table);
                    sp.setBounds(0, 350, 400, 150);
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
                    JFrame newWindow = new JFrame("Sell Price History");
                    Container newContentPane = newWindow.getContentPane();
                    newWindow.setVisible(true);
                    newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    newWindow.setBounds(0, 0, 400, 500);
                    newWindow.setBackground(SystemColor.activeCaption);
                    newWindow.setForeground(SystemColor.activeCaption);
                    newContentPane.add(sp);
                }
                else
                    JOptionPane.showMessageDialog(getComponent(0), "No Sell Price History to Show", "Notice", JOptionPane.PLAIN_MESSAGE);

            }
        });
        btnAddSupplier.setBounds(217, 150, 230, 33);
        contentPane.add(btnAddSupplier);

        JButton rateHistory = new JButton("Discount Rate History");
        rateHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                InventoryItem invi = (InventoryItem)InventoryItemDAO.getInstance().getById(Integer.parseInt(cnText.getText()));
                String[] column;
                String[][] data;
                column = new String[]{"Start Date", "End Date", "Discount Rate"};
                data = new String[invi.getDiscountHistory().size()][3];
                for(int i=0; i<invi.getDiscountHistory().size();i++){
                    Discount now = invi.getDiscountHistory().get(i);
                    data[i][0] = String.valueOf(now.getStartDate());
                    data[i][1] = String.valueOf(now.getEndDate());
                    data[i][2] = String.valueOf(now.getDiscountRate());
                }
                if(data.length != 0) {
                    JTable table = new JTable(data, column);
                    table.setBackground(SystemColor.activeCaption);
                    table.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                    JScrollPane sp = new JScrollPane(table);
                    sp.setBounds(0, 350, 400, 150);
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
                    JFrame newWindow = new JFrame("Discount Rate History");
                    Container newContentPane = newWindow.getContentPane();
                    newWindow.setVisible(true);
                    newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    newWindow.setBounds(0, 0, 400, 500);
                    newWindow.setBackground(SystemColor.activeCaption);
                    newWindow.setForeground(SystemColor.activeCaption);
                    newContentPane.add(sp);
                }
                else
                    JOptionPane.showMessageDialog(getComponent(0), "No Discount History to Show", "Notice", JOptionPane.PLAIN_MESSAGE);
            }
        });
        rateHistory.setBounds(217, 200, 230, 33);
        contentPane.add(rateHistory);
    }
}
