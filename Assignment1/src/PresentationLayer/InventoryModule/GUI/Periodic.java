package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.*;
import DataAccessLayer.InventoryModule.InventoryItemDAO;
import DataAccessLayer.InventoryModule.PeriodicOrderDAO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Periodic extends JFrame {
    private JPanel contentPane;
    private Branch branch;
    private InventoryManagment imanage;

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    public Periodic() {
        imanage = InventoryManagment.getInstance();
        setTitle("Edit Periodic Order");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //small label
        JLabel chooseLbl = new JLabel("Edit Periodic Order");
        chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        chooseLbl.setBounds(0, 40, 613, 59);
        contentPane.add(chooseLbl);
        //set order label
        JLabel orderLabel = new JLabel("Order ID");
        orderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        orderLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        orderLabel.setBounds(170, 100, 180, 30);
        contentPane.add(orderLabel);
        //set order textField
        JTextField orderText = new JTextField();
        orderText.setPreferredSize(new Dimension(250, 40));
        orderText.setBounds(320, 100, 100, 30);
        contentPane.add(orderText);
        JButton btnAddSupplier = new JButton("Display Periodic Order");
        btnAddSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                if (imanage.validPOrderUpdate(Integer.parseInt(orderText.getText()))) {
                    PeriodicOrder pOrder = (PeriodicOrder) PeriodicOrderDAO.getInstance().getById(Integer.parseInt(orderText.getText()));
                    String[] column;
                    String[][] data;
                    column = new String[]{"Catalog Number", "Description", "Amount"};
                    data = new String[pOrder.getIdAmountMap().size()][3];
                    AtomicInteger i = new AtomicInteger();
                    pOrder.getIdAmountMap().forEach((itemCN, amount) -> {
                        BasicItem bi = imanage.getSuperli().basicIsInStore(itemCN);
                        data[i.get()][0] = String.valueOf(itemCN);
                        data[i.get()][1] = bi.getName();
                        data[i.get()][2] = String.valueOf(amount);
                        i.getAndIncrement();
                    });
                    if (data.length != 0) {
                        JTable table = new JTable(data, column);
                        table.setBackground(SystemColor.activeCaption);
                        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                        JScrollPane sp = new JScrollPane(table);
                        sp.setBounds(170, 200, 400, 150);
                        sp.getViewport().setBackground(SystemColor.activeCaption);
                        sp.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        JTableHeader header = table.getTableHeader();
                        header.setBackground(SystemColor.activeCaption);
                        header.setFont(new Font("Tahoma", Font.BOLD, 14));
                        header.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
                        header.setResizingAllowed(false);
                        header.setReorderingAllowed(false);
                        table.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
                        table.setGridColor(SystemColor.activeCaption);
                        table.setEnabled(false);
                        Border lineBorder = BorderFactory.createLineBorder(SystemColor.activeCaption, 2);
                        sp.setBorder(lineBorder);
                        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                        table.setDefaultRenderer(Object.class, centerRenderer);
                        contentPane.add(sp);
                        //set item label
                        JLabel itemLabel = new JLabel("Catalog Number");
                        itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        itemLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
                        itemLabel.setBounds(170, 380, 180, 30);
                        contentPane.add(itemLabel);
                        //set item textField
                        JTextField itemText = new JTextField();
                        itemText.setPreferredSize(new Dimension(250, 40));
                        itemText.setBounds(320, 380, 100, 30);
                        contentPane.add(itemText);
                        //set amount label
                        JLabel amountLabel = new JLabel("Amount");
                        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        amountLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
                        amountLabel.setBounds(170, 420, 180, 30);
                        contentPane.add(amountLabel);
                        //set order textField
                        JTextField amountText = new JTextField();
                        amountText.setPreferredSize(new Dimension(250, 40));
                        amountText.setBounds(320, 420, 100, 30);
                        contentPane.add(amountText);
                        JButton update = new JButton("Update");
                        update.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                Map<Integer, Integer> idAmountMap = new HashMap<Integer, Integer>();
                                idAmountMap.put(Integer.parseInt(itemText.getText()), Integer.parseInt(amountText.getText()));
                                imanage.editPeriodicOrder(Integer.parseInt(orderText.getText()), idAmountMap);
                                PeriodicOrder pOrder = (PeriodicOrder) PeriodicOrderDAO.getInstance().getById(Integer.parseInt(orderText.getText()));
                                String[] column;
                                String[][] data;
                                column = new String[]{"Catalog Number", "Description", "Amount"};
                                data = new String[pOrder.getIdAmountMap().size()][3];
                                AtomicInteger i = new AtomicInteger();
                                pOrder.getIdAmountMap().forEach((itemCN, amount) -> {
                                    BasicItem bi = imanage.getSuperli().basicIsInStore(itemCN);
                                    data[i.get()][0] = String.valueOf(itemCN);
                                    data[i.get()][1] = bi.getName();
                                    data[i.get()][2] = String.valueOf(amount);
                                    i.getAndIncrement();
                                });
                                if (data.length != 0) {
                                    JTable table = new JTable(data, column);
                                    table.setBackground(SystemColor.activeCaption);
                                    table.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                                    JScrollPane sp = new JScrollPane(table);
                                    sp.setBounds(170, 200, 400, 150);
                                    sp.getViewport().setBackground(SystemColor.activeCaption);
                                    sp.setFont(new Font("Tahoma", Font.PLAIN, 14));
                                    JTableHeader header = table.getTableHeader();
                                    header.setBackground(SystemColor.activeCaption);
                                    header.setFont(new Font("Tahoma", Font.BOLD, 14));
                                    header.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
                                    header.setResizingAllowed(false);
                                    header.setReorderingAllowed(false);
                                    table.setBorder(BorderFactory.createLineBorder(SystemColor.activeCaption, 2));
                                    table.setGridColor(SystemColor.activeCaption);
                                    table.setEnabled(false);
                                    Border lineBorder = BorderFactory.createLineBorder(SystemColor.activeCaption, 2);
                                    sp.setBorder(lineBorder);
                                    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                                    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                                    table.setDefaultRenderer(Object.class, centerRenderer);
                                    contentPane.add(sp);
                                }
                            }
                        });
                        update.setBounds(217, 470, 230, 33);
                        contentPane.add(update);
                        validate();
                        repaint();
                    }
                }
                else
                    JOptionPane.showMessageDialog(getComponent(0), "No Periodic Order to Show", "Notice", JOptionPane.PLAIN_MESSAGE);
            }
        });
        btnAddSupplier.setBounds(217, 150, 230, 33);
        contentPane.add(btnAddSupplier);
    }
}
