package PresentationLayer.SuppliersModule.GUI;

import BusinessLayer.SuppliersModule.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class WatchHistoryOrders extends JFrame {
    private JPanel contentPane;
    private JTextField supplierIDTextField;
    private JTable table;
    private DefaultTableModel tableModel;
    int amount = 0;
    OrdersController ordersController;
    SuppliersController suppliersController;
    ManufacturerController manufacturerController;

    public WatchHistoryOrders() {
        this.ordersController= OrdersController.getInstance();
        this.suppliersController=SuppliersController.getInstance();
        this.manufacturerController=ManufacturerController.getInstance();

        setTitle("WatchHistoryOrders ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 400);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        JLabel lbSupplierID = new JLabel("SupplierID:");
        lbSupplierID.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lbSupplierID.setBounds(10, 20, 124, 14);
        contentPane.add(lbSupplierID);

        supplierIDTextField = new JTextField();
        supplierIDTextField.setColumns(10);
        supplierIDTextField.setBounds(144, 97, 254, 20);
        contentPane.add(supplierIDTextField);


        JButton btnShow = new JButton("Show History Orders");

        btnShow.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int yOffset = 20;

                    String supplierID = supplierIDTextField.getText();

                    GUIForm.watchHistoryOrders.setVisible(true);
//                    DeficienciesReport dailyReport=ordersController.getDailyShortageReport();
//                    ordersController.deficiencies_handler(dailyReport);
                    JLabel lblOrder = new JLabel("\n History Order By SupplierID ");
                    lblOrder.setFont(new Font("Tahoma", Font.BOLD, 16));
                    lblOrder.setHorizontalAlignment(SwingConstants.CENTER);
                    lblOrder.setBounds(10, yOffset+44, 600, 38);
                    contentPane.add(lblOrder);

                    String[] orderColumnNames = {"SupplierID", "OrderID", "First Price", "Final Price", "Catalog Number", "amount"};
                    DefaultTableModel orderTableModel = new DefaultTableModel(orderColumnNames, 0);
                    JTable orderTable = new JTable(orderTableModel);
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
                    sp1.setBorder(lineBorder1);
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

                    /*for (Map.Entry<String, Order> entry_ : dailyReport.getOrders().entrySet()) {
                        Order order = entry_.getValue();
                        SupplierID = Integer.parseInt(order.getSupplierID());
                        firstPrice = order.getFirst_price();
                        finalPrice = order.getFinale_price();
                        products_list = order.getProducts();

                        if(SupplierID == Integer.parseInt(supplierID)){
                            for (Map.Entry<Integer, Integer> entry1 : products_list.entrySet())
                            {
                                catalog_number = entry1.getKey();
                                amount = entry1.getValue();

                                Object[] rowData = {SupplierID, order.getOrder_id(), firstPrice, finalPrice, catalog_number, amount};
                                orderTableModel.addRow(rowData);
                            }
                        }
                    }

                     */



                    JScrollPane orderScrollPane = new JScrollPane(orderTable);
                    orderScrollPane.setBounds(10, yOffset, 800, 100);
                    contentPane.add(orderScrollPane);

                }
                catch (Exception t)
                {
                    JOptionPane.showMessageDialog(getComponent(0), "There is an empty textBox. Try Again");

                }
            }
        });
        btnShow.setBounds(11, 200, 200, 23);
        contentPane.add(btnShow);
    }

}
