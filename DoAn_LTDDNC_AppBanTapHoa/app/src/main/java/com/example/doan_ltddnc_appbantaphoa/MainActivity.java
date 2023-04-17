package com.example.doan_ltddnc_appbantaphoa;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.doan_ltddnc_appbantaphoa.Model.MyCart;
import com.example.doan_ltddnc_appbantaphoa.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar ;
    DrawerLayout drawerLayout ;
    NavigationView navigationView ;
    AppBarConfiguration appBarConfiguration;
    ActionBarDrawerToggle actionBarDrawerToggle ;
    NavController navController;
    Task<DataSnapshot> database ;
    FirebaseAuth auth ;
    FirebaseFirestore db ;
    ArrayList<MyCart> myCartArrayList ;
    int mCartItemCount = 0  ;
    Menu getMenu;
    TextView textCartItemCount ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout =findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigation_view);
        auth =FirebaseAuth.getInstance() ;
        db =FirebaseFirestore.getInstance();

        actionBarDrawerToggle =new ActionBarDrawerToggle(MainActivity.this ,
                drawerLayout,toolbar,R.string.open ,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        appBarConfiguration =new AppBarConfiguration.Builder(R.id.homeFragment,R.id.profileFragment,
                R.id.categoryFragment,R.id.offersFragment,R.id.newProductsFragment,R.id.myOrdersFragment,R.id.myCartFragment).setDrawerLayout(drawerLayout).build();
        navController = Navigation.findNavController(this , R.id.nav_host);
        NavigationUI.setupActionBarWithNavController(this ,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView ,navController);
        //header
        View view=navigationView.getHeaderView(0);
        TextView tvEmail =view.findViewById(R.id.tvEmail);
        TextView tvFullname=view.findViewById(R.id.tvFullname);
        CircleImageView imgIconUser =view.findViewById(R.id.imgIconUser);
        auth =FirebaseAuth.getInstance();
        String UID =auth.getCurrentUser().getUid() ;

        if(UID !=null){
            database = FirebaseDatabase.getInstance().getReference().child("Users").child(UID).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    User user =dataSnapshot.getValue(User.class);
                    tvFullname.setText(user.getFirstname().trim()+" "+user.getLastname().trim());
                    tvEmail.setText(user.getEmail());
                    Glide.with(MainActivity.this).load(user.getProfileImg()).into(imgIconUser);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            Toast.makeText(this, "UID rỗng", Toast.LENGTH_SHORT).show();
            return ;
        }

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(navDestination.getId()==R.id.homeFragment){
                    processCart();
                }if(navDestination.getId()==R.id.profileFragment){
                    getMenu.findItem(R.id.itemCart).setVisible(false);
                }if(navDestination.getId() ==R.id.myCartFragment){
                    getMenu.findItem(R.id.itemCart).setVisible(false);
                }if(navDestination.getId() ==R.id.myOrdersFragment){
                    getMenu.findItem(R.id.itemCart).setVisible(false);
                }
                else if(getMenu !=null){
                    getMenu.findItem(R.id.itemCart).setVisible(true);
                }

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.homeFragment:
                        break;
                    case R.id.profileFragment:
                        break;
                    case R.id.categoryFragment:
                        break;
                    case R.id.offersFragment:
                        break;
                    case R.id.newProductsFragment:
                        break;
                    case R.id.myOrdersFragment:
                        break;
                    case R.id.myCartFragment:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return NavigationUI.onNavDestinationSelected(item,navController);
            }
        });
    }
    public void processCart() {
        myCartArrayList =new ArrayList<>();
        db.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("MyCart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isComplete()){
                            for(QueryDocumentSnapshot ducument : task.getResult()){
                                MyCart myCart =ducument.toObject(MyCart.class);
                                myCartArrayList.add(myCart);
                            }
                            mCartItemCount= myCartArrayList.size();
                            textCartItemCount.setText(mCartItemCount+"");
                            setupBadge();
                        }

                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenu =menu;
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.item_cart,menu);
        MenuItem menuItem=menu.findItem(R.id.itemCart);
        MenuItemCompat.setActionView(menuItem,R.layout.menu_cart_custom);
        FrameLayout frameView = (FrameLayout) MenuItemCompat.getActionView(menuItem);
        textCartItemCount =frameView.findViewById(R.id.tv_count_cart);

        frameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }

        });
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController ,appBarConfiguration)||super.onSupportNavigateUp();
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
    public void setupBadge(){
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount,
                        99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        processCart();
    }
}