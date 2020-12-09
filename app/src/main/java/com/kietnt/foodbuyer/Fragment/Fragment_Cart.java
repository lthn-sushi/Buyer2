package com.kietnt.foodbuyer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andremion.counterfab.CounterFab;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kietnt.foodbuyer.Adapter.CartAdapter;
import com.kietnt.foodbuyer.Adapter.MyFoodsAdapter;
import com.kietnt.foodbuyer.DAO.FoodDAO;
import com.kietnt.foodbuyer.Model.Cart;
import com.kietnt.foodbuyer.Model.Food;
import com.kietnt.foodbuyer.R;

import java.util.ArrayList;
import java.util.Iterator;

public class Fragment_Cart extends Fragment {
    public static RecyclerView rcv_cart;
    TextView tvSum;
    ImageView iv_back;
    FoodDAO foodDAO;
    DatabaseReference mDataCart;
    public static CartAdapter cartAdapter;
    ArrayList<Cart> ds_cart;
    double thanhTien;
    Button btnOrder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        mDataCart= FirebaseDatabase.getInstance().getReference("Cart");

        btnOrder = root.findViewById(R.id.btnOrder);
        rcv_cart = root.findViewById(R.id.rcv_cart);
        tvSum = root.findViewById(R.id.tvSum);
        iv_back = root.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
//                fragment_food.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcv_cart.setLayoutManager(layoutManager);
        rcv_cart.hasFixedSize();
        rcv_cart.setHasFixedSize(false);
        foodDAO = new FoodDAO(getContext());

        ds_cart = new ArrayList<Cart>();

        mDataCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ds_cart.clear();

                if (snapshot.exists()){
                    ds_cart.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = snapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()){
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Cart cart = next.getValue(Cart.class);
                        ds_cart.add(cart);
                        thanhTien = thanhTien+cart.getPriceFood();
                        tvSum.setText(thanhTien+" Đ");
                    }
                    cartAdapter = new CartAdapter(getContext(),ds_cart);
                    rcv_cart.setAdapter(cartAdapter);
                    cartAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
//                fragment_food.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
            }
        });

        return root;
    }
}
