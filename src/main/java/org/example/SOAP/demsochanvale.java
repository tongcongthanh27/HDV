package org.example.SOAP;

import soap.DataService;
import soap.SoapDataService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class demsochanvale {
    public static void main(String[] args) throws Exception {
        String msv = "B222DCCN905";
        String code = "mj0G3mvm";

        DataService service = new DataService();
        SoapDataService port = service.getSoapDataServicePort();

        List<Integer> l = port.getData(msv,code);
        int dem1=0, dem2=0;
        for (Integer x: l){
            System.out.println(x);
            if(x%2==0){
                dem1++;
            }
            else{
                dem2++;
            }
        }
        List<String> l1 = new ArrayList<>();
        l1.add("EVEN=" + String.valueOf(dem1));
        l1.add("ODD=" + String.valueOf(dem2));
        port.submitDataStringArray(msv , code , l1);
    }
}
