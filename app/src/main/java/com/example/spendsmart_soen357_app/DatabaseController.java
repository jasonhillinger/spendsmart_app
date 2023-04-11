package com.example.spendsmart_soen357_app;

import android.os.AsyncTask;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.*;

public class DatabaseController {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public interface LoginCallback {
        void onLoginComplete(boolean success);
    }

    public void login(String username, String password, Callback<Boolean> callback) {
        new LoginTask(username, password,  callback).execute();
    }

    private class LoginTask extends AsyncTask<Void, Void, Boolean> {
        private final String username;
        private final String password;
        private final Callback<Boolean>  callback;

        public LoginTask(String username, String password, Callback<Boolean> callback) {
            this.username = username;
            this.password = password;
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            DatabaseReference userRef = database.getReference("users").child(username).child("password");
            Task<DataSnapshot> task = userRef.get();
            try {
                DataSnapshot dataSnapshot = Tasks.await(task);
                if (dataSnapshot.exists()) {
                    String dbPassword = dataSnapshot.getValue(String.class);
                    return dbPassword != null && dbPassword.equals(password);
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            callback.onCallback(success);
        }
    }
}
