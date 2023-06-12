package DataAccessLayer.SuppliersModule;

import BusinessLayer.SuppliersModule.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.*;

public class ContractDAO {
    private static ContractDAO instance;
    private Connection conn;
    private Map<String, Contract> identityMap; //string =
    PreparedStatement stmt;
    private int int_supplier_id;

    private ContractDAO() throws SQLException {
        this.identityMap = new HashMap<>();
        this.conn=Database.getDataBaseInstance().getConnection();
    }

    public static ContractDAO getContractInstance() {
        if (instance == null) {
            try {
                instance = new ContractDAO();
            }
            catch (SQLException e) {
                System.out.println("An error occurred");
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
        return instance;
    }

    public void add(Contract contract)
    {
        Supplier supplier=null;
        String supplier_id="";
        Map<Integer,String> catalogNumber_barcode=new HashMap<>();
        if (contract!=null)
        {
            supplier_id=contract.getProducts().entrySet().iterator().next().getValue().getSupplierID();
            if (identityMap.containsKey(supplier_id))
                return;
            identityMap.put(supplier_id,contract);
        }
        //products list
        for (Map.Entry<String, SupplierProduct> entry : contract.getProducts().entrySet())
        {
            SupplierProduct supplierProduct = entry.getValue();
            SupplierProductDAO supplierProductDAO = SupplierProductDAO.getSupplierProductInstance();
            supplierProductDAO.add(supplierProduct);
            catalogNumber_barcode.put(entry.getValue().getCatalog_number(),entry.getKey());
        }
        //quantity bill
        DiscountDAO discountDAO = DiscountDAO.getDiscountInstance();
        for (Map.Entry<Integer, List<Discount>> entry : contract.getQuantities_bill().entrySet())
        {
            // add each discount to discount table
            for (int i=0;i<entry.getValue().size();i++)
            {
                discountDAO.add(entry.getValue().get(i),catalogNumber_barcode.get(entry.getKey()),supplier_id);
            }
        }
    }

    public void delete(Contract contract)
    {
        Supplier supplier;
        String supplier_id="";
        Map<Integer,String> catalogNumber_barcode=new HashMap<>();
        if (contract!=null) {
            supplier_id = contract.getProducts().entrySet().iterator().next().getValue().getSupplierID();

        }
        //delete contract
        SupplierProductDAO supplierProductDAO = SupplierProductDAO.getSupplierProductInstance();
        supplierProductDAO.deleteSupplierProductsBySupplierID(supplier_id);
        //delete quantities bill
        DiscountDAO discountDAO = DiscountDAO.getDiscountInstance();
        for (Map.Entry<String, SupplierProduct> entry : contract.getProducts().entrySet())
        {
            catalogNumber_barcode.put(entry.getValue().getCatalog_number(),entry.getKey());
        }
        for (Map.Entry<Integer, List<Discount>> entry : contract.getQuantities_bill().entrySet())
        {
            // remove each discount from discount table
            for(int i=0;i<entry.getValue().size();i++)
                discountDAO.RemoveDiscountByDiscountID(entry.getValue().get(i).getDiscount_id());
        }
        //remove from identity map
        identityMap.remove(contract);
    }

    public Contract getSupplierContractBySupplierID(String supplierID)
    {
        Map<String,SupplierProduct> products = new HashMap<>(); // barcoode, supplier product
        Map<java.lang.Integer,List<Discount>> quantities_bill; // catalogNumber, discount list
        Contract contract = new Contract();
        SupplierProductDAO supplierProductDAO=SupplierProductDAO.getSupplierProductInstance();
        List<SupplierProduct> supplierProducts = supplierProductDAO.getSupplierProductsBySupplierID(supplierID);
        for(int i=0;i<supplierProducts.size();i++)
        {
            products.put(supplierProducts.get(i).getSupermarket_id(),supplierProducts.get(i));
        }
        DiscountDAO discountDAO = DiscountDAO.getDiscountInstance();
        quantities_bill=discountDAO.getDiscountsMapBySupplierID(supplierID);
        contract.setProducts(products);
        contract.setQuantities_bill(quantities_bill);
        return contract;
    }

    public void update(Contract contract)
    {
        for (Map.Entry<String, SupplierProduct> entry : contract.getProducts().entrySet())
        {
            SupplierProductDAO supplierProductDAO = SupplierProductDAO.getSupplierProductInstance();
            supplierProductDAO.update(entry.getValue());
        }
    }

    public void deleteProductFromContract(Contract contract,SupplierProduct supplierProduct)
    {
        SupplierProductDAO supplierProductDAO = SupplierProductDAO.getSupplierProductInstance();
        supplierProductDAO.delete(supplierProduct.getSupplierID(),supplierProduct.getSupermarket_id());
        identityMap.put(supplierProduct.getSupplierID(),contract);
    }

    public void addProductToContract(Contract contract, SupplierProduct supplierProduct)
    {

        String supplierID=contract.getProducts().entrySet().iterator().next().getValue().getSupplierID();
        SupplierProductDAO supplierProductDAO = SupplierProductDAO.getSupplierProductInstance();
        supplierProductDAO.add(supplierProduct);
        if (identityMap.containsKey(supplierID))
            identityMap.get(supplierID).addTo_contract(supplierProduct.getSupermarket_id(),supplierProduct);
        else
        {
            contract.addTo_contract(supplierProduct.getSupermarket_id(),supplierProduct);
            identityMap.put(supplierID,contract);
        }
    }

    public void addDiscountToQuantitiesBill(Contract contract,Discount discount,String supplierID,String barcode)
    {
            int catalogNumber=discount.getCatalog_number();
            DiscountDAO discountDAO = DiscountDAO.getDiscountInstance();
            discountDAO.add(discount,barcode,supplierID);
            if(identityMap.containsKey(supplierID))
                identityMap.get(supplierID).addTo_QuantitiesBill(barcode,catalogNumber,discount);
            else
            {
                contract.addTo_QuantitiesBill(barcode,catalogNumber,discount);
                identityMap.put(supplierID,contract);
            }
        }

    public void deleteDiscountFromQuantitiesBill(String discountID)
    {
        DiscountDAO discountDAO=DiscountDAO.getDiscountInstance();
        discountDAO.RemoveDiscountByDiscountID(discountID);
    }

}