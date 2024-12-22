package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.Branch;
import BusinessLayer.InventoryModule.InventoryManagment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RemoveItem extends JFrame {
    private JPanel contentPane;
    private Branch branch;
    private InventoryManagment imanage;

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    public RemoveItem() {
        imanage = InventoryManagment.getInstance();
        setTitle("Delete Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //small label
        JLabel chooseLbl = new JLabel("Delete Item");
        chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        chooseLbl.setBounds(0, 40, 613, 59);
        contentPane.add(chooseLbl);

        //set id label
        JLabel idLabel = new JLabel("Item ID");
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        idLabel.setBounds(170, 150, 180, 30);
        contentPane.add(idLabel);
        //set id textField
        JTextField idText = new JTextField();
        idText.setPreferredSize(new Dimension(250,40));
        idText.setBounds(320, 150, 100, 30);
        contentPane.add(idText);
        JButton btnAddSupplier = new JButton("Delete");
        btnAddSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                ArrayList<Integer> isInStore = imanage.deleteItem(Integer.parseInt(idText.getText()));
                JOptionPane.showMessageDialog(getComponent(0), "Item Deleted", "Successful edit", JOptionPane.PLAIN_MESSAGE);
                if (isInStore.size() > 1) {
                    String message ="<html>Item with catalog number " + Integer.parseInt(idText.getText())/10000 + " has reached it's minimum amount<br>" +
                            "Minimum amount: " + isInStore.get(0) +
                            "<br>Current amount: " + isInStore.get(1) +"</html>";
                    JOptionPane.showMessageDialog(getComponent(0), message, "NOTICE!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnAddSupplier.setBounds(217, 200, 230, 33);
        contentPane.add(btnAddSupplier);
    }
}
