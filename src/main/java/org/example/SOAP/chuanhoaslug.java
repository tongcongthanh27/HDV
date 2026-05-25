package org.example.SOAP;

import soap.CharacterService;
import soap.SoapCharacterService;

import java.util.Locale;

public class chuanhoaslug {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN905";
        String code = "hSHWzAC7";

        CharacterService service = new CharacterService();
        SoapCharacterService port = service.getSoapCharacterServicePort();

        String x = port.requestString(msv , code);
        x = x.toLowerCase();
        String[] x1 = x.trim().split("\\s+");
        String res = "";
        for(int i=0;i<x1.length;i++){
            res = res + x1[i] + "-";
        }
        res = res.substring(0, res.length()-2);
        port.submitString(msv, code , res);
    }
}
