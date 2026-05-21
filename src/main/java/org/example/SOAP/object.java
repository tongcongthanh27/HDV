package org.example.SOAP;

import com.fasterxml.jackson.databind.ObjectMapper;
import soap.ObjectService;
import soap.ProductY;
import soap.SoapObjectService;

public class object {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "vwxWTG43";

        ObjectService service = new ObjectService();
        SoapObjectService port = service.getSoapObjectServicePort();

        ProductY y = port.requestProductY(msv, code);

        float finalprice = y.getPrice() * (1+y.getTaxRate()/100)*(1-y.getDiscount()/100);
        y.setFinalPrice(finalprice);

        port.submitProductY(msv, code ,y);

    }
}
