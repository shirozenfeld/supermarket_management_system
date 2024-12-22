package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.Branch;
import BusinessLayer.InventoryModule.InventoryManagment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class CatDiscount extends JFrame {
    private JPanel contentPane;
    private Branch branch;
    private InventoryManagment imanage;

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    public CatDiscount() {
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
        JLabel chooseLbl = new JLabel("Add Discount by Category");
        chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        chooseLbl.setBounds(30, 40, 613, 59);
        contentPane.add(chooseLbl);
        //set category label
        JLabel cnLabel = new JLabel("Category ID");
        cnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cnLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cnLabel.setBounds(170, 150, 180, 30);
        contentPane.add(cnLabel);
        //set category textField
        JTextField cnText = new JTextField();
        cnText.setPreferredSize(new Dimension(250,40));
        cnText.setBounds(320, 150, 100, 30);
        contentPane.add(cnText);
        //set start label
        JLabel startLabel = new JLabel("<html>Start Date<br>(YYYY-MM-DD)</html>");
        startLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        startLabel.setBounds(170, 200, 180, 30);
        contentPane.add(startLabel);
        //set start textField
        JTextField startText = new JTextField();
        startText.setPreferredSize(new Dimension(250,40));
        startText.setBounds(320, 200, 100, 30);
        contentPane.add(startText);
        //set end label
        JLabel endLabel = new JLabel("<html>End Date<br>(YYYY-MM-DD)</html>");
        endLabel.setHorizontalAlignment(SwingConstants.CENTER);
        endLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        endLabel.setBounds(170, 250, 180, 30);
        contentPane.add(endLabel);
        //set end textField
        JTextField endText = new JTextField();
        endText.setPreferredSize(new Dimension(250,40));
        endText.setBounds(320, 250, 100, 30);
        contentPane.add(endText);
        //set discount label
        JLabel discountLabel = new JLabel("<html>Discount Rate<br>for X% press X</html>");
        discountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        discountLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        discountLabel.setBounds(170, 290, 180, 50);
        contentPane.add(discountLabel);
        //set discount textField
        JTextField discountText = new JTextField();
        discountText.setPreferredSize(new Dimension(250,40));
        discountText.setBounds(320, 300, 100, 30);
        contentPane.add(discountText);
        JButton btnAddSupplier = new JButton("Add");
        btnAddSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                LocalDate start = LocalDate.parse(startText.getText());
                LocalDate end = LocalDate.parse(endText.getText());
                imanage.addDiscountByCategory(Integer.parseInt(cnText.getText()), Double.parseDouble(discountText.getText()), start, end);
                JOptionPane.showMessageDialog(getComponent(0), "Category Discount Added", "Successful add", JOptionPane.PLAIN_MESSAGE);
            }
        });
        btnAddSupplier.setBounds(217, 350, 230, 33);
        contentPane.add(btnAddSupplier);
    }
}
