package org.example.GRPC;

import GRPC.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.*;

public class tagticketGRPC {
    public static void main(String[] args) throws Exception{
        String studentCode ="B22DCCN905";
        String qCode ="BhwY9ctO";
        ManagedChannel channel = ManagedChannelBuilder.forAddress("36.50.135.242",2240).usePlaintext().build();
        var stub = TypedJudgeServiceGrpc.newBlockingStub(channel);
        var res = stub.requestTyped(TypedJudgeRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).build());
        var data = res.getTextBatch();
        String requestId =res.getRequestId();
        System.out.println(data);
        Map<String,Integer> cnt = new HashMap<>();
        String [] key ={"account","payment","refund","shipping"};
        for (String s: data.getEntriesList()){
            for (String x : key){
                if( s.contains(x)){
                    cnt.put(x,cnt.getOrDefault(x,0)+1);
                }
            }
        }
        List<String> values = new ArrayList<>(cnt.keySet());
        Collections.sort(values);
        var answer = TextBatchAnswer.newBuilder().addAllValues(values).putAllCounts(cnt).build();
        var sub = stub.submitTyped(TypedSubmitRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).setRequestId(requestId).setTextBatchAnswer(answer).build());
        System.out.println(sub.getStatus());
    }
}
