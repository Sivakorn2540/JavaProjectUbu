package com.example.sivakorn.firebaseproject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sivakorn on 4/25/2017.
 */

public class Data {
    public String username;
    public String email;

    public Data() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Data(String username, String email) {
        this.username = username;
        this.email = email;
    }


}
