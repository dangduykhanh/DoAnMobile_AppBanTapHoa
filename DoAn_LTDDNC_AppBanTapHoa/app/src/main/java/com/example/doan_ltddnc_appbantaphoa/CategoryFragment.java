package com.example.doan_ltddnc_appbantaphoa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_ltddnc_appbantaphoa.Model.NavCategory;
import com.example.doan_ltddnc_appbantaphoa.adapter.NavCategoryAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
    RecyclerView rcNavCategory ;
    NavCategoryAdapter navcategoryAdapter;
    List<NavCategory> navCategoryList ;
    FirebaseFirestore db ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcNavCategory=view.findViewById(R.id.rc_cat_nav);
        navCategoryList =new ArrayList<>() ;
        navcategoryAdapter =new NavCategoryAdapter(getContext(),navCategoryList);
        rcNavCategory.setLayoutManager(new LinearLayoutManager(getActivity() ,LinearLayoutManager.VERTICAL,false));
        rcNavCategory.setAdapter(navcategoryAdapter);
        db =FirebaseFirestore.getInstance();
        db.collection("NavCategory").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                navCategoryList.clear();
                if (task.isComplete()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        NavCategory navCategory =documentSnapshot.toObject(NavCategory.class);
                        navCategoryList.add(navCategory);
                    }
                    navcategoryAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}