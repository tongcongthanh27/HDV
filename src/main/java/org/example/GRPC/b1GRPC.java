package org.example.GRPC;
import GRPC.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.*;
import java.io.*;

public class b1GRPC {
    public static void main(String[] args) throws Exception{
        String studentCode ="B22DCCN905";
        String qCode ="YkI9YgAQ";
        ManagedChannel channel = ManagedChannelBuilder.forAddress("36.50.135.242",2240).usePlaintext().build();
        var stub = JudgeServiceGrpc.newBlockingStub(channel);
        var res = stub.request(JudgeRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).build());
        System.out.println(res);
        String [] a = res.getData().split(",");
        int sum=0;
        for(String x : a){
            sum+= Integer.parseInt(x);
        }
        String answer =String.valueOf(sum);
        System.out.println(answer);
        var submit = stub.submit(SubmitRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).setRequestId(res.getRequestId()).setAnswer(answer).build());
        System.out.println(submit.getStatus());
        System.out.println(submit.getMessage());
    }
}
