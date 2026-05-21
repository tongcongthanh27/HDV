package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.*;
import java.util.Map;

public class putauditfields {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "Ron5a5BG";

        ObjectMapper m = new ObjectMapper();

        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/method?studentCode=" + msv +"&qCode=" + code).openConnection();

        JsonNode r = m.readTree(c.getInputStream());


        HttpURLConnection c1 = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/method/" + r.get("requestId").asText()).openConnection();

        c1.setRequestMethod("PUT");
        c1.setDoOutput(true);

        m.writeValue(
                c1.getOutputStream(),
                Map.of(
                        "studentCode", msv,
                "qCode", code,
                        "answer", Map.of(
                                "status", "ACTIVE",
                "activatedBy", "B22DCCN797",
                "auditNote", "manual-review-ok"
                        )
                )
        );
        System.out.println(m.readTree(c1.getInputStream()));
    }
}
