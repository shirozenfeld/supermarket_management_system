package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.Branch;
import BusinessLayer.InventoryModule.InventoryManagment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SetCounted extends JFrame{
    private JPanel contentPane;
    private Branch branch;
    private InventoryManagment imanage;

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    public SetCounted() {
        imanage = InventoryManagment.getInstance();
        setTitle("Set Counted Amount");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //small label
        JLabel chooseLbl = new JLabel("Set Counted Amount");
        chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        chooseLbl.setBounds(35, 40, 613, 59);
        contentPane.add(chooseLbl);
        //set catalog number label
        JLabel cnLabel = new JLabel("Catalog Number");
        cnLabel.setHorizontalAlignment(SwingConstants.LEFT);
        cnLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cnLabel.setBounds(170, 150, 230, 30);
        contentPane.add(cnLabel);
        //set catalog number textField
        JTextField cnText = new JTextField();
        cnText.setPreferredSize(new Dimension(250,40));
        cnText.setBounds(400, 150, 100, 30);
        contentPane.add(cnText);
        //set store label
        JLabel storeLabel = new JLabel("Counted Amount in Store");
        storeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        storeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        storeLabel.setBounds(170, 200, 230, 30);
        contentPane.add(storeLabel);
        //set store textField
        JTextField storeText = new JTextField();
        storeText.setPreferredSize(new Dimension(250,40));
        storeText.setBounds(400, 200, 100, 30);
        contentPane.add(storeText);
        //set warehouse label
        JLabel warehouseLabel = new JLabel("Counted Amount in Warehouse");
        warehouseLabel.setHorizontalAlignment(SwingConstants.LEFT);
        warehouseLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        warehouseLabel.setBounds(170, 250, 230, 30);
        contentPane.add(warehouseLabel);
        //set warehouse textField
        JTextField warehouseText = new JTextField();
        warehouseText.setPreferredSize(new Dimension(250,40));
        warehouseText.setBounds(400, 250, 100, 30);
        contentPane.add(warehouseText);
        JButton btnAddSupplier = new JButton("Update");
        btnAddSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                imanage.setCountedAmount(Integer.parseInt(cnText.getText()), Integer.parseInt(storeText.getText()), Integer.parseInt(warehouseText.getText()));
                JOptionPane.showMessageDialog(getComponent(0), "Counted Amount Updated", "Successful update", JOptionPane.PLAIN_MESSAGE);
            }
        });
        btnAddSupplier.setBounds(217, 300, 230, 33);
        contentPane.add(btnAddSupplier);
    }
}
