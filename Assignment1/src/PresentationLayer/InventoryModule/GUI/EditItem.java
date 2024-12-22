package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.Branch;
import BusinessLayer.InventoryModule.InventoryManagment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditItem extends JFrame {
    private Branch branch;
    private InventoryManagment imanage;
    private JPanel contentPane;

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public EditItem() {
        imanage = InventoryManagment.getInstance();
        setTitle("Edit Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //small label
        JLabel chooseLbl = new JLabel("What would you like to edit?");
        chooseLbl.setHorizontalAlignment(SwingConstants.CENTER);
        chooseLbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        chooseLbl.setBounds(30, 0, 613, 59);
        contentPane.add(chooseLbl);
        //set item ID label
        JLabel idLabel = new JLabel("Item ID");
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        idLabel.setBounds(170, 70, 180, 30);
        contentPane.add(idLabel);
        //set item ID textField
        JTextField idText = new JTextField();
        idText.setPreferredSize(new Dimension(250,40));
        idText.setBounds(320, 70, 100, 30);
        contentPane.add(idText);
        JButton btnAddSupplier = new JButton("Cost Price");
        btnAddSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GUIForm.editCP.isVisible())
                {
                    imanage.branchInSuper(getBranch().getBranchID());
                    setBranch(imanage.getBranch());
                    GUIForm.editCP.setBranch(getBranch());
                    GUIForm.editCP.setItemID(Integer.parseInt(idText.getText()));
                    GUIForm.editCP.setVisible(true);
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

        JButton btnRemoveSupplier = new JButton("Sell Price");
        btnRemoveSupplier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GUIForm.editSP.isVisible())
                {
                    imanage.branchInSuper(getBranch().getBranchID());
                    setBranch(imanage.getBranch());
                    GUIForm.editSP.setBranch(getBranch());
                    GUIForm.editSP.setItemID(Integer.parseInt(idText.getText()));
                    GUIForm.editSP.setVisible(true);
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


        JButton btnUpdateSuppliersCard = new JButton("Product Integrity");
        btnUpdateSuppliersCard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GUIForm.editIntegrity.isVisible())
                {
                    imanage.branchInSuper(getBranch().getBranchID());
                    setBranch(imanage.getBranch());
                    GUIForm.editIntegrity.setBranch(getBranch());
                    GUIForm.editIntegrity.setItemID(Integer.parseInt(idText.getText()));
                    GUIForm.editIntegrity.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }
            }
        });
        btnUpdateSuppliersCard.setBounds(217, 208, 230, 33);
        contentPane.add(btnUpdateSuppliersCard);


        JButton btnUpdateContract = new JButton("Location in branch");
        btnUpdateContract.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GUIForm.editLoc.isVisible())
                {
                    imanage.branchInSuper(getBranch().getBranchID());
                    setBranch(imanage.getBranch());
                    GUIForm.editLoc.setBranch(getBranch());
                    GUIForm.editLoc.setItemID(Integer.parseInt(idText.getText()));
                    GUIForm.editLoc.setVisible(true);
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

        JButton btnUpdateVisitingDay = new JButton("Minimum amount");
        btnUpdateVisitingDay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GUIForm.editMin.isVisible())
                {
                    imanage.branchInSuper(getBranch().getBranchID());
                    setBranch(imanage.getBranch());
                    GUIForm.editMin.setBranch(getBranch());
                    GUIForm.editMin.setItemID(Integer.parseInt(idText.getText()));
                    GUIForm.editMin.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }
            }

        });
        btnUpdateVisitingDay.setBounds(217, 296, 230, 33);
        contentPane.add(btnUpdateVisitingDay);



        JButton btnWatchVisitingDay = new JButton("Set discount");
        btnWatchVisitingDay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GUIForm.editDis.isVisible())
                {
                    imanage.branchInSuper(getBranch().getBranchID());
                    setBranch(imanage.getBranch());
                    GUIForm.editDis.setBranch(getBranch());
                    GUIForm.editDis.setItemID(Integer.parseInt(idText.getText()));
                    GUIForm.editDis.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Already Opened", "Warning", 0);
                }
            }
        });
        btnWatchVisitingDay.setBounds(217, 340, 230, 33);
        contentPane.add(btnWatchVisitingDay);

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int choice = JOptionPane.showOptionDialog(null, "Do you want to Exit?", "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                } else {

                }
            }
        });
        btnExit.setBounds(217, 384, 230, 33);
        contentPane.add(btnExit);


        ImageIcon icon = new ImageIcon("PresentationLayer/SuppliersModule/GUI/supermarket.png");
    }


}