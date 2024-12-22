package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.Branch;
import BusinessLayer.InventoryModule.InventoryManagment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditMinimum extends JFrame {
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

    public EditMinimum() {
        imanage = InventoryManagment.getInstance();
        setTitle("Edit Minimum Amount");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //small label
        JLabel chooseLbl = new JLabel("Edit Minimum Amount");
        chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        chooseLbl.setBounds(0, 40, 613, 59);
        contentPane.add(chooseLbl);
        //set demand label
        JLabel demandLabel = new JLabel("Demand Per Day");
        demandLabel.setHorizontalAlignment(SwingConstants.CENTER);
        demandLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        demandLabel.setBounds(170, 100, 180, 30);
        contentPane.add(demandLabel);
        //set demand textField
        JTextField demandText = new JTextField();
        demandText.setPreferredSize(new Dimension(250,40));
        demandText.setBounds(320, 100, 100, 30);
        contentPane.add(demandText);
        //set supply label
        JLabel supplyLabel = new JLabel("Supply Time");
        supplyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        supplyLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        supplyLabel.setBounds(170, 150, 180, 30);
        contentPane.add(supplyLabel);
        //set supply textField
        JTextField supplyText = new JTextField();
        supplyText.setPreferredSize(new Dimension(250,40));
        supplyText.setBounds(320, 150, 100, 30);
        contentPane.add(supplyText);
        JButton btnAddSupplier = new JButton("Edit");
        btnAddSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                int newMin = Integer.parseInt(demandText.getText()) * Integer.parseInt(supplyText.getText());
                imanage.editMinAmount(getItemID(), newMin);
                JOptionPane.showMessageDialog(getComponent(0), "Minimum Amount Changed", "Successful edit", JOptionPane.PLAIN_MESSAGE);
                dispose();
            }
        });
        btnAddSupplier.setBounds(217, 200, 230, 33);
        contentPane.add(btnAddSupplier);
    }
}