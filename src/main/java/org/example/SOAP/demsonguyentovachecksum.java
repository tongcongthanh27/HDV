package org.example.SOAP;

import soap.DataService;
import soap.SoapDataService;

import java.util.ArrayList;
import java.util.List;

public class demsonguyentovachecksum {
    public static int snt(Integer x){
        if (x < 2) return 0;
        if (x == 2 || x ==3) return 1;
        for(int i = 2; i <=Math.sqrt(x);i++){
            if (x%i==0) return 0;
        }
        return 1;
    }
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN905";
        String code = "UQhPthKI";

        DataService service = new DataService();
        SoapDataService port = service.getSoapDataServicePort();

        List<Integer> l = port.getData(msv, code);
        int dem1 = 0;
        int sum = 0;
        for (Integer x : l){
            if(snt(x)==1){
                dem1++;
            }
        }
        for (int i = 0 ; i < l.size();i++){
            sum+= (i+1)*l.get(i);
        }
        sum = sum%100000;
        String x = "primeCount=" + String.valueOf(dem1) + ";checksum=" + String.valueOf(sum);
        port.submitDataString(msv,code, x);
    }
}
