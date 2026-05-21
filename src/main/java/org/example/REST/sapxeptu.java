package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class sapxeptu {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "5VJNjShP";

        ObjectMapper m = new ObjectMapper();

        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/character?studentCode=" + msv + "&qCode=" + code).openConnection();

        JsonNode r = m.readTree(c.getInputStream());

        System.out.println(r);

        List<String> l = new ArrayList<>();
        String x = r.get("data").asText();
        System.out.println(x);
        String x1[] = x.split("\\s+");
        for(String y : x1){
            l.add(y);
        }
        Collections.sort(l);
        String res ="";
        for(String y : l){
            res = res + y + " ";
        }
        res = res.trim();
        HttpURLConnection p = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/character/submit").openConnection();

        p.setRequestMethod("POST");
        p.setDoOutput(true);

        m.writeValue(
                p.getOutputStream(),
                Map.of(
                        "studentCode", msv,
                "qCode", code,
                "requestId", r.get("requestId"),
                "answer",     res
                )
        );
        System.out.println(m.readTree(p.getInputStream()));
    }
}
