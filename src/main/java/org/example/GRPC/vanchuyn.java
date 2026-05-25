package org.example.GRPC;

import GRPC.ShippingQuoteAnswer;
import GRPC.TypedJudgeRequest;
import GRPC.TypedJudgeServiceGrpc;
import GRPC.TypedSubmitRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class vanchuyn {
    public static void main(String[] args) throws Exception{
        String studentCode ="B22DCCN905";
        String qCode ="wA46gGKN";
        ManagedChannel channel = ManagedChannelBuilder.forAddress("36.50.135.242",2240).usePlaintext().build();
        var stub = TypedJudgeServiceGrpc.newBlockingStub(channel);
        var res =stub.requestTyped(TypedJudgeRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).build());
        String requestId = res.getRequestId();
        var data = res.getShippingQuote();
        double weight_kg = data.getWeightKg();
        int maxeta = data.getMaxEtaDays();
        double minfee = Double.MAX_VALUE;
        double max_reli = -1;
        double totalFee = 0;
        System.out.println(data);
       int eta=0;
        String carrrier ="";
        for (var x: data.getQuotesList()){
            if(x.getEtaDays()< maxeta){
                totalFee= x.getBaseFee()+weight_kg*x.getPerKgFee();
                totalFee=Math.round(totalFee*100)/100.0;
                if(totalFee< minfee || totalFee == minfee && max_reli< x.getReliability()){
                    minfee =totalFee;
                    max_reli=x.getReliability();
                    carrrier = x.getCarrier();
                    eta =x.getEtaDays();
                }
            }
        }
        var answer = ShippingQuoteAnswer.newBuilder().setCarrier(carrrier).setEtaDays(eta).setTotalFee(minfee);
        var sub = stub.submitTyped(TypedSubmitRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).setRequestId(requestId).setShippingQuoteAnswer(answer).build());
        System.out.println(sub.getStatus());
    }
}
