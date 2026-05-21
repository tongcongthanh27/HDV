package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.*;
import java.util.Map;

public class headerreplay {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "J4MFLAFl";

        ObjectMapper m = new ObjectMapper();


        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/header?studentCode=" + msv +"&qCode=" + code).openConnection();

        JsonNode r = m.readTree(c.getInputStream());
        String x = c.getHeaderField("X-Checksum");
        System.out.println(r);


        HttpURLConnection p = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/header/submit").openConnection();

        p.setRequestMethod("POST");
        p.setRequestProperty("X-Checksum", x);
        p.setDoOutput(true);

        m.writeValue(
                p.getOutputStream(),
                Map.of(
                        "studentCode",msv,
                "qCode", code,
                "requestId", r.get("requestId")
                )
        );
        System.out.println(m.readTree(p.getInputStream()));
    }
}
