package org.example.SOAP;

import soap.DataService;
import soap.SoapDataService;

import java.util.List;

public class tinhtongsochan {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "rx4ycah0";

        DataService service = new DataService();
        SoapDataService port = service.getSoapDataServicePort();

        List<Integer> l = port.getData(msv, code);
        int sum = 0;
        for(Integer x : l){
            if(x%2==0){
                sum += x;
            }
        }
        port.submitDataInt(msv,code,sum);
    }
}
