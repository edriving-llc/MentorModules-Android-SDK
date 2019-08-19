package com.edriving.mentor.mentor_module;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.edriving.mentor.mentor_module.ui.MovieListActivity;
import com.edriving.mentor.modulessdk.MentorModuleProvider;
import com.edriving.mentor.modulessdk.network.exception.InitializationException;
import com.edriving.mentor.modulessdk.network.exception.InvalidKeyException;
import com.edriving.mentor.modulessdk.network.exception.ModuleInitializationException;
import com.edriving.mentor.modulessdk.network.exception.ModuleListException;
import com.edriving.mentor.modulessdk.network.model.MentorModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int PERMISSIONS_RESULT = 1500;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String clientDebugKey = "SSTj47MD3E59wdFrqdFsvJ4IMAgXoanXq2edW6Ipw";
    public static String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private MentorModuleProvider moduleProvider;
    private ProgressBar progressBar;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText userIdInput = findViewById(R.id.user_id);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.main_page_progressbar);

        login.setOnClickListener(view -> {
            String userId = userIdInput.getText().toString();
            if (!userId.isEmpty() && isNetworkAvailable()) {
                InitializeTheMentorModule initializeMentorSdk = new InitializeTheMentorModule();
                initializeMentorSdk.execute(userId);
            } else {
                Toast.makeText(view.getContext(), "Please input user name, and make sure internet is working", Toast.LENGTH_LONG).show();
            }
        });

        if (!hasPermission(permissions[0])) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_RESULT);
        } else {
            login.setEnabled(true);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_RESULT) {
            login.setEnabled(true);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void startMovieList(List<MentorModule> mentorModules) {
        Intent movieListIntent = new Intent(getApplicationContext(), MovieListActivity.class);
        movieListIntent.putExtra("movie-list", new ArrayList<>(mentorModules));
        startActivity(movieListIntent);
    }

    private boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    class InitializeTheMentorModule extends AsyncTask<String, Void, List<MentorModule>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            login.setEnabled(false);
        }

        @Override
        protected List<MentorModule> doInBackground(String... userId) {
            List<MentorModule> moduleList = null;
            try {
                moduleProvider = new MentorModuleProvider(getApplicationContext(), clientDebugKey, userId[0]);
                // Or if you like to get modules in any other languages
                // moduleProvider = new MentorModuleProvider(getApplicationContext(),clientDebugKey,userId[0],"es","419","America/Mexico_City");
                moduleList = moduleProvider.getModules();

            } catch (InvalidKeyException | InitializationException | ModuleListException e) {
                Log.e(TAG, "Error:" + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IO Error", e);
            } catch (ModuleInitializationException e) {
                Log.e(TAG, "Error:" + e.getMessage());
            }

            return moduleList;
        }

        @Override
        protected void onPostExecute(List<MentorModule> mentorModules) {
            super.onPostExecute(mentorModules);
            progressBar.setVisibility(View.INVISIBLE);
            login.setEnabled(true);
            if (mentorModules != null) {
                Log.d(TAG, "Result:" + mentorModules.size());
                startMovieList(mentorModules);
            } else {
                Toast.makeText(getApplicationContext(), "Something is wrong", Toast.LENGTH_LONG).show();
            }

        }
    }


}
