package org.example.SOAP;

import soap.CharacterService;
import soap.SoapCharacterService;

import java.util.*;

public class top2cumtu {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN905";
        String code = "zkMrfloS";

        CharacterService service = new CharacterService();
        SoapCharacterService port = service.getSoapCharacterServicePort();

        String res = port.requestString(msv , code);
        System.out.println(res);

        res = res.substring(0, res.length()-1);
        System.out.println(res);

        String ans = "";
        String[] x = res.split("\\s+");
        Map<String,Integer> map = new HashMap<>();

        for(int i = 0; i < x.length - 1; i++){

            String x1 = x[i] + "_" + x[i+1];

            map.put(x1, map.getOrDefault(x1,0) + 1);
        }

        // sort theo tần suất giảm dần
        List<Map.Entry<String,Integer>> l =
                new ArrayList<>(map.entrySet());

        l.sort((x1,y) -> {
            if(y.getValue() != x1.getValue())
                return y.getValue() - x1.getValue();

            return x1.getKey().compareTo(y.getKey());
        });

        // lấy tối đa 3 cụm
        String res1 = "";

        for(int i = 0; i < Math.min(3, l.size()); i++){

            res1 += l.get(i).getKey()
                    + "="
                    + l.get(i).getValue()
                    + "|";
        }

        res1= res1.substring(0, res1.length()-1);

        System.out.println(res1);

        port.submitString(msv, code, res1);

    }
}
