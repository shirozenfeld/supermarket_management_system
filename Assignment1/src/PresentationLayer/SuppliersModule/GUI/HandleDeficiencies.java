package PresentationLayer.SuppliersModule.GUI;

import BusinessLayer.SuppliersModule.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class HandleDeficiencies extends JFrame {

    private JPanel contentPane;
    private JTextField supplierName_textField;
    private JTable table;
    private DefaultTableModel tableModel;
    int amount = 0;
    OrdersController ordersController;
    SuppliersController suppliersController;
    ManufacturerController manufacturerController;


    /**
     * Create the frame.
     */

    public HandleDeficiencies() {

        this.ordersController= OrdersController.getInstance();
        this.suppliersController=SuppliersController.getInstance();
        this.manufacturerController=ManufacturerController.getInstance();

        setTitle("Add Savings Account ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel HandleDeficiencies = new JLabel("Handle Deficiencies Report");
        HandleDeficiencies.setFont(new Font("Tahoma", Font.BOLD, 20));
        HandleDeficiencies.setHorizontalAlignment(SwingConstants.CENTER);
        HandleDeficiencies.setBounds(10, 11, 414, 34);
        contentPane.add(HandleDeficiencies);
        DeficienciesReport dailyReport=ordersController.getDailyShortageReport();
        ordersController.deficiencies_handler(dailyReport);
        int yOffset = 11;


        JLabel lblHandle = new JLabel("Deficiencies Report");
        lblHandle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblHandle.setHorizontalAlignment(SwingConstants.CENTER);
        lblHandle.setBounds(10, yOffset+44, 414, 38);
        contentPane.add(lblHandle);
        yOffset+=44;


        String[] columnNames = {"Product name", "Amount", "Manufacturer"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setBackground(SystemColor.activeCaption);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0, 350, 400, 100);
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

        yOffset+=44;


        for (Map.Entry<SuperProduct, Integer> entry : dailyReport.getProducts().entrySet()) {
            SuperProduct superProduct = entry.getKey();
            amount = entry.getValue();
            String manufacturer_name =  superProduct.getManufacturer().getManufacturer_name();

            Object[] rowData = {superProduct.getProduct_name(), amount, manufacturer_name};
            tableModel.addRow(rowData);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, yOffset, 414, 100);
        contentPane.add(scrollPane);

        yOffset+=70;


        JLabel lblOrder = new JLabel("\n Orders Under Report ");
        lblOrder.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblOrder.setHorizontalAlignment(SwingConstants.CENTER);
        lblOrder.setBounds(10, yOffset+44, 600, 38);
        contentPane.add(lblOrder);

        String[] orderColumnNames = {"SupplierID", "OrderID", "First Price", "Final Price", "Catalog Number", "amount"};
        DefaultTableModel orderTableModel = new DefaultTableModel(orderColumnNames, 0);
        JTable orderTable = new JTable(orderTableModel);
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        orderTable.setBackground(SystemColor.activeCaption);
        orderTable.setFont(new Font("Tahoma", Font.PLAIN, 12));

        orderTable.setBackground(SystemColor.activeCaption);
        orderTable.setFont(new Font("Tahoma", Font.PLAIN, 14));
        orderTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        JScrollPane sp1 = new JScrollPane(orderTable);
        sp1.setBounds(0, 350, 600, 100);
        sp1.getViewport().setBackground(SystemColor.activeCaption);
        sp1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        JTableHeader header1 = table.getTableHeader();
        header1.setBackground(SystemColor.activeCaption);
        header1.setFont(new Font("Tahoma", Font.BOLD, 14));
        header1.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
        header1.setResizingAllowed(false);
        header1.setReorderingAllowed(false);
        orderTable.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
        orderTable.setGridColor(SystemColor.activeCaption);
        orderTable.setEnabled(false);
        Border lineBorder1 = BorderFactory.createLineBorder(SystemColor.activeCaption, 2);
        sp.setBorder(lineBorder1);
        DefaultTableCellRenderer centerRenderer1 = new DefaultTableCellRenderer();
        centerRenderer1.setHorizontalAlignment(SwingConstants.CENTER);
        orderTable.setDefaultRenderer(Object.class, centerRenderer1);


        yOffset+=50;

        Map<java.lang.Integer,java.lang.Integer> products_list;
        int SupplierID;
        Double firstPrice;
        Double finalPrice;
        int catalog_number;
        int amount;
        yOffset +=44;
        int i = 1;

        for (Map.Entry<String, Order> entry_ : dailyReport.getOrders().entrySet()) {
            Order order = entry_.getValue();

            SupplierID = Integer.parseInt(order.getSupplierID());
            firstPrice = order.getFirst_price();
            finalPrice = order.getFinale_price();
            products_list = order.getProducts();

            for (Map.Entry<Integer, Integer> entry1 : products_list.entrySet())
            {
                catalog_number = entry1.getKey();
                amount = entry1.getValue();

                Object[] rowData = {SupplierID, order.getOrder_id(), firstPrice, finalPrice, catalog_number, amount};
                orderTableModel.addRow(rowData);
            }
        }

        JScrollPane orderScrollPane = new JScrollPane(orderTable);
        orderScrollPane.setBounds(10, yOffset, 800, 100);
        contentPane.add(orderScrollPane);


    }


}







