package com.example.wastematepro.Fragments;

import android.app.Dialog;
import android.app.DownloadManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wastematepro.Adapters.LeaderboardAdapter;
import com.example.wastematepro.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class LeaderboardFragment extends Fragment {

    FragmentLeaderboardBinding binding;
    ArrayList<UserModel>list;
    LeaderboardAdapter adapter;
    FirebaseFirestore firestore;
    Dialog dialog;


    public LeaderboardFragment() {
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
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);

        firestore = FirebaseFirestore.getInstance();

        list = new ArrayList<>();

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.loading_dialog);

        dialog.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvLeaderboard.setLayoutManager(layoutManager);

        adapter = new LeaderboardAdapter(getContext(), list, new LeaderboardAdapter.RankListener(){
            @Override
            public void rankItem(int position, String name, String profile, int coins, ArrayList<UserModel>list){

                if(position==0){

                    binding.rank1Name.setText(name);
                    binding.rank1Coin.setText(coins+"");

                    Picasso.get()
                            .load(profile)
                            .placeholder(R.drawable.profile)
                            .into(binding.profileImage);
                }
                else if(position==1){

                    binding.rank2Name.setText(name);
                    binding.rank2Coin.setText(coins+"");

                    Picasso.get()
                            .load(profile)
                            .placeholder(R.drawable.profile)
                            .into(binding.rank2Profile);
                }
                if(position==2){

                    binding.rank3Name.setText(name);
                    binding.rank3Coin.setText(coins+"");

                    Picasso.get()
                            .load(profile)
                            .placeholder(R.drawable.profile)
                            .into(binding.rank3Profile);
                }

            }
        });

        binding.rvLeaderboard.setAdapter(adapter);

        firestore.collection("users").orderBy("coins", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>(){
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshot){

                for(DocumentSnapshot snapshot : queryDocumentSnapshot){
                    UserModel model = snapshot.toObject(UserModel.class);
                    list.add(model);
                }

                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        return binding.getRoot();
    }
}