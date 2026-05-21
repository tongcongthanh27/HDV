package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class putmethod {
    public static void main(String[] args) throws Exception {
        String msv = "B22DCCN797";
        String code = "0ZeCZ65i";

        ObjectMapper m = new ObjectMapper();


        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/method?studentCode=" + msv +"&qCode=" + code).openConnection();

        JsonNode r = m.readTree(c.getInputStream());
        System.out.println(r);

        HttpURLConnection p= (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/method/" + r.get("requestId").asText() ).openConnection();

        p.setRequestMethod("PUT");
        p.setDoOutput(true);

        m.writeValue(
                p.getOutputStream(),
                Map.of(
                        "studentCode", msv,
                "qCode", code,
                        "answer", Map.of(
                                "status", "done"
                        )
                )
        );
        System.out.println(m.readTree(p.getInputStream()));

    }
}
