package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.Branch;
import BusinessLayer.InventoryModule.InventoryManagment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class EditCostPrice extends JFrame {
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

    public EditCostPrice() {
        imanage = InventoryManagment.getInstance();
        setTitle("Edit Cost Price");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //small label
        JLabel chooseLbl = new JLabel("Edit Cost Price");
        chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        chooseLbl.setBounds(0, 40, 613, 59);
        contentPane.add(chooseLbl);
        //set cost label
        JLabel costLabel = new JLabel("New Cost Price");
        costLabel.setHorizontalAlignment(SwingConstants.CENTER);
        costLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        costLabel.setBounds(170, 100, 180, 30);
        contentPane.add(costLabel);
        //set cost textField
        JTextField costText = new JTextField();
        costText.setPreferredSize(new Dimension(250,40));
        costText.setBounds(320, 100, 100, 30);
        contentPane.add(costText);
        JButton btnAddSupplier = new JButton("Edit");
        btnAddSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                imanage.editCost(getItemID(), Double.parseDouble(costText.getText()));
                JOptionPane.showMessageDialog(getComponent(0), "Cost Price Changed", "Successful edit", JOptionPane.PLAIN_MESSAGE);
                dispose();
            }
        });
        btnAddSupplier.setBounds(217, 150, 230, 33);
        contentPane.add(btnAddSupplier);
    }

}
