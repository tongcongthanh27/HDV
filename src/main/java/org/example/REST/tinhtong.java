package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.print.attribute.HashDocAttributeSet;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class tinhtong {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "dJaFbvRH";

        ObjectMapper m = new ObjectMapper();

        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/data?studentCode=" + msv + "&qCode=" + code).openConnection();

        JsonNode r = m.readTree(c.getInputStream());

        System.out.println(r);
        long tong =0;
        for(JsonNode i : r.get("data")){
            tong += i.asInt();
        }

        HttpURLConnection p = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/data/submit").openConnection();

        p.setRequestMethod("POST");
        p.setDoOutput(true);

        m.writeValue(
                p.getOutputStream(),
                Map.of(
                        "studentCode", msv,
                "qCode", code,
                "requestId", r.get("requestId"),
                "answer", tong
                )
        );

        System.out.println(m.readTree(p.getInputStream()));
    }
}
