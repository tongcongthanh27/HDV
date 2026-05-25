package org.example.SOAP;

import soap.Customer;
import soap.ObjectService;
import soap.SoapObjectService;

import java.util.ArrayList;
import java.util.List;

public class lockhachvip {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN905";
        String code = "JdjHVQXA";

        ObjectService service = new ObjectService();
        SoapObjectService port = service.getSoapObjectServicePort();

        List<Customer> c = port.requestListCustomer(msv, code);
        System.out.println(c);
        List<Customer> l = new ArrayList<>();

        for (Customer x : c){
            if (x.getTotalSpent() >= 4000 && x.getPurchaseCount() >=6){
                l.add(x);
            }
        }
        l.sort((a,b) -> {
            if (a.getTotalSpent() != b.getTotalSpent()){
                return Double.compare(b.getTotalSpent(), a.getTotalSpent());
            }
            return a.getCustomerId().compareTo(b.getCustomerId());
        });
        port.submitListCustomer(msv,code,l);
    }
}
