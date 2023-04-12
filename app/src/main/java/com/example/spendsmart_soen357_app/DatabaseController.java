package com.example.spendsmart_soen357_app;

import android.os.AsyncTask;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.*;

public class DatabaseController {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static String LOGGEDIN_USERNAME = null;

    public interface LoginCallback {
        void onLoginComplete(boolean success);
    }

    public static void setLOGGEDIN_USERNAME(String LOGGEDIN_USER) {
        LOGGEDIN_USERNAME = LOGGEDIN_USER;
    }

    public void login(String username, String password, Callback<Boolean> callback) {
        new LoginTask(username, password,  callback).execute();
    }

    public void addTransaction(int amount){}

    // Adds (or subtracts if given a negative number) to the logged in username's checking account balance
    public void addCheckingAccountFunds(int amount){
        if (LOGGEDIN_USERNAME != null) {
            DatabaseReference userRef = database.getReference("users").child(this.LOGGEDIN_USERNAME).child("checking_account");

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get the current value of the checking_account child node
                    int currentBalance = dataSnapshot.getValue(Integer.class);

                    // Add the amount to the current balance
                    int newBalance = currentBalance + amount;

                    // Update the checking_account child node with the new balance
                    userRef.setValue(newBalance);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle the error
                }
            });
        } else {
            // Handle the case where LOGGEDIN_USERNAME is null
        }
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
