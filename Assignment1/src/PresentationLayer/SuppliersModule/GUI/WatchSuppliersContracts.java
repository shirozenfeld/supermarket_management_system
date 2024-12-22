package PresentationLayer.SuppliersModule.GUI;

import BusinessLayer.SuppliersModule.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WatchSuppliersContracts extends JFrame {

    private JPanel contentPane;

    OrdersController ordersController;
    SuppliersController suppliersController;
    ManufacturerController manufacturerController;
    DefaultTableModel tableModel;
    JTable table;

    /**
     * Create the frame.
     */

    public WatchSuppliersContracts() {
        this.ordersController = OrdersController.getInstance();
        this.suppliersController = SuppliersController.getInstance();
        this.manufacturerController = ManufacturerController.getInstance();

        setTitle("Watch Suppliers Contract");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 950, 800);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("Watch Suppliers Contract");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        title.setBounds(0, 30, 613, 59);
        contentPane.add(title);


        String[] columnNames = {"Supplier ID","Product Name", "Manufacturer", "Catalog Number", "Amount", "Unit Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setBackground(SystemColor.activeCaption);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0, 350, 400, 150);
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
        Map<String,List<String[]>> contracts=suppliersController.getSuppliersContracts();

        for (Map.Entry<String,List<String[]>> entry : contracts.entrySet())
        {
            for(int i=0;i<entry.getValue().size();i++) {
                String[] values=entry.getValue().get(i);
                Object[] rowData = {values[0],values[1],values[2],values[3],values[4],values[5]};
                tableModel.addRow(rowData);
            }
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 188, 850, 300);
        contentPane.add(scrollPane);


        JButton btnExit = new JButton("Back to Menu");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        btnExit.setBounds(217, 500, 200, 23);
        contentPane.add(btnExit);

    }






}




