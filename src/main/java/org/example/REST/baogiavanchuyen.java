package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class baogiavanchuyen {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "Ik1yeU7n";

        ObjectMapper m = new ObjectMapper();

        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/object?studentCode=" + msv +"&qCode=" + code).openConnection();

        JsonNode r = m.readTree(c.getInputStream());
        System.out.println(r);

        int maxEtaDays = r.get("data").get("maxEtaDays").asInt();
        double weightKg = r.get("data").get("weightKg").asDouble();

        double besttotalFee = Double.MAX_VALUE;
        String carrier = "";
        int etaDays = 0;
        double reali = 0;
        for (JsonNode i : r.get("data").get("quotes")){
            int check =0 ;
            if(i.get("etaDays").asInt()<=maxEtaDays){
                double totalFee = i.get("baseFee").asDouble() + weightKg*i.get("perKgFee").asDouble();

                if(totalFee < besttotalFee ){
                    check =1;
                }
                else if(totalFee == besttotalFee &&  reali > i.get("reliability").asDouble()){
                    check = 1;
                }

                if(check==1){
                    besttotalFee = totalFee;
                    carrier = i.get("carrier").asText();
                    etaDays = i.get("etaDays").asInt();
                    reali = i.get("reliability").asDouble();
                }
            }
        }

        HttpURLConnection p = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/object/submit").openConnection();

        p.setRequestMethod("POST");
        p.setDoOutput(true);
        m.writeValue(
                p.getOutputStream(),
                Map.of(
                        "studentCode", msv
                        ,"qCode",code,
                        "requestId",r.get("requestId"),
                        "answer", Map.of(
                                "carrier", carrier,
                                "totalFee",besttotalFee,
                                "etaDays",etaDays
                        )
                )
        );
        System.out.println(m.readTree(p.getInputStream()));
    }
}
