package org.example.REST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class chedulieunhaycamtronglog {
    public static void main(String[] args) throws Exception{
        String msv = "B22DCCN797";
        String code = "1KlECeob";

        ObjectMapper m = new ObjectMapper();

        HttpURLConnection c = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/character?studentCode=" + msv + "&qCode=" + code ).openConnection();

        JsonNode r = m.readTree(c.getInputStream());
        System.out.println(r);
        String x = r.get("data").asText();
        String[] x1 = x.trim().split("\\s+");
        String res = "";
        for(String y : x1){
            if(y.charAt(0) == 'u'){
                res = res + "user=[EMAIL] ";
            }
            else if (y.charAt(0) == 'p'){
                res = res + "phone=[PHONE] ";
            }
            else if(y.charAt(0) == 't'){
                res = res + "token=[TOKEN] ";
            }
            else{
                res = res + y + " ";
            }
        }

        HttpURLConnection p = (HttpURLConnection)
                new URL("http://36.50.135.242:2230/api/rest/character/submit").openConnection();

        p.setRequestMethod("POST");
        p.setDoOutput(true);

        m.writeValue(
                p.getOutputStream(),
                Map.of(
                        "studentCode",msv
                        ,"qCode", code
                        ,"requestId", r.get("requestId"),
                        "answer",res
                )
        );

        System.out.println(m.readTree(p.getInputStream()));

    }
}
