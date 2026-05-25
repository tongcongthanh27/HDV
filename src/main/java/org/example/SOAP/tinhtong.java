package org.example.SOAP;

import soap.DataService;
import soap.SoapDataService;

import java.util.List;

public class tinhtong {
    public static void main(String[] args) throws Exception {
        String msv = "B22DCCN797";
        String code = "iiwV1rpy";

        DataService service = new DataService();
        SoapDataService port = service.getSoapDataServicePort();

        List<Integer> l = port.getData(msv,code);
        int sum = 0;
        for(Integer x : l){
            sum += x;
        }
        port.submitDataInt(msv,code,sum);
    }
}
