package org.example.GRPC;
import java.util.*;
import java.io.*;
import GRPC.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class b3GRPC {
    public static void main(String[] args) throws Exception{
        String studentCode = "B22DCCN905";
        String qCode ="W8rXBuQm";
        ManagedChannel channel = ManagedChannelBuilder.forAddress("36.50.135.242",2240).usePlaintext().build();
        var stub = JudgeServiceGrpc.newBlockingStub(channel);
        var res = stub.request(JudgeRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).build());
        System.out.println(res);
        ObjectMapper m = new ObjectMapper();
        JsonNode r = m.readTree(res.getData());
        System.out.println(r);
        String name = r.get("name").asText();
        double price = r.get("price").asDouble();
        double taxRate = r.get("taxRate").asDouble();
        double discount =r.get("discount").asDouble();
        double finalPrice=price * (1 + taxRate / 100) - discount;
        String answer = String.format("%.2f",finalPrice);
        var submit = stub.submit(SubmitRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).setRequestId(res.getRequestId()).setAnswer(answer).build());
        System.out.println(submit.getStatus());
    }
}
