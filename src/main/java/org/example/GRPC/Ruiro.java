package org.example.GRPC;
import GRPC.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.*;
public class Ruiro {
    public static void main(String[] args) throws Exception{
        String studentCode = "B22DCCN905";
        String qCode ="IVfRtMAL";
        ManagedChannel channel = ManagedChannelBuilder.forAddress("36.50.135.242",2240).usePlaintext().build();
        var stub = TypedJudgeServiceGrpc.newBlockingStub(channel);
        var res = stub.requestTyped(TypedJudgeRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).build());
        System.out.println(res);
        String requestId = res.getRequestId();
        List<String>ids = new ArrayList<>();
        double total =0;
        var data = res.getTransactionRiskBatch();
        for(var x: data.getTransactionsList()){
            if(x.getAmount()>=5000 || x.getChargebackCount()>=2 || x.getNewDevice() && !x.getCountry().equals("VN")){
                ids.add(x.getTransactionId());
                total+=x.getAmount();

            }
        }
        total=Math.round(total*100)/100.0;
      var answer = TransactionRiskAnswer.newBuilder().addAllHighRiskTransactionIds(ids).setReviewCount(ids.size()).setTotalHighRiskAmount(total).build();
      var sub = stub.submitTyped(TypedSubmitRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).setRequestId(requestId).setTransactionRiskAnswer(answer).build());
        System.out.println(sub.getStatus());
    }
}
