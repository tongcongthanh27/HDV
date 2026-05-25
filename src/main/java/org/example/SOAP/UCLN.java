package org.example.SOAP;

import soap.DataService;
import soap.SoapDataService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UCLN {
    public static int gcd(int a, int b){
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN905";
        String code = "xtJCdt78";

        DataService service = new DataService();
        SoapDataService port = service.getSoapDataServicePort();

        List<Integer> l = port.getData(msv, code);
        Collections.sort(l);
        List<Integer> l1 = new ArrayList<>();
        l1.add(l.get(0));
        l1.add(l.get(l.size()-1));
        l1.add(1);
        l1.add(l1.get(1)-l1.get(0));
        port.submitDataIntArray(msv,code,l1);
    }
}
