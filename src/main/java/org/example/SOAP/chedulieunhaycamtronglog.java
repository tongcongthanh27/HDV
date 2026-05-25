package org.example.SOAP;

import soap.CharacterService;
import soap.SoapCharacterService;

import java.util.ArrayList;
import java.util.List;

public class chedulieunhaycamtronglog {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN905";
        String code = "WMib61Kn";

        CharacterService service = new CharacterService();
        SoapCharacterService port = service.getSoapCharacterServicePort();
        List<String> l = port.requestStringArray(msv, code);
        List<String> l1 = new ArrayList<>();
        System.out.println(l);
        for (String x : l){
            String[] x1 = x.trim().split("\\s+");
            String res = "";
            for (String y : x1){
                if(y.charAt(0) == 'e'){
                    res = res + "email=[EMAIL]" + " ";
                }
                else if(y.charAt(0) == 'p'){
                    res = res + "phone=[PHONE]" + " ";
                }
                else if(y.charAt(0) == 't'){
                    res = res + "token=[TOKEN]" + " ";
                }
                else {
                    res = res + y + " ";
                }


            }
            res = res.trim();
            l1.add(res);
        }
        port.submitStringArray(msv, code , l1);
    }
}
