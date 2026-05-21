package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.*;

public class phantrangvalockhachhang {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "sy9Mv1d3";

        ObjectMapper m = new ObjectMapper();


        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/path?studentCode=" + msv +"&qCode=" + code).openConnection();

        JsonNode r = m.readTree(c.getInputStream());
        System.out.println(r);
        int overdueAmount = Integer.MIN_VALUE;
        String cus = "";
        int page =0;

        for(JsonNode i : r.get("data")){
            if(i.get("status").asText().equals("OVERDUE")){
                int max = i.get("overdueAmount").asInt();
                if(max > overdueAmount){
                    overdueAmount = max;
                    cus = i.get("customerId").asText();
                    page = i.get("page").asInt();
                }
            }
        }

        HttpURLConnection c1 = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/path/" + cus + "?studentCode=" + msv +"&qCode=" + code + "&requestId=" + r.get("requestId").asText() + "&status=OVERDUE&page=" + page ).openConnection();

        System.out.println(m.readTree(c1.getInputStream()));
    }
}
