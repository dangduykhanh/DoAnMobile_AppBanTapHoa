package com.example.doan_ltddnc_appbantaphoa;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_ltddnc_appbantaphoa.Model.MyCart;
import com.example.doan_ltddnc_appbantaphoa.Model.User;
import com.example.doan_ltddnc_appbantaphoa.adapter.MyCartAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCartFragment extends Fragment implements LocationListener{
    RecyclerView rcMycart ;
    FirebaseFirestore db ;
    MyCartAdapter myCartAdapter ;
    List<MyCart> cartList ;
    FirebaseAuth auth ;
    DatabaseReference myRef ;
    TextView tvAmount,tvName ,tvMobile;
    EditText edtAddress;
    ImageView imgGetAddres ;
    final int REQUEST_CODE_LOCATION = 124;
    //permission arrays
    String[] locationPermission;
    LocationManager locationManager;
    public double latitude ,longtitude;
    AppCompatButton btnBuynow ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainActivity activity;

    public MyCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCartFragment newInstance(String param1, String param2) {
        MyCartFragment fragment = new MyCartFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcMycart=view.findViewById(R.id.rcMyCart);
        tvAmount =view.findViewById(R.id.tvTotal);
        tvMobile =view.findViewById(R.id.tvMobile);
        tvName=view.findViewById(R.id.tvName);
        edtAddress =view.findViewById(R.id.edtAddress);
        imgGetAddres=view.findViewById(R.id.imgGetAddress);
        btnBuynow =view.findViewById(R.id.btnBuyNow);

        db =FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        cartList =new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference();
        myCartAdapter =new MyCartAdapter(cartList, getContext(), new MyCartAdapter.OnCartItemClickListener() {
            @Override
            public void OnCartItemClick(MyCart myCart) {
                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("MyCart").document(myCart.getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                    cartList.remove(myCart);
                                    myCartAdapter.notifyDataSetChanged();
                                    calculateTotalAmount(cartList);
                            }
                        });
            }
        });
        rcMycart.setAdapter(myCartAdapter);

        //amount

        rcMycart.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        db.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("MyCart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isComplete()){
                          for (  QueryDocumentSnapshot document : task.getResult()){
                              String documentId =document.getId();
                              MyCart myCart =document.toObject(MyCart.class);
                              myCart.setDocumentId(documentId);
                              cartList.add(myCart) ;
                              myCartAdapter.notifyDataSetChanged();
                          }
                          calculateTotalAmount(cartList);

                        }
                    }
                });
        myRef.child("Users").child(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                   User user =task.getResult().getValue(User.class);
                   tvName.setText("Họ và tên: "+user.getFirstname()+" "+user.getLastname());
                   tvMobile.setText("Số điện thoại: "+user.getMobile());
            }
        });

        //GetAddress
        locationPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

        imgGetAddres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkLocationPermission()) {
                        detectLocation();
                } else {
                    //không cho phép
                    requestLocationPermission();
                }
            }
        });
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragMaps);

        //Asysnc map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                //When mapp is loaded
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        //When click on mapp
                        MarkerOptions markerOptions =new MarkerOptions();
                        //Set postion
                        markerOptions.position(latLng);
                        markerOptions.title(edtAddress.getText().toString());
                        googleMap.clear();
                        //zoom
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng,13
                        ));
                        //add market
                        googleMap.addMarker(markerOptions);

                    }
                });
            }
        });
        //BUY NOW

        btnBuynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtAddress.getText().equals("")){
                    edtAddress.setError("Không được trống");
                }else {
                    if(cartList.size() ==0){
                        Toast.makeText(getActivity(), "Giỏ hàng rỗng", Toast.LENGTH_SHORT).show();

                    }else {
                        Intent intent = new Intent(getContext(), PlacedOderActivity.class);
                        intent.putExtra("itemcartlist", (Serializable) cartList);
                        intent.putExtra("address", edtAddress.getText().toString());
                        intent.putExtra("totalamount",tvAmount.getText().toString());
                        startActivity(intent);

                    }
                }
            }
        });
    }
    public void calculateTotalAmount(List<MyCart> cartList) {
        Locale locale =new Locale("vi","VN");
        NumberFormat numberFormat =NumberFormat.getNumberInstance(locale);
        int totalAmount =0 ;
        for(MyCart myCart :cartList){
            totalAmount +=myCart.getTotalPrice();
        }
        tvAmount.setText(numberFormat.format(totalAmount));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  REQUEST_CODE_LOCATION:{
                if(grantResults.length>0){
                    boolean locationAcepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(locationAcepted){
                        detectLocation();

                    }else {
                        Toast.makeText(getActivity(), "không cho phép truy cập địa chỉ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void detectLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    private boolean checkLocationPermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(getActivity(), locationPermission, REQUEST_CODE_LOCATION);

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        //location detected
        latitude = location.getLatitude();
        longtitude =location.getLongitude();

        //demo
        /*getCurrentAdress();*/

    }
    private void getCurrentAdress() {
        Geocoder geocoder ;
        List<Address> addresses;
        geocoder=new Geocoder(getContext(), Locale.getDefault());

        try {
            addresses=geocoder.getFromLocation(latitude ,longtitude,1);
            String address =addresses.get(0).getAddressLine(0);
            edtAddress.setText(address);


        } catch (IOException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;

    }






}