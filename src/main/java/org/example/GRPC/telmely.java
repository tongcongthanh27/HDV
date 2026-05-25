package org.example.GRPC;

import GRPC.SensorTelemetryAnswer;
import GRPC.TypedJudgeRequest;
import GRPC.TypedJudgeServiceGrpc;
import GRPC.TypedSubmitRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class telmely {
    public static void main(String[] args) throws Exception{
        String studentCode ="B22DCCN905";
        String qCode ="RQBExaJc";
        ManagedChannel channel = ManagedChannelBuilder.forAddress("36.50.135.242",2240).usePlaintext().build();
        var stub = TypedJudgeServiceGrpc.newBlockingStub(channel);
        var res = stub.requestTyped(TypedJudgeRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).build());
        var data = res.getSensorTelemetry();
        System.out.println(data);
        String requestId = res.getRequestId();
        double thresold = data.getThreshold();
        double avg = 0;
        double p95=0;
        int anomaly_count=0;
        List<Double> a = new ArrayList<>();
        for( var x : data.getReadingsList()){
            avg+=x.getValue();
            a.add( x.getValue());
            if(x.getValue()>thresold){
                anomaly_count++;
            }

        }
        avg = Math.round((avg/data.getReadingsCount())*100)/100.0;
        System.out.println(avg);
        double index = Math.ceil(data.getReadingsCount()*0.95)-1;
        Collections.sort(a);
        p95=a.get((int) index);
        p95=Math.round(p95*100)/100.0;
        var answer = SensorTelemetryAnswer.newBuilder().setAverage(avg).setP95(p95).setAnomalyCount(anomaly_count).build();
        var sub = stub.submitTyped(TypedSubmitRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).setRequestId(requestId).setSensorTelemetryAnswer(answer).build());
        System.out.println(sub.getStatus());
    }
}
