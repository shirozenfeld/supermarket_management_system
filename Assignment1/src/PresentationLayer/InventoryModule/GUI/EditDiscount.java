package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.Branch;
import BusinessLayer.InventoryModule.InventoryManagment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class EditDiscount extends JFrame {
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

    public EditDiscount() {
        imanage = InventoryManagment.getInstance();
        setTitle("Edit Discount");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //small label
        JLabel chooseLbl = new JLabel("Edit Discount");
        chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        chooseLbl.setBounds(0, 40, 613, 59);
        contentPane.add(chooseLbl);
        //set start label
        JLabel startLabel = new JLabel("<html>Start Date<br>(YYYY-MM-DD)</html>");
        startLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        startLabel.setBounds(170, 100, 180, 30);
        contentPane.add(startLabel);
        //set start textField
        JTextField startText = new JTextField();
        startText.setPreferredSize(new Dimension(250,40));
        startText.setBounds(320, 100, 100, 30);
        contentPane.add(startText);
        //set end label
        JLabel endLabel = new JLabel("<html>End Date<br>(YYYY-MM-DD)</html>");
        endLabel.setHorizontalAlignment(SwingConstants.CENTER);
        endLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        endLabel.setBounds(170, 150, 180, 30);
        contentPane.add(endLabel);
        //set end textField
        JTextField endText = new JTextField();
        endText.setPreferredSize(new Dimension(250,40));
        endText.setBounds(320, 150, 100, 30);
        contentPane.add(endText);
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
                LocalDate startd = LocalDate.parse(startText.getText());
                LocalDate endd = LocalDate.parse(endText.getText());
                imanage.editDiscount(getItemID(), startd, endd, Double.parseDouble(discountText.getText()));
                JOptionPane.showMessageDialog(getComponent(0), "Discount Changed", "Successful edit", JOptionPane.PLAIN_MESSAGE);
                dispose();
            }
        });
        btnAddSupplier.setBounds(217, 250, 230, 33);
        contentPane.add(btnAddSupplier);
    }
}
