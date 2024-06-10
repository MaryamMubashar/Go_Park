package com.example.gopark;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gopark.apis.APIClient;
import com.example.gopark.apis.APIInterface;
import com.example.gopark.data.User;
import com.example.gopark.databinding.ActivitySignUpBinding;
import com.example.gopark.room.AppDatabase;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
//        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
//            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
//            finish();
//        }

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.username.getText().toString();
                String email = binding.email.getText().toString();
                String phone = binding.phone.getText().toString();
                String password = binding.password.getText().toString();

                if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    signup(new User(username, email, phone, password));
                }
            }
        });
    }

    private void signup(User user) {
        APIInterface service = APIClient.getClient().create(APIInterface.class);
        Map<String, String> map = new HashMap<>();
        map.put("email", user.email);
        map.put("phone", user.phone);
        map.put("name", user.username);

        Call<User> call = service.signup(map,user.password);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(SignUpActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Log.e("SignupActivity", "Signup failed: " + response.code() + " - " + response.errorBody());
                    Toast.makeText(SignUpActivity.this, "Signup Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("SignupActivity", "Network error", t);
                Toast.makeText(SignUpActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
