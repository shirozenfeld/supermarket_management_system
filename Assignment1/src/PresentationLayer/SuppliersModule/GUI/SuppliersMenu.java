package PresentationLayer.SuppliersModule.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuppliersMenu extends JFrame
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public SuppliersMenu() {
        setTitle("SuperLi Management System");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 800, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //BIG LABEL
        JLabel superLiManagementSystem = new JLabel("Welcome to SuperLi's Management System");
        superLiManagementSystem.setHorizontalAlignment(SwingConstants.CENTER);
        superLiManagementSystem.setFont(new Font("Tahoma", Font.BOLD, 24));
        superLiManagementSystem.setBounds(0, 30, 613, 59);
        contentPane.add(superLiManagementSystem);
        //small label
        JLabel chooseLbl = new JLabel("Please choose your option:");
        chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 12));
        chooseLbl.setBounds(0, 70, 613, 59);
        contentPane.add(chooseLbl);

        //FileIO.Read();
        JButton btnAddSupplier = new JButton("Add Supplier");
        btnAddSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GUIForm.addSupplier.isVisible())
                {
                    GUIForm.addSupplier.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }

            }
        });
        btnAddSupplier.setBounds(217, 120, 230, 33);
        contentPane.add(btnAddSupplier);

        ///////////

        JButton btnRemoveSupplier = new JButton("Remove Supplier");
        btnRemoveSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GUIForm.removeSupplier.isVisible())
                {
                    GUIForm.removeSupplier.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }


            }

        });
        btnRemoveSupplier.setBounds(217, 164, 230, 33);
        contentPane.add(btnRemoveSupplier);
        /////////


        JButton btnUpdateSuppliersCard = new JButton("Update Supplier's Card");
        btnUpdateSuppliersCard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(!GUIForm.updateCard.isVisible())
                {
                    GUIForm.updateCard.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }

            }
        });
        btnUpdateSuppliersCard.setBounds(217, 208, 230, 33);
        contentPane.add(btnUpdateSuppliersCard);


        JButton btnUpdateContract = new JButton("Update Supplier's Contract");
        btnUpdateContract.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(!GUIForm.updateContract.isVisible())
                {
                    GUIForm.updateContract.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }
            }
        });
        btnUpdateContract.setBounds(217, 252, 230, 33);
        contentPane.add(btnUpdateContract);

        //////////

        JButton btnUpdateVisitingDay = new JButton("Update Visiting Day");
        btnUpdateVisitingDay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GUIForm.updateVisitingDay.isVisible())
                {
                    GUIForm.updateVisitingDay.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }


            }

        });
        btnUpdateVisitingDay.setBounds(217, 296, 230, 33);
        contentPane.add(btnUpdateVisitingDay);



        JButton btnWatchVisitingDay = new JButton("Watch Visiting Day");
        btnWatchVisitingDay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(!GUIForm.watchVisitingDay.isVisible())
                {
                    GUIForm.watchVisitingDay.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }
            }
        });
        btnWatchVisitingDay.setBounds(217, 340, 230, 33);
        contentPane.add(btnWatchVisitingDay);




        JButton btnAddManufacturer = new JButton("Add Manufacturer");
        btnAddManufacturer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GUIForm.addManufacturer.isVisible())
                {
                    GUIForm.addManufacturer.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }
            }
        });
        btnAddManufacturer.setBounds(217, 384, 230, 36);
        contentPane.add(btnAddManufacturer);



        JButton btnHandleDeficiencies = new JButton("Handle Deficiencies");
        btnHandleDeficiencies.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(!GUIForm.handleDeficiencies.isVisible())
                {
                    GUIForm.handleDeficiencies.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }
            }
        });
        btnHandleDeficiencies.setBounds(217, 428, 230, 33);
        contentPane.add(btnHandleDeficiencies);



        ///////////////
        JButton btnWatchSuppliersContract = new JButton("Watch Suppliers' Contracts Report");
        btnWatchSuppliersContract.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GUIForm.watchSuppliersContracts.isVisible())
                {
                    GUIForm.watchSuppliersContracts.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }
            }
        });
        btnWatchSuppliersContract.setBounds(217, 472, 230, 36);
        contentPane.add(btnWatchSuppliersContract);

        JButton btnAddPeriodicOrder = new JButton("Add Periodic Order");
        btnAddPeriodicOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GUIForm.addPeriodicOrder.isVisible())
                {
                    GUIForm.addPeriodicOrder.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }
            }
        });
        btnAddPeriodicOrder.setBounds(217, 516, 230, 36);
        contentPane.add(btnAddPeriodicOrder);


        //////////////

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(getComponent(0), "Thanks For Using") ;
                System.exit(0);
            }
        });
        btnExit.setBounds(217, 560, 230, 33);
        contentPane.add(btnExit);



        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon(SuppliersMenu.class.getResource("supermarket.png")));
        lblNewLabel.setBounds(500, 100, 216, 213);
        contentPane.add(lblNewLabel);

        ImageIcon icon = new ImageIcon("PresentationLayer/SuppliersModule/GUI/supermarket.png");
    }


}
