package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static final String BASE_URL = "https://predictor.yandex.net";
    public static final String KEY = "pdct.1.1.20231215T152437Z.359f4ba5ba7bcc9f.6e3254ffd7e39d6d6316461f5dd5b095a707420e";
    public static final String LANG = "en";
    public static final Integer LIMIT = 5;
    private YandexAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(YandexAPI.class);
        binding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                doHttpRequest();
            }
        });
    }
    public void doHttpRequest() {
        api.getComplete(KEY,
                        binding.editText.getText().toString(),
                        LANG,
                        LIMIT).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()) {  // 200 Ok
                    List<String> result = response.body().getText();
                    binding.textView.setText(String.join("\n",result));
                }
            }
            @Override
            public void onFailure(Call<Response> call, Throwable throwable) {
                Log.d("RRR",throwable.toString());
            }
        });
    }
}