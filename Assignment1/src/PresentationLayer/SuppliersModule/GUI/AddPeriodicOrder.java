package PresentationLayer.SuppliersModule.GUI;

import BusinessLayer.SuppliersModule.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPeriodicOrder extends JFrame {
    JTextField supplierId_textField;
    JPanel contentPane;

    OrdersController ordersController;
    SuppliersController suppliersController;
    boolean exists;
    public AddPeriodicOrder() {

        this.suppliersController = SuppliersController.getInstance();
        this.ordersController = OrdersController.getInstance();
        setTitle("Add Periodic Order ");
        exists=false;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        JLabel title = new JLabel("Add Periodic Order");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        title.setBounds(0, 30, 613, 59);
        contentPane.add(title);

        JLabel lblsupplierid = new JLabel("Insert supplier id:");
        lblsupplierid.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblsupplierid.setHorizontalAlignment(SwingConstants.CENTER);
        lblsupplierid.setBounds(10, 124, 180, 20);
        contentPane.add(lblsupplierid);

        supplierId_textField = new JTextField();
        supplierId_textField.setBounds(217, 120, 230, 33);
        contentPane.add(supplierId_textField);
        supplierId_textField.setColumns(10);

        ///////

        JButton btnAdd = new JButton("Add");

        btnAdd.addActionListener(new ActionListener()
        {
            String orderID="-1";
            public void actionPerformed(ActionEvent e) {
                String supplierID = supplierId_textField.getText();
                if (supplierID == null || supplierID.trim().isEmpty())
                    JOptionPane.showMessageDialog(getComponent(0), "Invalid Input", "Warning", 0);

                else if (!suppliersController.doesSupplierExistByID(supplierID)) {
                    JOptionPane.showMessageDialog(getComponent(0), "Supplier ID Doesn't Exist", "Warning", 0);
                } else
                {
                    Supplier supplier = suppliersController.getSupplierByID(supplierID);
                    int real_amount = supplier.getContract().getProducts().size();
                    if (real_amount == 0) {
                        JOptionPane.showMessageDialog(getComponent(0), "Supplier doesn't provide products yet", "Warning", 0);
                    }
                    orderID=ordersController.getPeriodicOrderNumberBySupplierID(supplierID);
                    if (orderID!=null)
                    {


                        exists = true;
                    }
                    if(real_amount!=0){
                        if (exists) {
                            ordersController.removePeriodicOrderByOrderID(orderID);
                        }
                        HashMap<Integer, Integer> order_products = new HashMap<>();
                        for (Map.Entry<String, SupplierProduct> entry : supplier.getContract().getProducts().entrySet())
                        {
                            order_products.put(entry.getValue().getCatalog_number(), entry.getValue().getAmount());
                        }
                        ordersController.addPeriodicOrder("Periodic", supplierID,order_products);
                        JOptionPane.showMessageDialog(getComponent(0), "Successfully Added! ", "Warning", 0);
                    }
                }
            }
        });
        btnAdd.setBounds(400, 350, 200, 23);
        contentPane.add(btnAdd);


        JButton btnExit = new JButton("Back to Menu");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        btnExit.setBounds(100, 350, 200, 23);
        contentPane.add(btnExit);




    }





}