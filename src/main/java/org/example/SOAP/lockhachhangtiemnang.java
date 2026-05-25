package org.example.SOAP;

import soap.Customer;
import soap.ObjectService;
import soap.SoapObjectService;

import java.util.ArrayList;
import java.util.List;

public class lockhachhangtiemnang {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN905";
        String code = "T3ARfIf5";

        ObjectService service = new ObjectService();
        SoapObjectService port = service.getSoapObjectServicePort();

        List<Customer> l = port.requestListCustomer(msv, code);
        List<Customer> l1 = new ArrayList<>();
        for (Customer x : l){
            if(x.getPurchaseCount() >=5 && x.getTotalSpent() > 5000){
                l1.add(x);
            }
        }
        port.submitListCustomer(msv , code ,  l1);
    }
}
