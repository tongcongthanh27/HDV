package org.example.GRPC;

import GRPC.TextBatchAnswer;
import GRPC.TypedJudgeRequest;
import GRPC.TypedJudgeServiceGrpc;
import GRPC.TypedSubmitRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class log {
    public static void main(String[] args) throws Exception{
        String studentCode ="B22DCCN905";
        String qCode ="5VzgIqeY";
        ManagedChannel channel = ManagedChannelBuilder.forAddress("36.50.135.242",2240).usePlaintext().build();
        var stub = TypedJudgeServiceGrpc.newBlockingStub(channel);
        var res = stub.requestTyped(TypedJudgeRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).build());

        String requestId = res.getRequestId();
        var data = res.getTextBatch();
        System.out.println(data);
        String [] log = {"INFO", "WARN" , "ERROR"};
        Map<String,Integer> counts = new HashMap<>();
        String id="";
        int ok =0;
        for (String s : data.getEntriesList()){

            for(String x : log){
              if(s.contains(x)){
                  counts.put(x,counts.getOrDefault(x,0)+1);
              }
            }
            for(String i : s.split(" ")){
                if(i.startsWith("code")) {
                    if (ok == 0) {
                        id += i.substring(5);
                        ok=1;
                    }
                }
            }
        }
        System.out.println(id);
        List<String> values = new ArrayList<>();
        values.add(id);
        var answer = TextBatchAnswer.newBuilder().putAllCounts(counts).addAllValues(values).build();
        var sub = stub.submitTyped(TypedSubmitRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).setRequestId(requestId).setTextBatchAnswer(answer).build());
        System.out.println(sub.getStatus());
    }
}
