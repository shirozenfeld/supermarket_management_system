package PresentationLayer.InventoryModule.GUI;

import BusinessLayer.InventoryModule.Branch;
import BusinessLayer.InventoryModule.InventoryManagment;
import BusinessLayer.InventoryModule.ReportMaker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class AddItem extends JFrame {
    private Branch branch;
    private InventoryManagment imanage;
    private JPanel contentPane;
    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    public AddItem(){
        //set contentPane
        setTitle("Add Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(0, 0, 1100, 700);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setForeground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        imanage = InventoryManagment.getInstance();
        //set main label
        JLabel addItem = new JLabel("Add Item");
        addItem.setHorizontalAlignment(SwingConstants.CENTER);
        addItem.setFont(new Font("Tahoma", Font.BOLD, 24));
        addItem.setBounds(250, 0 , 613, 59);
        contentPane.add(addItem);
        //set item catalog number label
        JLabel catalogNum = new JLabel("Item Catalog Number");
        catalogNum.setHorizontalAlignment(SwingConstants.CENTER);
        catalogNum.setFont(new Font("Tahoma", Font.PLAIN, 16));
        catalogNum.setBounds(400, 110, 180, 30);
        contentPane.add(catalogNum);
        //set item catalog number textField
        JTextField itemCNText = new JTextField();
        itemCNText.setPreferredSize(new Dimension(250,40));
        itemCNText.setBounds(600, 110, 100, 30);
        contentPane.add(itemCNText);
        //set item catalog number button
        JButton itemCNButton = new JButton("Continue");
        itemCNButton.setBounds(500, 150, 135, 33);
        final Formatter[] fmt = new Formatter[1];
        itemCNButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imanage.branchInSuper(getBranch().getBranchID());
                branch = imanage.getBranch();
                //basic doesnt exist
                //itemCNButton.setVisible(false);
                if (!imanage.basicExist(Integer.parseInt(itemCNText.getText()))) {
                    //set name label
                    JLabel itemName = new JLabel("Item's Name");
                    itemName.setHorizontalAlignment(SwingConstants.LEFT);
                    itemName.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    itemName.setBounds(150, 200, 200, 50);
                    contentPane.add(itemName);
                    //set name textField
                    JTextField nameText = new JTextField();
                    nameText.setPreferredSize(new Dimension(250, 50));
                    nameText.setBounds(350, 200, 100, 30);
                    contentPane.add(nameText);
                    //set manu label
                    JLabel manuNameLabel = new JLabel("Manufacturer Name");
                    manuNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    manuNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    manuNameLabel.setBounds(150, 250, 200, 50);
                    contentPane.add(manuNameLabel);
                    //set manu textField
                    JTextField manuText = new JTextField();
                    manuText.setPreferredSize(new Dimension(250, 50));
                    manuText.setBounds(350, 250, 100, 30);
                    contentPane.add(manuText);
                    //set cost price label
                    JLabel costPrice = new JLabel("Cost Price");
                    costPrice.setHorizontalAlignment(SwingConstants.LEFT);
                    costPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    costPrice.setBounds(150, 300, 200, 50);
                    contentPane.add(costPrice);
                    //set cost price textField
                    JTextField costPriceText = new JTextField();
                    costPriceText.setPreferredSize(new Dimension(250, 40));
                    costPriceText.setBounds(350, 300, 100, 30);
                    contentPane.add(costPriceText);
                    //set cats label
                    JLabel catsLabel = new JLabel("<html>Categories<br>Category Num-Category Name, etc</html>");
                    catsLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    catsLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    catsLabel.setBounds(150, 340, 200, 50);
                    contentPane.add(catsLabel);
                    //set cats textField
                    JTextField catsText = new JTextField();
                    catsText.setPreferredSize(new Dimension(250, 40));
                    catsText.setBounds(350, 350, 100, 30);
                    contentPane.add(catsText);
                    //set Minimun label
                    JLabel minimumLabel = new JLabel("Minimum Amount");
                    minimumLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    minimumLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    minimumLabel.setBounds(150, 400, 200, 50);
                    contentPane.add(minimumLabel);
                    //set minimum textField
                    JTextField MinimumText = new JTextField();
                    MinimumText.setPreferredSize(new Dimension(250, 40));
                    MinimumText.setBounds(350, 400, 100, 30);
                    contentPane.add(MinimumText);
                    //set sell price label
                    JLabel sellPriceLabel = new JLabel("Sell Price");
                    sellPriceLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    sellPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    sellPriceLabel.setBounds(550, 200, 200, 50);
                    contentPane.add(sellPriceLabel);
                    //set sell price textField
                    JTextField sellPriceText = new JTextField();
                    sellPriceText.setPreferredSize(new Dimension(250, 40));
                    sellPriceText.setBounds(750, 200, 100, 30);
                    contentPane.add(sellPriceText);
                    //set expiry label
                    JLabel expiryDate = new JLabel("<html>Expiry Date (YYYY-MM-DD)<br>no expiry date type null</html>");
                    expiryDate.setHorizontalAlignment(SwingConstants.LEFT);
                    expiryDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    expiryDate.setBounds(550, 240, 200, 50);
                    contentPane.add(expiryDate);
                    //set branch textField
                    JTextField expiryDateText = new JTextField();
                    expiryDateText.setPreferredSize(new Dimension(250, 40));
                    expiryDateText.setBounds(750, 250, 100, 30);
                    contentPane.add(expiryDateText);
                    //set damage label
                    JLabel damageLabel = new JLabel("<html>Damage Type<br>If item has no damage type null</html>");
                    damageLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    damageLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    damageLabel.setBounds(550, 290, 200, 50);
                    contentPane.add(damageLabel);
                    //set damage textField
                    JTextField damageText = new JTextField();
                    damageText.setPreferredSize(new Dimension(250, 40));
                    damageText.setBounds(750, 300, 100, 30);
                    contentPane.add(damageText);
                    //set location label
                    JLabel locationLabel = new JLabel("Location");
                    locationLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    locationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    locationLabel.setBounds(550, 350, 200, 30);
                    contentPane.add(locationLabel);
                    //set location comboBox
                    String[] loc = {"Store", "Warehouse"};
                    JComboBox<String> locationText = new JComboBox<>(loc);
                    locationText.setPreferredSize(new Dimension(250, 40));
                    locationText.setBounds(750, 350, 100, 30);
                    locationText.setBackground(SystemColor.white);
                    contentPane.add(locationText);
                    //set how many label
                    JLabel howManyLabel = new JLabel("Quantity");
                    howManyLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    howManyLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    howManyLabel.setBounds(550, 400, 200, 30);
                    contentPane.add(howManyLabel);
                    //set h0w many textField
                    JTextField howManyText = new JTextField();
                    howManyText.setPreferredSize(new Dimension(250, 40));
                    howManyText.setBounds(750, 400, 100, 30);
                    contentPane.add(howManyText);
                    //set item add button
                    JButton addButton = new JButton("Add");
                    addButton.setBounds(500, 450, 135, 33);
                    contentPane.add(addButton);
                    validate();
                    repaint();
                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String categories = catsText.getText();
                            String[] cats = categories.split(",");
                            Map<Integer, String> catList = new HashMap<>();
                            for (String cat : cats) {
                                String[] splited = cat.split("-");
                                catList.put(Integer.valueOf(splited[0]), splited[1]);
                            }
                            imanage.addItemIfBNotExist(Integer.parseInt(itemCNText.getText()), nameText.getText(), manuText.getText(), Integer.parseInt(costPriceText.getText()), catList);
                            imanage.addItemIfInNotExist(Integer.parseInt(itemCNText.getText()), Integer.parseInt(MinimumText.getText()), Double.parseDouble(sellPriceText.getText()));
                            String loct;
                            if (locationText.getSelectedItem() == "Store") {
                                loct = "S";
                            } else {
                                loct = "W";
                            }
                            LocalDate expired;
                            if (Objects.equals(expiryDateText.getText(), "null"))
                            {
                                expired = null;
                            }
                            else
                            {
                                expired = LocalDate.parse(expiryDateText.getText());
                            }
                            imanage.addItemIfExist(Integer.parseInt(howManyText.getText()), Integer.parseInt(itemCNText.getText()), expired, damageText.getText(), loct);
                            JOptionPane.showMessageDialog(getComponent(0), "New Item Added", "Successful add", JOptionPane.PLAIN_MESSAGE);
                            minimumLabel.setVisible(false);
                            MinimumText.setVisible(false);
                            damageLabel.setVisible(false);
                            damageText.setVisible(false);
                            expiryDate.setVisible(false);
                            expiryDateText.setVisible(false);
                            howManyText.setVisible(false);
                            howManyLabel.setVisible(false);
                            sellPriceLabel.setVisible(false);
                            sellPriceText.setVisible(false);
                            locationLabel.setVisible(false);
                            locationText.setVisible(false);
                            catsText.setVisible(false);
                            catsLabel.setVisible(false);
                            manuNameLabel.setVisible(false);
                            manuText.setVisible(false);
                            nameText.setVisible(false);
                            itemName.setVisible(false);
                            costPrice.setVisible(false);
                            costPriceText.setVisible(false);
                            addButton.setVisible(false);
                        }
                    });
                }
                //only basic exist
                if (imanage.basicExist(Integer.parseInt(itemCNText.getText())) && !imanage.inviExist(Integer.parseInt(itemCNText.getText()))) {
                    //set Minimun label
                    JLabel minimumLabel = new JLabel("Minimum Amount");
                    minimumLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    minimumLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    minimumLabel.setBounds(150, 400, 200, 50);
                    contentPane.add(minimumLabel);
                    //set minimum textField
                    JTextField MinimumText = new JTextField();
                    MinimumText.setPreferredSize(new Dimension(250, 40));
                    MinimumText.setBounds(350, 400, 100, 30);
                    contentPane.add(MinimumText);
                    //set sell price label
                    JLabel sellPriceLabel = new JLabel("Sell Price");
                    sellPriceLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    sellPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    sellPriceLabel.setBounds(550, 200, 200, 50);
                    contentPane.add(sellPriceLabel);
                    //set sell price textField
                    JTextField sellPriceText = new JTextField();
                    sellPriceText.setPreferredSize(new Dimension(250, 40));
                    sellPriceText.setBounds(750, 200, 100, 30);
                    contentPane.add(sellPriceText);
                    //set expiry label
                    JLabel expiryDate = new JLabel("<html>Expiry Date (YYYY-MM-DD)<br>no expiry date type null</html>");
                    expiryDate.setHorizontalAlignment(SwingConstants.LEFT);
                    expiryDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    expiryDate.setBounds(550, 240, 200, 50);
                    contentPane.add(expiryDate);
                    //set branch textField
                    JTextField expiryDateText = new JTextField();
                    expiryDateText.setPreferredSize(new Dimension(250, 40));
                    expiryDateText.setBounds(750, 250, 100, 30);
                    contentPane.add(expiryDateText);
                    //set damage label
                    JLabel damageLabel = new JLabel("<html>Damage Type<br>If item has no damage type null</html>");
                    damageLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    damageLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    damageLabel.setBounds(550, 290, 200, 50);
                    contentPane.add(damageLabel);
                    //set damage textField
                    JTextField damageText = new JTextField();
                    damageText.setPreferredSize(new Dimension(250, 40));
                    damageText.setBounds(750, 300, 100, 30);
                    contentPane.add(damageText);
                    //set location label
                    JLabel locationLabel = new JLabel("Location");
                    locationLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    locationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    locationLabel.setBounds(550, 350, 200, 30);
                    contentPane.add(locationLabel);
                    //set location comboBox
                    String[] loc = {"Store", "Warehouse"};
                    JComboBox<String> locationText = new JComboBox<>(loc);
                    locationText.setPreferredSize(new Dimension(250, 40));
                    locationText.setBounds(750, 350, 100, 30);
                    locationText.setBackground(SystemColor.white);
                    contentPane.add(locationText);
                    //set how many label
                    JLabel howManyLabel = new JLabel("Quantity");
                    howManyLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    howManyLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    howManyLabel.setBounds(550, 400, 200, 30);
                    contentPane.add(howManyLabel);
                    //set h0w many textField
                    JTextField howManyText = new JTextField();
                    howManyText.setPreferredSize(new Dimension(250, 40));
                    howManyText.setBounds(750, 400, 100, 30);
                    contentPane.add(howManyText);
                    //set item add button
                    JButton addButton = new JButton("Add");
                    addButton.setBounds(500, 450, 135, 33);
                    contentPane.add(addButton);
                    validate();
                    repaint();
                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            imanage.addItemIfInNotExist(Integer.parseInt(itemCNText.getText()), Integer.parseInt(MinimumText.getText()), Double.parseDouble(sellPriceText.getText()));
                            String loct;
                            if (locationText.getSelectedItem() == "Store") {
                                loct = "S";
                            } else {
                                loct = "W";
                            }
                            LocalDate expired;
                            if (Objects.equals(expiryDateText.getText(), "null"))
                            {
                                expired = null;
                            }
                            else
                            {
                                expired = LocalDate.parse(expiryDateText.getText());
                            }
                            imanage.addItemIfExist(Integer.parseInt(howManyText.getText()), Integer.parseInt(itemCNText.getText()), expired, damageText.getText(), loct);
                            JOptionPane.showMessageDialog(getComponent(0), "New Item Added", "Successful add", JOptionPane.PLAIN_MESSAGE);                            minimumLabel.setVisible(false);
                            MinimumText.setVisible(false);
                            damageLabel.setVisible(false);
                            damageText.setVisible(false);
                            expiryDate.setVisible(false);
                            expiryDateText.setVisible(false);
                            howManyText.setVisible(false);
                            howManyLabel.setVisible(false);
                            sellPriceLabel.setVisible(false);
                            sellPriceText.setVisible(false);
                            locationLabel.setVisible(false);
                            locationText.setVisible(false);
                            addButton.setVisible(false);
                        }
                    });
                }
                //both exist
                if (imanage.basicExist(Integer.parseInt(itemCNText.getText())) && imanage.inviExist(Integer.parseInt(itemCNText.getText()))) {
                    //set expiry label
                    JLabel expiryDate = new JLabel("<html>Expiry Date (YYYY-MM-DD)<br>no expiry date type null</html>");
                    expiryDate.setHorizontalAlignment(SwingConstants.LEFT);
                    expiryDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    expiryDate.setBounds(400, 240, 200, 50);
                    contentPane.add(expiryDate);
                    //set branch textField
                    JTextField expiryDateText = new JTextField();
                    expiryDateText.setPreferredSize(new Dimension(250, 40));
                    expiryDateText.setBounds(600, 250, 100, 30);
                    contentPane.add(expiryDateText);
                    //set damage label
                    JLabel damageLabel = new JLabel("<html>Damage Type<br>If item has no damage type null</html>");
                    damageLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    damageLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    damageLabel.setBounds(400, 290, 200, 50);
                    contentPane.add(damageLabel);
                    //set damage textField
                    JTextField damageText = new JTextField();
                    damageText.setPreferredSize(new Dimension(250, 40));
                    damageText.setBounds(600, 300, 100, 30);
                    contentPane.add(damageText);
                    //set location label
                    JLabel locationLabel = new JLabel("Location");
                    locationLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    locationLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    locationLabel.setBounds(400, 350, 200, 30);
                    contentPane.add(locationLabel);
                    //set location comboBox
                    String[] loc = {"Store", "Warehouse"};
                    JComboBox<String> locationText = new JComboBox<>(loc);
                    locationText.setPreferredSize(new Dimension(250, 40));
                    locationText.setBounds(600, 350, 100, 30);
                    locationText.setBackground(SystemColor.white);
                    contentPane.add(locationText);
                    //set how many label
                    JLabel howManyLabel = new JLabel("Quantity");
                    howManyLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    howManyLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
                    howManyLabel.setBounds(400, 400, 200, 30);
                    contentPane.add(howManyLabel);
                    //set h0w many textField
                    JTextField howManyText = new JTextField();
                    howManyText.setPreferredSize(new Dimension(250, 40));
                    howManyText.setBounds(600, 400, 100, 30);
                    contentPane.add(howManyText);
                    //set item add button
                    JButton addButton = new JButton("Add");
                    addButton.setBounds(500, 450, 135, 33);
                    contentPane.add(addButton);
                    validate();
                    repaint();
                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String loct;
                            if (locationText.getSelectedItem() == "Store") {
                                loct = "S";
                            } else {
                                loct = "W";
                            }
                            LocalDate expired;
                            if (Objects.equals(expiryDateText.getText(), "null"))
                            {
                                expired = null;
                            }
                            else
                            {
                                expired = LocalDate.parse(expiryDateText.getText());
                            }
                            imanage.addItemIfExist(Integer.parseInt(howManyText.getText()), Integer.parseInt(itemCNText.getText()), expired, damageText.getText(), loct);
                            JOptionPane.showMessageDialog(getComponent(0), "New Item Added", "Successful add", JOptionPane.PLAIN_MESSAGE);                            damageLabel.setVisible(false);
                            damageText.setVisible(false);
                            expiryDate.setVisible(false);
                            expiryDateText.setVisible(false);
                            howManyText.setVisible(false);
                            howManyLabel.setVisible(false);
                            locationLabel.setVisible(false);
                            locationText.setVisible(false);
                            addButton.setVisible(false);
                        }
                    });
                }
            }
        });
        contentPane.add(itemCNButton);
    }
}
