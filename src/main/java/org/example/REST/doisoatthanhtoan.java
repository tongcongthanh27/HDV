package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class doisoatthanhtoan {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "M7XWjSho";

        ObjectMapper m = new ObjectMapper();

        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/data?studentCode=" + msv + "&qCode=" + code).openConnection();

        JsonNode r = m.readTree(c.getInputStream());

        System.out.println(r);
        double tong1 = 0;
        double tong2 =0;
        double tong3 =0;
        int dem =0;
        for(JsonNode i : r.get("data")){
            if(i.get("status").asText().equals("CAPTURED")){
                tong1 += i.get("amount").asDouble();
            }
            else if(i.get("status").asText().equals("REFUNDED")){
                tong2 += i.get("amount").asDouble();
            }
            else if (i.get("status").asText().equals("FAILED")){
                dem++;
            }
        }
        tong3 = tong1 - tong2;

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
                "answer", Map.of(
                                "capturedTotal", tong1,
                                "refundedTotal", tong2,
                                "netTotal", tong3,
                                "failedCount", dem
                        )
                )
        );

        System.out.println(m.readTree(p.getInputStream()));
    }
}
