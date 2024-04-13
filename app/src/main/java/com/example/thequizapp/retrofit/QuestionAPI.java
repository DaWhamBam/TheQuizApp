package com.example.thequizapp.retrofit;

import com.example.thequizapp.model.QuestionList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionAPI {

    @GET("myquiyapi.php") // end point
    Call<QuestionList> getQuestions();

}
