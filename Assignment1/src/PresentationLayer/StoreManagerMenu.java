package PresentationLayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreManagerMenu extends JFrame
{


    private JPanel contentPane;
    public StoreManagerMenu() {
        setTitle("Store Manager Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnSuppliersMenu = new JButton("Suppliers Issues");
        btnSuppliersMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                PresentationLayer.SuppliersModule.GUI.GUIForm.suppliersMenu=new PresentationLayer.SuppliersModule.GUI.SuppliersMenu();
                if(!PresentationLayer.SuppliersModule.GUI.GUIForm.suppliersMenu.isVisible())
                {
                    PresentationLayer.SuppliersModule.GUI.GUIForm.suppliersMenu.setVisible(true);

                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }


            }
        });
        btnSuppliersMenu.setBounds(118, 60, 193, 38);
        contentPane.add(btnSuppliersMenu);

        JButton btnInventoryMenu = new JButton("Inventory Issues");
        btnInventoryMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                PresentationLayer.InventoryModule.GUI.GUIForm.stockkeeper=new PresentationLayer.InventoryModule.GUI.Stockkeeper();
                if(!PresentationLayer.InventoryModule.GUI.GUIForm.stockkeeper.isVisible())
                {
                    PresentationLayer.InventoryModule.GUI.GUIForm.stockkeeper.setVisible(true);

                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }


            }
        });
        btnInventoryMenu.setBounds(118, 104, 193, 38);
        contentPane.add(btnInventoryMenu);




        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(getComponent(0), "Thanks For Using") ;
                System.exit(0);
            }
        });
        btnExit.setBounds(118, 200, 193, 38);
        contentPane.add(btnExit);


    }
}
