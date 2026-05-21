package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class object {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "6CRlyNHS";

        ObjectMapper m = new ObjectMapper();

        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/object?studentCode=" + msv +"&qCode=" + code).openConnection();

        JsonNode r = m.readTree(c.getInputStream());

        System.out.println(r);
        double tong = r.get("data").get("price").asDouble() * (1 + r.get("data").get("taxRate").asDouble()/100 )*(1 -r.get("data").get("discount").asDouble()/100 );

        HttpURLConnection p = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/object/submit").openConnection();

        p.setRequestMethod("POST");
        p.setDoOutput(true);

        m.writeValue(
                p.getOutputStream(),
                Map.of(
                        "studentCode",msv,
                "qCode", code,
                "requestId", r.get("requestId"),
                "answer", Map.of(
                        "name",r.get("data").get("name"),
                "price",r.get("data").get("price"),
                "taxRate",r.get("data").get("taxRate"),
                "discount",r.get("data").get("discount"),
                "finalPrice",tong
    )
                )
        );
        System.out.println(m.readTree(p.getInputStream()));
    }
}
