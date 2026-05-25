package org.example.GRPC;
import java.util.*;
import java.io.*;
import GRPC.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class b2GRPC {
    public static void main(String[] args) throws Exception{
            String studentCode ="B22DCCN905";
            String qCode ="mQNTrzYh";
            ManagedChannel channel = ManagedChannelBuilder.forAddress("36.50.135.242",2240).usePlaintext().build();
            var stub = JudgeServiceGrpc.newBlockingStub(channel);
            var request = stub.request(JudgeRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).build());
            System.out.println(request);
            String [] a = request.getData().split(",");
            List<String> b = new ArrayList<>();
            for (String x: a){
                b.add(x);
            }
            Collections.sort(b);
            String answer = b.toString();
           /* String answer ="";
            for (String x:b){
              answer+=x+",";
            }
            answer=answer.substring(0,answer.length()-1);
        System.out.println(answer);*/
        var submit = stub.submit(SubmitRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).setRequestId(request.getRequestId()).setAnswer(answer).build());
        System.out.println(submit.getStatus());
    }
}
