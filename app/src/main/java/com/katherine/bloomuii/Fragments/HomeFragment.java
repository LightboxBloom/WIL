/*Created by: Katherine Chambers
* Edited by: Rohini Naidu 28/06/2020*/
package com.katherine.bloomuii.Fragments;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.katherine.bloomuii.Games.MatchShape.MatchShapesActivity;
import com.katherine.bloomuii.Games.MatchingCard.MatchingCardsMainActivity;
import com.katherine.bloomuii.Games.Math.MathFragment;
import com.katherine.bloomuii.Games.Order.OrderFragment;
import com.katherine.bloomuii.Games.PartOfSpeech.PartOfSpeechMenuActivity;
import com.katherine.bloomuii.Games.PhotoLabel.PhotoLabelMenu;
import com.katherine.bloomuii.Games.Puzzle.PuzzleMain;
import com.katherine.bloomuii.Games.Unjumble.UnjumbleFragment;
import com.katherine.bloomuii.Games.Unjumble.UnjumbleHandler;
import com.katherine.bloomuii.Handlers.StoryBoardHandler;
import com.katherine.bloomuii.R;
import com.katherine.bloomuii.ObjectClasses.User;
import com.squareup.picasso.Picasso;
public class HomeFragment extends Fragment {
    //UI Components
    TextView fullName;
    CardView itemPuzzle, itemShape, itemUnjumble, itemMatching, itemOrder, itemMath, itemLabel, itemPartOfSpeech;
    ImageView mProfilePicture;
    FloatingActionButton viewClassrooms;
    ImageView image;
    TextView message;
    //Firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private StorageReference storage;
    private StorageReference fileRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //UI Components
        fullName = view.findViewById(R.id.txtHomeUsername);
        itemPuzzle = view.findViewById(R.id.itemPuzzle);
        itemShape = view.findViewById(R.id.itemShapes);
        itemUnjumble = view.findViewById(R.id.itemUnjumble);
        itemMatching = view.findViewById(R.id.itemMatching);
        itemOrder = view.findViewById(R.id.itemOrder);
        itemMath = view.findViewById(R.id.itemMath);
        itemLabel = view.findViewById(R.id.itemLabel);
        itemPartOfSpeech = view.findViewById(R.id.itemPartOfSpeech);
        mProfilePicture = view.findViewById(R.id.imgProfilePicture);
        viewClassrooms = view.findViewById(R.id.btnViewClassrooms);
        image = view.findViewById(R.id.imgStoryBoard);
        message = view.findViewById(R.id.txtStoryboardContent);
        //Firebase Declarations
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        myRef = database.getReference("Users/" + currentUser.getUid());
        storage = FirebaseStorage.getInstance().getReference();
        fileRef = storage.child("Users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user!=null) {
                    fullName.setText(user.getFull_Name() + "#" + currentUser.getUid().substring(0, 4));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        setStoryBoard();
        setProfilePicture();
        ViewClassrooms();
        Puzzle();
        MatchShape();
        Unjumble();
        MatchingCard();
        Order();
        Math();
        Label();
        PartOfSpeech();
        return view;
    }
    //Set up StoryBoard
    private void setStoryBoard(){
        StoryBoardHandler sth = new StoryBoardHandler();
        int resID = getResources().getIdentifier(sth.getImage(),"drawable", getContext().getPackageName());
        image.setImageResource(resID);
        message.setText(sth.getMessage());
    }
    //Navigation
    private void ViewClassrooms(){
        viewClassrooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ViewClassroomFragment viewClassroom = new ViewClassroomFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, viewClassroom);
                fragmentTransaction.commit();
            }
        });
    }
    private void MatchShape() {
        itemShape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MatchShapesActivity.class));
            }
        });
    }
    private void Puzzle(){
        itemPuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PuzzleMain.class));
            }
        });
    }
    private void Unjumble(){
        itemUnjumble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                UnjumbleFragment fragment = new UnjumbleFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.commit();

                /*Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        UnjumbleHandler.FirebaseData();
                    }
                }, 2000);   //5 seconds*/
            }
        });
    }
    private void MatchingCard(){
        itemMatching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MatchingCardsMainActivity.class));
            }
        });
    }
    private void Order(){
        itemOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                OrderFragment fragment = new OrderFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.commit();

                /*Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        OrderFragment.levelCreate();
                    }
                }, 3000);   //5 seconds*/
            }
        });
    }
    private void Math(){
        itemMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MathFragment fragment = new MathFragment();
                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                fragmentTransaction.commit();

                /*Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        MathFragment.sumType();
                    }
                }, 2000);   //5 seconds*/
            }
        });
    }
    private void Label(){
        itemLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PhotoLabelMenu.class));
            }
        });
    }
    private void PartOfSpeech(){
        itemPartOfSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PartOfSpeechMenuActivity.class));
            }
        });
    }
    //Source: StackOverflow
    //https://stackoverflow.com/questions/25347943/how-to-use-picasso-library
    private void setProfilePicture() {
        if(fileRef !=null) {
            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).centerCrop().fit().into(mProfilePicture);
                }
            });
        }
    }

}
