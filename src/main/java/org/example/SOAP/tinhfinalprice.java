package org.example.SOAP;

import soap.ObjectService;
import soap.ProductY;
import soap.SoapObjectService;

public class tinhfinalprice {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "IFksDhgy";

        ObjectService service = new ObjectService();
        SoapObjectService port = service.getSoapObjectServicePort();

        ProductY y = port.requestProductY(msv, code);
         y.setFinalPrice(y.getPrice() * (1+y.getTaxRate()/100)*(1-y.getDiscount()/100));
         port.submitProductY(msv, code, y);
    }
}
