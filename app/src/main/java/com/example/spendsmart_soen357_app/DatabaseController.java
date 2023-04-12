package com.example.spendsmart_soen357_app;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DatabaseController {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static String LOGGEDIN_USERNAME = null;
    private static String LOGGEDIN_CHECKING = null;

    public interface LoginCallback {
        void onLoginComplete(boolean success);
    }

    public static void setLOGGEDIN_USERNAME(String LOGGEDIN_USER) {
        LOGGEDIN_USERNAME = LOGGEDIN_USER;
    }

    public void login(String username, String password, Callback<Boolean> callback) {
        new LoginTask(username, password,  callback).execute();
    }

    // Gets transactions for the logged in user
    // todo: to be implemented
    public void getTransactions(Callback<JSONArray> callback) {
        DatabaseReference transactionsRef = database.getReference("transactions");
        Query query = transactionsRef.orderByChild("username").equalTo(LOGGEDIN_USERNAME);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                JSONArray transactionsJsonArray = new JSONArray();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        // convert transaction data to JSON and add it to the array
                        JSONObject transactionJson = new JSONObject();
                        transactionJson.put("id", dataSnapshot.getKey());
                        transactionJson.put("account", dataSnapshot.child("account").getValue());
                        transactionJson.put("amount", dataSnapshot.child("amount").getValue());
                        transactionJson.put("category", dataSnapshot.child("category").getValue());
//                        transactionJson.put("date", dataSnapshot.child("date").getValue()); // TODO: maybe implement date of purchase
                        transactionJson.put("subject", dataSnapshot.child("subject").getValue());
                        transactionJson.put("type", dataSnapshot.child("type").getValue());
                        transactionJson.put("username", dataSnapshot.child("username").getValue());
                        transactionsJsonArray.put(transactionJson);
                    } catch (JSONException e) {
                        // handle the error
                        callback.onCallback(null);
                        return;
                    } catch (Exception a) {
                        a.printStackTrace();
                    }
                }
                callback.onCallback(transactionsJsonArray);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle the error
                callback.onCallback(null);
            }
        });
    }



    // gets the saving account funds of the logged in user
    public void getSavingAccountFunds(Callback<String> callback){
        if (LOGGEDIN_USERNAME != null) {
            DatabaseReference userRef = database.getReference("users").child(this.LOGGEDIN_USERNAME).child("saving_account");

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get the current value of the checking_account child node
                    String funds = dataSnapshot.getValue(String.class);
                    callback.onCallback(funds);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle the error
                    callback.onCallback(null);
                }
            });
        } else {
            // Handle the case where LOGGEDIN_USERNAME is null
            callback.onCallback("User not logged in");
        }
    }


    // Gets the checking account funds for the logged in user
    public void getCheckingAccountFunds(Callback<String> callback){
        if (LOGGEDIN_USERNAME != null) {
            DatabaseReference userRef = database.getReference("users").child(this.LOGGEDIN_USERNAME).child("checking_account");

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get the current value of the checking_account child node
                    String funds = dataSnapshot.getValue(String.class);
                    callback.onCallback(funds);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle the error
                    callback.onCallback(null);
                }
            });
        } else {
            // Handle the case where LOGGEDIN_USERNAME is null
            callback.onCallback("User not logged in");
        }
    }


    // Adds (or subtracts if given a negative number) to the logged in username's checking account balance
    public void addCheckingAccountFunds(int amount){
        if (LOGGEDIN_USERNAME != null) {
            DatabaseReference userRef = database.getReference("users").child(this.LOGGEDIN_USERNAME).child("checking_account");

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get the current value of the checking_account child node
                    String currentBalance = dataSnapshot.getValue(String.class);

                    // Add the amount to the current balance
                    int newBalance = Integer.parseInt(currentBalance) + amount;
                    String newBal = Integer.toString(newBalance);

                    // Update the checking_account child node with the new balance
                    userRef.setValue(newBal);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle the error
                }
            });
        } else {
            // Handle the case where LOGGEDIN_USERNAME is null
            return;
        }
    }

    // Adds (or subtracts if given a negative number) to the logged in username's checking account balance
    public void addSavingAccountFunds(int amount){
        if (LOGGEDIN_USERNAME != null) {
            DatabaseReference userRef = database.getReference("users").child(this.LOGGEDIN_USERNAME).child("saving_account");

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get the current value of the checking_account child node
                    String currentBalance = dataSnapshot.getValue(String.class);

                    // Add the amount to the current balance
                    int newBalance = Integer.parseInt(currentBalance) + amount;
                    String newBal = Integer.toString(newBalance);

                    // Update the checking_account child node with the new balance
                    userRef.setValue(newBal);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle the error
                }
            });
        } else {
            // Handle the case where LOGGEDIN_USERNAME is null
            return;
        }
    }


    // Android multithreading bs, used for login
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
