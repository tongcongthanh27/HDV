package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class chuky {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "DMxV8TSW";

        ObjectMapper m = new ObjectMapper();

        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/header?studentCode="+msv +"&qCode="+code).openConnection();

        JsonNode r = m.readTree(c.getInputStream());
        System.out.println(r);
        String payload = r.get("data").get("nonce").asText() + ":";
        String signingKey = r.get("data").get("signingKey").asText();
        for (JsonNode i : r.get("data").get("events")){
            payload = payload + i.asText() + "|";
        }
        payload = payload.substring(0, payload.length()-1) + ":B22DCCN797";
        System.out.println(payload);
        System.out.println(signingKey);
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(signingKey.getBytes(), "HmacSHA256"));

        String sign = "";
        for(byte b : mac.doFinal(payload.getBytes()))
            sign += String.format("%02x", b);

        System.out.println(sign);
        HttpURLConnection p = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/header/submit").openConnection();

        p.setRequestMethod("POST");
        p.setRequestProperty("X-Signature", sign);
        p.setDoOutput(true);
        m.writeValue(
                p.getOutputStream(),
                Map.of(
                        "studentCode",msv,
                        "qCode", code,
                        "requestId",r.get("requestId")
                )
        );
        System.out.println(m.readTree(p.getInputStream()));
    }
}
