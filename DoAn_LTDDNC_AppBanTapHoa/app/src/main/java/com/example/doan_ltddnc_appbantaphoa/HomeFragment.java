package com.example.doan_ltddnc_appbantaphoa;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_ltddnc_appbantaphoa.Model.HomeCategory;
import com.example.doan_ltddnc_appbantaphoa.Model.Populars;
import com.example.doan_ltddnc_appbantaphoa.Model.Recommended;
import com.example.doan_ltddnc_appbantaphoa.Model.ViewAllProduct;
import com.example.doan_ltddnc_appbantaphoa.adapter.HomeCategoryAdapter;
import com.example.doan_ltddnc_appbantaphoa.adapter.PopularAdapter;
import com.example.doan_ltddnc_appbantaphoa.adapter.RecommendedAdapter;
import com.example.doan_ltddnc_appbantaphoa.adapter.ViewAllAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    RecyclerView rcpopular ,rcHomeCategory,rcRec;
    List<Populars> popularsList ;
    List<HomeCategory> homeCategoryList ;
    List<Recommended>recommendedList;
    PopularAdapter popularAdapter ;
    HomeCategoryAdapter categoryAdapter;
    RecommendedAdapter recommendedAdapter;
    FirebaseFirestore db ;
    ScrollView scrollView ;
    LinearLayout lnall ;

    ////////////////////////////////Srearch view
    EditText edtsearch;
    private List<ViewAllProduct> viewAllProductList;
    private RecyclerView rcSearch ;
    private ViewAllAdapter viewAllAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcpopular =view.findViewById(R.id.rv_pop);
        rcHomeCategory=view.findViewById(R.id.rv_Category);
        rcRec=view.findViewById(R.id.rv_recommened);
        scrollView =view.findViewById(R.id.srv);
        rcpopular.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularsList =new ArrayList<>();
        popularAdapter =new PopularAdapter(getActivity(),popularsList);
        rcpopular.setAdapter(popularAdapter);
        scrollView.setVisibility(View.GONE);
        lnall = scrollView.findViewById(R.id.lnallhome);
        LoadingDialog loadingDialog =new LoadingDialog(getContext());
        loadingDialog.ShowDialog("Loading...");
        db =FirebaseFirestore.getInstance();


        db.collection("PopularProducts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        popularsList.clear();
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document :task.getResult()){
                                Populars populars =document.toObject(Populars.class);
                                popularsList.add(populars);
                                Log.d("ABC", populars.toString());
                            }
                            popularAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //home category
        homeCategoryList =new ArrayList<>() ;
        categoryAdapter =new HomeCategoryAdapter(getActivity() ,homeCategoryList);
        rcHomeCategory.setLayoutManager(new LinearLayoutManager(getActivity() ,LinearLayoutManager.HORIZONTAL,false));
        rcHomeCategory.setAdapter(categoryAdapter);
        db.collection("HomeCategory").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                homeCategoryList.clear();
                if (task.isComplete()){
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        HomeCategory homeCategory =documentSnapshot.toObject(HomeCategory.class);
                        homeCategoryList.add(homeCategory);
                        scrollView.setVisibility(View.VISIBLE);
                        loadingDialog.HideDialog();

                    }
                    categoryAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Reccommend
        recommendedList =new ArrayList<>();
        recommendedAdapter =new RecommendedAdapter(recommendedList,getContext());
        rcRec.setLayoutManager(new LinearLayoutManager(getActivity() ,LinearLayoutManager.HORIZONTAL,false));
        rcRec.setAdapter(recommendedAdapter);
        db.collection("Recommened").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               for ( QueryDocumentSnapshot documentSnapshot: task.getResult()){
                   Recommended recommended =documentSnapshot.toObject(Recommended.class);
                   recommendedList.add(recommended);
               }
               recommendedAdapter.notifyDataSetChanged();
            }
        });
        /////////Sreach view

        edtsearch =view.findViewById(R.id.edtsearch);
        rcSearch =view.findViewById(R.id.rc_search);
        rcSearch.setAdapter(viewAllAdapter);
        viewAllProductList =new ArrayList<>();
        rcSearch.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));
        viewAllAdapter =new ViewAllAdapter(getContext(),viewAllProductList);
        rcSearch.setAdapter(viewAllAdapter);
        rcSearch.setHasFixedSize(true);
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    lnall.setVisibility(View.VISIBLE);
                    viewAllProductList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                }else {
                    searchProduct(editable.toString());
                }

            }
        });
    }

    private void searchProduct(String name) {
        if(!name.isEmpty()){

            lnall.setVisibility(View.GONE);
            db.collection("AllProduct").whereEqualTo("type",name).get().
                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful() &&task.getResult() !=null){
                                viewAllProductList.clear();
                                viewAllAdapter.notifyDataSetChanged();
                                for(DocumentSnapshot doc:task.getResult().getDocuments()){
                                    ViewAllProduct viewAllProduct =doc.toObject(ViewAllProduct.class);
                                    viewAllProductList.add(viewAllProduct);
                                    viewAllAdapter.notifyDataSetChanged();
                                }

                            }
                        }
                    });
        }
        else {
            lnall.setVisibility(View.VISIBLE);
        }

    }
}