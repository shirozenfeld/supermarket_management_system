package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.BasicItem;
import BusinessLayer.InventoryModule.Branch;
import BusinessLayer.InventoryModule.InventoryManagment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stockkeeper extends JFrame {
    private JPanel contentPane;
    private Branch branch;
    private InventoryManagment imanage;

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Stockkeeper() {
        setTitle("SuperLi Management System - StockKeeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 0, 800, 700);
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

        JLabel whichBranch = new JLabel("Branch ID");
        whichBranch.setHorizontalAlignment(SwingConstants.CENTER);
        whichBranch.setFont(new Font("Tahoma", Font.PLAIN, 16));
        whichBranch.setBounds(150, 150, 180, 30);
        contentPane.add(whichBranch);
        //set branch textField
        JTextField branchText = new JTextField();
        branchText.setPreferredSize(new Dimension(250,40));
        branchText.setBounds(320, 150, 100, 30);
        contentPane.add(branchText);

        JButton branchButton = new JButton("Continue");
        branchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                whichBranch.setVisible(false);
                branchText.setVisible(false);
                branchButton.setVisible(false);
                imanage = InventoryManagment.getInstance();
                imanage.branchInSuper(Integer.parseInt(branchText.getText()));
                setBranch(imanage.getBranch());
                //small label
                JLabel chooseLbl = new JLabel("Please choose your option:");
                chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
                chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 12));
                chooseLbl.setBounds(0, 70, 613, 59);
                contentPane.add(chooseLbl);

                //FileIO.Read();
                JButton btnAddSupplier = new JButton("Add Item");
                btnAddSupplier.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(!GUIForm.addItem.isVisible())
                        {
                            GUIForm.addItem.setBranch(getBranch());
                            GUIForm.addItem.setVisible(true);
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

                JButton btnRemoveSupplier = new JButton("Delete Item");
                btnRemoveSupplier.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(!GUIForm.deleteItem.isVisible())
                        {
                            GUIForm.deleteItem.setBranch(getBranch());
                            GUIForm.deleteItem.setVisible(true);
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


                JButton btnUpdateSuppliersCard = new JButton("Edit Item");
                btnUpdateSuppliersCard.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        if(!GUIForm.editItem.isVisible())
                        {
                            GUIForm.editItem.setBranch(getBranch());
                            GUIForm.editItem.setVisible(true);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                        }

                    }
                });
                btnUpdateSuppliersCard.setBounds(217, 208, 230, 33);
                contentPane.add(btnUpdateSuppliersCard);


                JButton btnUpdateContract = new JButton("Generate Report");
                btnUpdateContract.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        if(!GUIForm.generateReport.isVisible())
                        {
                            GUIForm.generateReport.setBranch(getBranch());
                            GUIForm.generateReport.setVisible(true);
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

                JButton btnUpdateVisitingDay = new JButton("Sell Price & Discount History");
                btnUpdateVisitingDay.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(!GUIForm.history.isVisible())
                        {
                            GUIForm.history.setBranch(getBranch());
                            GUIForm.history.setVisible(true);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                        }


                    }

                });
                btnUpdateVisitingDay.setBounds(217, 296, 230, 33);
                contentPane.add(btnUpdateVisitingDay);



                JButton btnCountedAmount = new JButton("Set Counted Amount");
                btnCountedAmount.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        if(!GUIForm.setCounted.isVisible())
                        {
                            GUIForm.setCounted.setBranch(getBranch());
                            GUIForm.setCounted.setVisible(true);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                        }
                    }
                });
                btnCountedAmount.setBounds(217, 340, 230, 33);
                contentPane.add(btnCountedAmount);




                JButton btnAddManufacturer = new JButton("Add Category Discount");
                btnAddManufacturer.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(!GUIForm.catDiscount.isVisible())
                        {
                            GUIForm.catDiscount.setBranch(getBranch());
                            GUIForm.catDiscount.setVisible(true);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                        }
                    }
                });
                btnAddManufacturer.setBounds(217, 384, 230, 36);
                contentPane.add(btnAddManufacturer);



                JButton btnPeriodicOrder = new JButton("Edit Period Order");
                btnPeriodicOrder.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        if(!GUIForm.periodic.isVisible())
                        {
                            GUIForm.periodic.setBranch(getBranch());
                            GUIForm.periodic.setVisible(true);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                        }
                    }
                });
                btnPeriodicOrder.setBounds(217, 428, 230, 33);
                contentPane.add(btnPeriodicOrder);
                validate();
                repaint();
            }
        });
        branchButton.setBounds(217, 200, 230, 33);
        contentPane.add(branchButton);



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
        lblNewLabel.setIcon(new ImageIcon(Stockkeeper.class.getResource("super.jpeg")));
        lblNewLabel.setBounds(500, 100, 216, 213);
        contentPane.add(lblNewLabel);

        ImageIcon icon = new ImageIcon("PresentationLayer/InventoryModule/GUI/super.jpeg");


        JButton btnBackToMenu = new JButton("Back to Menu");
        btnBackToMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        btnBackToMenu.setBounds(217, 510, 230, 33);
        contentPane.add(btnBackToMenu);

    }

}
