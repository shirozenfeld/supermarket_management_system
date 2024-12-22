package PresentationLayer.SuppliersModule.GUI;

import BusinessLayer.SuppliersModule.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddSupplier extends JFrame
{
    ;

    private JPanel contentPane;

    public static DelayDaysWindow delayDaysWindow= new DelayDaysWindow();;
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

     String dayVisiting;
    int delayDays;

    OrdersController ordersController;
    SuppliersController suppliersController;
    ManufacturerController manufacturerController;


    /**
     * Create the frame.
     */

    public AddSupplier() {
        delayDays=-1;
        dayVisiting="";
        this.ordersController= OrdersController.getInstance();
        this.suppliersController=SuppliersController.getInstance();
        this.manufacturerController=ManufacturerController.getInstance();

        setTitle("Add Supplier ");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 800);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);

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
        lblName.setBounds(10, 45, 414, 38);
        contentPane.add(lblName);

        supplierName_textField = new JTextField();
        supplierName_textField.setBounds(144, 54, 254, 20);
        contentPane.add(supplierName_textField);
        supplierName_textField.setColumns(10);

        ///////
        JLabel lbDomainName = new JLabel("Domain Name:");
        lbDomainName.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lbDomainName.setBounds(10, 99, 124, 14);
        contentPane.add(lbDomainName);

        domainName_textField = new JTextField();
        domainName_textField.setColumns(10);
        domainName_textField.setBounds(144, 97, 254, 20);
        contentPane.add(domainName_textField);

        ///////////////
        JLabel lblPOCName = new JLabel("POC number:");
        lblPOCName.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPOCName.setBounds(10, 143, 135, 14);
        contentPane.add(lblPOCName);

        pocName_textField = new JTextField();
        pocName_textField.setColumns(10);
        pocName_textField.setBounds(144, 140, 254, 20);
        contentPane.add(pocName_textField);

        ///////////////
        JLabel lblPhoneNumber = new JLabel("Phone Number:");
        lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPhoneNumber.setBounds(10, 187, 135, 14);
        contentPane.add(lblPhoneNumber);

        phoneNumber_textField = new JTextField();
        phoneNumber_textField.setColumns(10);
        phoneNumber_textField.setBounds(144, 185, 254, 20);
        contentPane.add(phoneNumber_textField);

        ///////////////
        JLabel lbEmailAdrress = new JLabel("Email Adrress:");
        lbEmailAdrress.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lbEmailAdrress.setBounds(10, 231, 135, 14);
        contentPane.add(lbEmailAdrress);

        emailAdrress_textField = new JTextField();
        emailAdrress_textField.setColumns(10);
        emailAdrress_textField.setBounds(144, 231, 254, 20);
        contentPane.add(emailAdrress_textField);

        ///////////////
        JLabel lblCity = new JLabel("City:");
        lblCity.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblCity.setBounds(10, 275, 135, 14);
        contentPane.add(lblCity);

        city_textField = new JTextField();
        city_textField.setColumns(10);
        city_textField.setBounds(144, 276, 254, 20);
        contentPane.add(city_textField);

        ///////////////
        JLabel lbStreet = new JLabel("Street:");
        lbStreet.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lbStreet.setBounds(10, 319, 135, 14);
        contentPane.add(lbStreet);

        street_textField = new JTextField();
        street_textField.setColumns(10);
        street_textField.setBounds(144, 319, 254, 20);
        contentPane.add(street_textField);

        ///////////////
        JLabel lblBuildingNumber = new JLabel("Building Number:");
        lblBuildingNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblBuildingNumber.setBounds(10, 363, 135, 14);
        contentPane.add(lblBuildingNumber);

        buildingNumber_textField = new JTextField();
        buildingNumber_textField.setColumns(10);
        buildingNumber_textField.setBounds(144, 363, 254, 20);
        contentPane.add(buildingNumber_textField);

        ///////

        JLabel lblphcNumber = new JLabel("PHC Number:");
        lblphcNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblphcNumber.setBounds(10, 407, 124, 14);
        contentPane.add(lblphcNumber);

        phcNumber_textField = new JTextField();
        phcNumber_textField.setColumns(10);
        phcNumber_textField.setBounds(144, 407, 254, 20);
        contentPane.add(phcNumber_textField);

        ///////

        JLabel lbBankAccountNumber = new JLabel("Bank Account Number:");
        lbBankAccountNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lbBankAccountNumber.setBounds(10, 451, 124, 14);
        contentPane.add(lbBankAccountNumber);

        bankAccountNumber_textField = new JTextField();
        bankAccountNumber_textField.setColumns(10);
        bankAccountNumber_textField.setBounds(144, 451, 254, 20);
        contentPane.add(bankAccountNumber_textField);


        ///////

        JLabel lblPaymentConditions = new JLabel("Payment Conditions:");
        lblPaymentConditions.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPaymentConditions.setBounds(10, 495, 124, 14);
        contentPane.add(lblPaymentConditions);

        String [] payments={"shotef","shotef+30","shotef+60"};
        paymentConditions_comboBOX = new JComboBox(payments);
        paymentConditions_comboBOX.setBounds(144, 495, 254, 20);
        contentPane.add(paymentConditions_comboBOX);

        ///////


        JLabel lbPeriodicDayDelivery = new JLabel("Periodic Day Delivery");
        lbPeriodicDayDelivery.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lbPeriodicDayDelivery.setBounds(10, 539, 124, 14);
        contentPane.add(lbPeriodicDayDelivery);

        String [] days={"Sunday","Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        periodicDayDelivery_comboBOX = new JComboBox(days);
        periodicDayDelivery_comboBOX.setBounds(144, 539, 254, 20);
        contentPane.add(periodicDayDelivery_comboBOX);


        ///////
        JLabel lblSuppliersType = new JLabel("Supplier Type:");
        lblSuppliersType.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblSuppliersType.setBounds(10, 583, 124, 14);
        contentPane.add(lblSuppliersType);
        String [] types={"Orderly Visiting","Persistently Visiting","Not Visiting"};
        supplierType_comboBOX = new JComboBox(types);
        supplierType_comboBOX.setBounds(144, 583, 254, 20);
        contentPane.add(supplierType_comboBOX);
        ////


        JButton btnAdd = new JButton("Add");

        btnAdd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    String name = supplierName_textField.getText();
                    String domain = domainName_textField.getText();
                    String poc = pocName_textField.getText();
                    String phoneNumber = phoneNumber_textField.getText();
                    String email = emailAdrress_textField.getText();
                    String city = city_textField.getText();
                    String street = street_textField.getText();
                    String phcNumber = phcNumber_textField.getText();
                    int bankAccountNumber = Integer.parseInt(bankAccountNumber_textField.getText());
                    int buildingNumber = Integer.parseInt(buildingNumber_textField.getText());
                    String paymentCondition = (String) paymentConditions_comboBOX.getSelectedItem();
                    String periodicDayDelivery = (String) periodicDayDelivery_comboBOX.getSelectedItem();
                    String supplierType=(String)supplierType_comboBOX.getSelectedItem();
                    if (name == null || domain == null || poc == null || phoneNumber == null
                            || email == null || city == null || street == null || buildingNumber < 0 || bankAccountNumber < 0 || paymentCondition == null || periodicDayDelivery == null || phcNumber == null) {
                        JOptionPane.showMessageDialog(getComponent(0), "There is an empty textBox. Try Again");
                        reset();


                    }

                    if (suppliersController.doesSupplierExistByName(name)) {
                        JOptionPane.showMessageDialog(getComponent(0), "Supplier already exist", "Warning", 0);
                        reset();
                    }
                    else
                    {
                        List<String> domains = new ArrayList<>();
                        domains.add(domain);
                        if (supplierType.equals("Orderly Visiting")) {

                            GUIForm.delayDaysWindow.setVisible(true);

                            if (delayDays >= 0) {

                                JOptionPane.showMessageDialog(getComponent(0), "Added Successfully");
                                //delayDays = delayDaysWindow.getDelayDays();
                                suppliersController.addOrderlyVisitingSupplier(poc, phoneNumber, email, city, street, buildingNumber, phcNumber, bankAccountNumber, Card.Bill.valueOf(paymentCondition), Day.valueOf(periodicDayDelivery), delayDays, name, domains);
                                reset();
                            }

                        }

                        if (supplierType.equals("Persistently Visiting"))
                        {
                            GUIForm.shortageDeliveryWindow.setVisible(true);

                            if (dayVisiting !=null)
                            {
                                List<Day> days = new ArrayList<>();
                                days.add(Day.valueOf(dayVisiting));
                                suppliersController.addPersistentVisitingSupplier(poc, phoneNumber, email, city, street, buildingNumber, phcNumber, bankAccountNumber, Card.Bill.valueOf(paymentCondition), Day.valueOf(periodicDayDelivery), name, domains, days);
                                JOptionPane.showMessageDialog(getComponent(0), "Added Successfully");
                                reset();
                            }

                        }

                        if (supplierType.equals("Not Visiting")) {
                            suppliersController.addNotVisitingSupplier(poc, phoneNumber, email, city, street, buildingNumber, phcNumber, bankAccountNumber, Card.Bill.valueOf(paymentCondition), Day.valueOf(periodicDayDelivery), name, domains);
                            JOptionPane.showMessageDialog(getComponent(0), "Added Successfully");
                            reset();
                        }
                    }
                }
                catch (Exception t)
                {
                    JOptionPane.showMessageDialog(getComponent(0), "There is an empty textBox. Try Again");
                    reset();
                }
            }
        });
        btnAdd.setBounds(400, 710, 89, 23);
        contentPane.add(btnAdd);

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                reset();

            }
        });
        btnReset.setBounds(600, 710, 89, 23);
        contentPane.add(btnReset);

        JButton btnExit = new JButton("Back to Menu");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        btnExit.setBounds(150, 710, 139, 23);
        contentPane.add(btnExit);



    }
    public void setDelayDays(int number)
    {
        delayDays=number;
    }
    public void setShortageVisitingDay(String day)
    {
        this.dayVisiting=day;
    }

    public void reset()
    {
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

    }

}
