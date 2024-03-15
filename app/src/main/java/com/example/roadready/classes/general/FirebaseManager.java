package com.example.roadready.classes.general;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class FirebaseManager {
    private final String TAG = "FirebaseManager"; // declare TAG for each class for debuging purposes using Log.d()
    private static FirebaseManager firebaseManager = null;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;
    private final FirebaseStorage storage;

    private FirebaseManager() {
        mAuth = FirebaseAuth.getInstance(); // Firebase Authentication Object
        db = FirebaseFirestore.getInstance(); // Firebase Cloud Firestore Object
        storage = FirebaseStorage.getInstance(); // Firebase Cloud Storage Object
    }

    // Singleton
    public static synchronized FirebaseManager getInstance() {
        if (firebaseManager == null) {
            firebaseManager = new FirebaseManager();
        }
        return firebaseManager;
    }

    public boolean isSignedIn() {
        return mAuth.getCurrentUser() != null;
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public void register(String email, String password, String firstname, String lastname) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                Map<String, Object> userData = new HashMap<>();
                userData.put("firstname", firstname);
                userData.put("lastname", lastname);

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(firstname + " " + lastname)
                        .build();

                assert firebaseUser != null;
                firebaseUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                }
                            }
                        });

                db.collection("users")
                        .document(firebaseUser.getUid())
                        .set(userData)
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "User added to Firestore successfully"))
                        .addOnFailureListener(e -> Log.w(TAG, "Error adding user to Firestore", e));
                Log.d(TAG, "createUserWithEmail:success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "createUserWithEmail:failure");
            }
        });
    }
}
