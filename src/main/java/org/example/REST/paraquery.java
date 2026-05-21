package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.*;

public class paraquery {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "u8d79FkG";

        ObjectMapper m = new ObjectMapper();

        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/path?studentCode=" + msv +"&qCode=" + code).openConnection();

        JsonNode r = m.readTree(c.getInputStream());
        System.out.println(r);
        int x = 1;

        HttpURLConnection c1 = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/path/" + x + "?studentCode="+msv +"&qCode="+ code + "&requestId=" + r.get("requestId").asText()+"&currency=USD").openConnection();
        System.out.println(m.readTree(c1.getInputStream()));
    }
}
