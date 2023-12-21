package com.example.wastematepro.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.wastematepro.Adapters.TaskAdapter;
import com.example.wastematepro.Models.TasksModel;
import com.example.wastematepro.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    ArrayList<TasksModel>list;
    TaskAdapter adapter;
    FirebaseFirestore firestore;
    Dialog dialog;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        firestore = FirebaseFirestore.getInstance();

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.loading_dialog);

        dialog.show();
        loadUserData();


        list = new ArrayList<>();

        list.add(new TasksModel("Trash Bin Locator",R.drawable.appicon));
        list.add(new TasksModel("Eco-Makeover",R.drawable.tutorial));
        list.add(new TasksModel("Knowledge Time!",R.drawable.ideas));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvTasks.setLayoutManager(layoutManager);

        adapter = new TaskAdapter(getContext(),list);
        binding.rvTasks.setAdapter(adapter);

        return binding.getRoot();
    }

    private void loadUserData() {

        firestore.collection("users").document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot){

                        UserModel model = documentSnapshot.toObject(UserModel.class);

                        if(documentSnapshot.exists()){
                            binding.userName.setText(model.getName());
                            binding.userCoin.setText(model.getCoins()+"");
                            binding.totalCoins.setText(model.getCoins()+"");

                            Picasso.get()
                                    .load(model.getProfile())
                                    .placeholder(R.drawable.profile)
                                    .into(binding.profileImage);

                            dialog.dismiss();
                        }

                    }
                })
    }
}