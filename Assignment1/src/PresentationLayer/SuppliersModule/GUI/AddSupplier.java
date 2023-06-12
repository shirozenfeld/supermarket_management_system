package PresentationLayer.SuppliersModule.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSupplier extends JFrame
{

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField supplierName_textField;
    private JTextField domainName_textField;
    private JTextField pocName_textField;
    private JTextField phoneNumber_textField;
    private JTextField emailAdrress_textField;
    private JTextField city_textField;
    private JTextField street_textField;
    private JTextField buildingNumber_textField;
    private JTextField phcNumber_textField;
    private JTextField bankAccountNumber_textField;
    private JComboBox paymentConditions_comboBOX;
    private JComboBox periodicDayDelivery_comboBOX;
    private JComboBox supplierType_comboBOX;
    private JComboBox visitingDayPersistantVisiting_comboBOX;
    private JTextField delayDaysOrderlyVisiting_textField;

    /**
     * Create the frame.
     */

    public AddSupplier() {
        setTitle("Add Savings Account ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel addSupplier = new JLabel("Add Supplier");
        addSupplier.setFont(new Font("Tahoma", Font.BOLD, 16));
        addSupplier.setHorizontalAlignment(SwingConstants.CENTER);
        addSupplier.setBounds(10, 11, 414, 34);
        contentPane.add(addSupplier);

        JLabel lblName = new JLabel("Name:");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblName.setBounds(10, 72, 124, 14);
        contentPane.add(lblName);

        supplierName_textField = new JTextField();
        supplierName_textField.setBounds(144, 69, 254, 20);
        contentPane.add(supplierName_textField);
        supplierName_textField.setColumns(10);

        ///////
        JLabel lbDomainName = new JLabel("Domain Name:");
        lbDomainName.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lbDomainName.setBounds(10, 118, 124, 14);
        contentPane.add(lbDomainName);

        domainName_textField = new JTextField();
        domainName_textField.setColumns(10);
        domainName_textField.setBounds(144, 115, 254, 20);
        contentPane.add(domainName_textField);

        ///////////////
        JLabel lblPOCName = new JLabel("POC number:");
        lblPOCName.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPOCName.setBounds(10, 163, 135, 14);
        contentPane.add(lblPOCName);

        pocName_textField = new JTextField();
        pocName_textField.setColumns(10);
        pocName_textField.setBounds(144, 160, 254, 20);
        contentPane.add(pocName_textField);

        ///////////////
        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPhoneNumber.setBounds(10, 163, 135, 14);
        contentPane.add(lblPhoneNumber);

        phoneNumber_textField = new JTextField();
        phoneNumber_textField.setColumns(10);
        phoneNumber_textField.setBounds(144, 160, 254, 20);
        contentPane.add(phoneNumber_textField);

        ///////////////
        JLabel lbEmailAdrress = new JLabel("Email Adrress:");
        lbEmailAdrress.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lbEmailAdrress.setBounds(10, 163, 135, 14);
        contentPane.add(lbEmailAdrress);

        emailAdrress_textField = new JTextField();
        emailAdrress_textField.setColumns(10);
        emailAdrress_textField.setBounds(144, 160, 254, 20);
        contentPane.add(emailAdrress_textField);

        ///////////////
        JLabel lblCity = new JLabel("City:");
        lblCity.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblCity.setBounds(10, 163, 135, 14);
        contentPane.add(lblCity);

        city_textField = new JTextField();
        city_textField.setColumns(10);
        city_textField.setBounds(144, 160, 254, 20);
        contentPane.add(city_textField);

        ///////////////
        JLabel lbStreet = new JLabel("Street:");
        lbStreet.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lbStreet.setBounds(10, 163, 135, 14);
        contentPane.add(lbStreet);

        street_textField = new JTextField();
        street_textField.setColumns(10);
        street_textField.setBounds(144, 160, 254, 20);
        contentPane.add(street_textField);

        ///////////////
        JLabel lblBuildingNumber = new JLabel("Building Number:");
        lblBuildingNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblBuildingNumber.setBounds(10, 163, 135, 14);
        contentPane.add(lblBuildingNumber);

        buildingNumber_textField = new JTextField();
        buildingNumber_textField.setColumns(10);
        buildingNumber_textField.setBounds(144, 160, 254, 20);
        contentPane.add(buildingNumber_textField);

        ///////

        JLabel lblphcNumber = new JLabel("PHC Number:");
        lblphcNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblphcNumber.setBounds(10, 118, 124, 14);
        contentPane.add(lblphcNumber);

        phcNumber_textField = new JTextField();
        phcNumber_textField.setColumns(10);
        phcNumber_textField.setBounds(144, 115, 254, 20);
        contentPane.add(phcNumber_textField);

        ///////

        JLabel lbBankAccountNumber = new JLabel("Bank Account Number:");
        lbBankAccountNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lbBankAccountNumber.setBounds(10, 118, 124, 14);
        contentPane.add(lbBankAccountNumber);

        bankAccountNumber_textField = new JTextField();
        bankAccountNumber_textField.setColumns(10);
        bankAccountNumber_textField.setBounds(144, 115, 254, 20);
        contentPane.add(bankAccountNumber_textField);


        ///////

        JLabel lblPaymentConditions = new JLabel("PHC Number:");
        lblPaymentConditions.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPaymentConditions.setBounds(10, 118, 124, 14);
        contentPane.add(lblPaymentConditions);

        String [] payments={"shotef","shotef+30","shotef+60"};
        paymentConditions_comboBOX = new JComboBox(payments);
        paymentConditions_comboBOX.setBounds(144, 115, 254, 20);
        contentPane.add(paymentConditions_comboBOX);

        ///////


        JLabel lbPeriodicDayDelivery = new JLabel("Periodic Day Delivery");
        lbPeriodicDayDelivery.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lbPeriodicDayDelivery.setBounds(10, 118, 124, 14);
        contentPane.add(lbPeriodicDayDelivery);

        String [] days={"Sunday","Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        periodicDayDelivery_comboBOX = new JComboBox(days);
        periodicDayDelivery_comboBOX.setBounds(144, 115, 254, 20);
        contentPane.add(periodicDayDelivery_comboBOX);


        ///////

        JLabel lblSuppliersType = new JLabel("Supplier Type:");
        lblSuppliersType.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblSuppliersType.setBounds(10, 118, 124, 14);
        contentPane.add(lblSuppliersType);
        String [] types={"Orderly Visiting","Persistently Visiting","Not Visiting"};
        supplierType_comboBOX = new JComboBox(types);
        supplierType_comboBOX.setBounds(144, 115, 254, 20);
        contentPane.add(supplierType_comboBOX);

        ////


        JLabel lbdelayDaysOrderlyVisiting = new JLabel("Delay Days Orderly Visiting:");
        lbdelayDaysOrderlyVisiting.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lbdelayDaysOrderlyVisiting.setBounds(10, 118, 124, 14);
        contentPane.add(lbdelayDaysOrderlyVisiting);

        delayDaysOrderlyVisiting_textField = new JTextField();
        delayDaysOrderlyVisiting_textField.setColumns(10);
        delayDaysOrderlyVisiting_textField.setBounds(144, 115, 254, 20);
        contentPane.add(delayDaysOrderlyVisiting_textField);



        /////
        JLabel lblPersistantVisitingDays = new JLabel("Day of shortage order delivery:");
        lblPersistantVisitingDays.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPersistantVisitingDays.setBounds(10, 118, 124, 14);
        contentPane.add(lblPersistantVisitingDays);
        visitingDayPersistantVisiting_comboBOX = new JComboBox(days);
        visitingDayPersistantVisiting_comboBOX.setBounds(144, 115, 254, 20);
        contentPane.add(visitingDayPersistantVisiting_comboBOX);

        /////
        JButton btnAdd = new JButton("Add");
        /*
        btnAdd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                String name=lblName.getText();



                double bal=Double.parseDouble(textField_1.getText());
                double maxw=Double.parseDouble(textField_2.getText());
                if(bal<2000)
                {
                    JOptionPane.showMessageDialog(getComponent(0), "Minimum Limit 5000", "Warning", 0);
                    textField.setText(null);
                    textField_1.setText(null);
                    textField_2.setText(null);
                }
                else
                {
                    if(name==null||bal<=0||maxw<=0)
                    {
                        JOptionPane.showMessageDialog(getComponent(0),"Typing Mismatch!! Try Again");
                        textField.setText(null);
                        textField_1.setText(null);
                        textField_2.setText(null);
                    }
                    else
                    {
                        int ch=JOptionPane.showConfirmDialog(getComponent(0), "Confirm?");
                        if(ch==0)
                        {
                            int index = FileIO.bank.addAccount(name, bal, maxw);
                            DisplayList.arr.addElement(FileIO.bank.getAccounts()[index].toString());
                            //file.Write(FileIO.bank);
                            JOptionPane.showMessageDialog(getComponent(0),"Added Successfully");
                            dispose();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(getComponent(0),"Failed");
                            textField.setText(null);
                            textField_1.setText(null);
                            textField_2.setText(null);
                        }
                        textField.setText(null);
                        textField_1.setText(null);
                        textField_2.setText(null);

                    }
                }
            }
        });
        btnAdd.setBounds(86, 209, 89, 23);
        contentPane.add(btnAdd);

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                supplierName_textField.setText(null);
                domainName_textField.setText(null);
                pocName_textField.setText(null);
                phoneNumber_textField.setText(null);
                emailAdrress_textField.setText(null);
                city_textField.setText(null);
                street_textField.setText(null);
                buildingNumber_textField.setText(null);
                phcNumber_textField.setText(null);
                bankAccountNumber_textField.setText(null);
                paymentConditions_comboBOX.setSelectedIndex(0);
                periodicDayDelivery_comboBOX.setSelectedIndex(0);
                supplierType_comboBOX.setSelectedIndex(0);
                visitingDayPersistantVisiting_comboBOX.setSelectedIndex(0);
                delayDaysOrderlyVisiting_textField.setText(null);

            }
        });
        btnReset.setBounds(309, 209, 89, 23);
        contentPane.add(btnReset);
*/
    }




}
