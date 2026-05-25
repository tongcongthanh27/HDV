package org.example.SOAP;

import soap.ObjectService;
import soap.ProductY;
import soap.SoapObjectService;

public class tinhfinal2 {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "IFksDhgy";

        ObjectService service = new ObjectService();
        SoapObjectService port = service.getSoapObjectServicePort();

        ProductY y = port.requestProductY(msv, code);
        float finalPrice =
                (float) (Math.round(
                                        (y.getPrice()*(1+y.getTaxRate()/100)-y.getDiscount())*100)/100.0);
        y.setFinalPrice( finalPrice);
        port.submitProductY(msv, code, y);
    }
}
