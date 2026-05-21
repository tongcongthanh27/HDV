package org.example.SOAP;

import soap.CharacterService;
import soap.SoapCharacterService;

public class character {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "dObgU3op";

        CharacterService service = new CharacterService();
        SoapCharacterService port = service.getSoapCharacterServicePort();

        String x = port.requestString("B22DCCN797", "dObgU3op");
        System.out.println(x);

        String res = "";
        for(int i = x.length()-1 ; i>=0; i--){
            res = res + x.charAt(i);
        }
        port.submitString("B22DCCN797", "dObgU3op", res);
    }

}
