package org.example.SOAP;

import soap.CharacterService;
import soap.SoapCharacterService;

import java.util.ArrayList;
import java.util.List;

public class chelog2 {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN905";
        String code = "s9x6JVGv";

        CharacterService service = new CharacterService();
        SoapCharacterService port = service.getSoapCharacterServicePort();

        List<String> l = port.requestStringArray(msv, code);
        System.out.println(l);
        List<String> l1  = new ArrayList<>();
        for (String x : l){
            String[] x1 = x.trim().split("\\s+");
            String res = "";
            for (String y : x1){
                if(y.charAt(0) == 'e'){
                    res = res + "email=[EMAIL] ";
                }
                else if(y.charAt(0) == 'p'){
                    res = res + "phone=[PHONE] ";
                }
                else if(y.charAt(0) == 't'){
                    res = res + "token=[TOKEN] ";
                }
                else {
                    res = res + y + " ";
                }
            }
            res = res.trim();
            l1.add(res);
        }
        List<String> l2 = new ArrayList<>();
        for (String x : l1){
            String[] x1 = x.split("\\s+");
            if(x1[0].equals("ERROR")){
                l2.add(x);
            }
        }
        for (String x : l1){
            String[] x1 = x.split("\\s+");
            if(x1[0].equals("WARN")){
                l2.add(x);
            }
        }
        for (String x : l1){
            String[] x1 = x.split("\\s+");
            if(x1[0].equals("INFO")){
                l2.add(x);
            }
        }
        port.submitStringArray(msv, code , l2);
    }
}
