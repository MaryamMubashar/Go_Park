package com.example.gopark;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gopark.apis.APIClient;
import com.example.gopark.apis.APIInterface;
import com.example.gopark.data.User;
import com.example.gopark.databinding.ActivityLoginBinding;
import com.example.gopark.room.AppDatabase;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Bind views
        TextInputEditText emailEditText = findViewById(R.id.emaillogin);
        TextInputEditText passwordEditText = findViewById(R.id.passwordlogin);
        Button loginButton = findViewById(R.id.loginbtn);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Make API call for login
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Map<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);
            Call<User> call1 = apiInterface.login(map);
            call1.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200) {
                        User user = response.body();
                        String session = response.headers().get("session");
                        if (user != null) {
                            user.setSession(session);

                            // Insert or replace user data asynchronously


                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error: User data is null", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

}