package org.example.SOAP;

import soap.DataService;
import soap.SoapDataService;

import java.util.List;

public class soapdata {
    public static void main(String[] args) throws Exception{

        DataService data = new DataService();
        SoapDataService port = data.getSoapDataServicePort();

        List<Integer> l = port.getData("B22DCCN797", "E3t6IAMM");
        int tong =0;
        for (Integer x : l){
            tong += x;
        }
        port.submitDataInt("B22DCCN797", "E3t6IAMM", tong);

    }
}
