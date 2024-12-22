package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.Branch;
import BusinessLayer.InventoryModule.InventoryManagment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditIntegrity extends JFrame {
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

    public EditIntegrity() {
        imanage = InventoryManagment.getInstance();
        setTitle("Edit Product Integrity");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //small label
        JLabel chooseLbl = new JLabel("Edit Product Integrity");
        chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        chooseLbl.setBounds(0, 40, 613, 59);
        contentPane.add(chooseLbl);
        //set integrity label
        JLabel integrityLabel = new JLabel("Product Integrity");
        integrityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        integrityLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        integrityLabel.setBounds(170, 100, 180, 30);
        contentPane.add(integrityLabel);
        //set integrity comboBox
        String[] inte = {"Damaged", "Expired"};
        JComboBox<String> integrityText = new JComboBox<>(inte);
        integrityText.setPreferredSize(new Dimension(250, 40));
        integrityText.setBounds(320, 100, 100, 30);
        integrityText.setBackground(SystemColor.white);
        contentPane.add(integrityText);
        //set damage type label
        JLabel typeLabel = new JLabel("Damage Type");
        typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        typeLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        typeLabel.setBounds(170, 150, 180, 30);
        contentPane.add(typeLabel);
        //set type textField
        JTextField typeText = new JTextField();
        typeText.setPreferredSize(new Dimension(250,40));
        typeText.setBounds(320, 150, 100, 30);
        contentPane.add(typeText);
        //set discount label
        JLabel discountLabel = new JLabel("<html>Discount Rate<br>for X% press X</html>");
        discountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        discountLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        discountLabel.setBounds(170, 190, 180, 50);
        contentPane.add(discountLabel);
        //set discount textField
        JTextField discountText = new JTextField();
        discountText.setPreferredSize(new Dimension(250,40));
        discountText.setBounds(320, 200, 100, 30);
        contentPane.add(discountText);
        JButton btnAddSupplier = new JButton("Edit");
        btnAddSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                String integrity;
                if (integrityText.getSelectedItem() == "Damaged") {
                    integrity = "D";
                } else {
                    integrity = "E";
                }
                imanage.editPIntegrity(getItemID(), integrity, typeText.getText(), Double.parseDouble(discountText.getText()));
                JOptionPane.showMessageDialog(getComponent(0), "Integrity Changed", "Successful edit", JOptionPane.PLAIN_MESSAGE);
                dispose();
            }
        });
        btnAddSupplier.setBounds(217, 250, 230, 33);
        contentPane.add(btnAddSupplier);
    }
}

