package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.Branch;
import BusinessLayer.InventoryModule.InventoryManagment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditLocation extends JFrame {
    private JPanel contentPane;
    private int itemID;
    private Branch branch;
    private InventoryManagment imanage;

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    public int getItemID() {
        return itemID;
    }

    public EditLocation() {
        imanage = InventoryManagment.getInstance();
        setTitle("Edit Location");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //small label
        JLabel chooseLbl = new JLabel("Edit Location in Branch");
        chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        chooseLbl.setBounds(0, 40, 613, 59);
        contentPane.add(chooseLbl);
        //set location label
        JLabel integrityLabel = new JLabel("Location");
        integrityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        integrityLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        integrityLabel.setBounds(170, 100, 180, 30);
        contentPane.add(integrityLabel);
        //set location comboBox
        String[] loc = {"Store", "Warehouse"};
        JComboBox<String> locationText = new JComboBox<>(loc);
        locationText.setPreferredSize(new Dimension(250, 40));
        locationText.setBounds(320, 100, 100, 30);
        locationText.setBackground(SystemColor.white);
        contentPane.add(locationText);
        JButton btnAddSupplier = new JButton("Edit");
        btnAddSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                String loct;
                if (locationText.getSelectedItem() == "Store") {
                    loct = "S";
                } else {
                    loct = "W";
                }
                imanage.editLocation(getItemID(), loct);
                JOptionPane.showMessageDialog(getComponent(0), "Location Changed", "Successful edit", JOptionPane.PLAIN_MESSAGE);
                dispose();
            }
        });
        btnAddSupplier.setBounds(217, 150, 230, 33);
        contentPane.add(btnAddSupplier);
    }
}
