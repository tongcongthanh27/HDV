package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.*;

public class pathqueryvalidation {
    public static void main(String[] args) throws Exception {
        String msv = "B22DCCN797";
        String code = "kjkacsvm";

        ObjectMapper m = new ObjectMapper();


        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/path?studentCode=" + msv +"&qCode=" + code).openConnection();

        JsonNode r = m.readTree(c.getInputStream());
        System.out.println(r);


        HttpURLConnection c1 = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/path/" + 1 + "?studentCode=" + msv +"&qCode=" + code).openConnection();
    }
}
