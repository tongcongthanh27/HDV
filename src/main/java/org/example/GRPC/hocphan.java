package org.example.GRPC;

import GRPC.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class hocphan {
    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN905";
        String qCode = "K3eVdirE";
        ManagedChannel channel = ManagedChannelBuilder.forAddress("36.50.135.242", 2240).usePlaintext().build();
        var stub = TypedJudgeServiceGrpc.newBlockingStub(channel);
        var res = stub.requestTyped(TypedJudgeRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).build());
        String requestId = res.getRequestId();
        var data = res.getEnrollment();
        System.out.println(data);
        double gpa = 0;
        double min_gpa =0;
        double gpa_gap = Math.max(0,min_gpa);
        gpa_gap =Math.round(gpa_gap*100)/100.0;
        List<String> missing_course = new ArrayList<>();
        for(String x: data.getRequiredCoursesList()){
            for (String y : data.getCompletedCoursesList()){
                if(!y.equals(x)) missing_course.add(x);
            }
        }
        Collections.sort(missing_course);
        boolean eligible ;
        if(data.getCompletedCoursesCount() == data.getRequiredCoursesCount()) eligible=true;
        else eligible=false;
        var answer = EnrollmentAnswer.newBuilder().addAllMissingCourses(missing_course).setEligible(eligible).setGpaGap(gpa_gap);
        var sub = stub.submitTyped(TypedSubmitRequest.newBuilder().setStudentCode(studentCode).setQuestionAlias(qCode).setRequestId(requestId).setEnrollmentAnswer(answer).build());
        System.out.println(sub.getStatus());
    }
}
