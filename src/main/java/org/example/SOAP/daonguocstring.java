package org.example.SOAP;

import soap.CharacterService;
import soap.SoapCharacterService;

public class daonguocstring {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "TNuZAqhp";

        CharacterService service = new CharacterService();
        SoapCharacterService port = service.getSoapCharacterServicePort();

        String x = port.requestString(msv, code);
        String res = "";
        for (int i = x.length()-1;i>=0;i--){
            res = res + x.charAt(i);
        }
        port.submitString(msv,code,res);
    }
}
