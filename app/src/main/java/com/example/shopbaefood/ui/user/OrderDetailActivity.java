package com.example.shopbaefood.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shopbaefood.R;
import com.example.shopbaefood.adapter.OrderDetailAdapter;
import com.example.shopbaefood.model.Order;
import com.example.shopbaefood.model.OrderDetail;
import com.example.shopbaefood.model.dto.ApiResponse;
import com.example.shopbaefood.service.ApiService;
import com.example.shopbaefood.util.Notification;
import com.example.shopbaefood.util.UtilApp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {
    Intent intent;
    BottomNavigationView bottomNavigationView;
    RecyclerView rcvOrderDetail;
    ProgressBar progressBar;
    TextView orderMerName, orderMerTotal, orderMerAddress, orderMerNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        bottomNavigationView = findViewById(R.id.nav_order_detail);
        rcvOrderDetail=findViewById(R.id.recyclerView_order_detail);
        progressBar=findViewById(R.id.progressBar_order_detail);
        orderMerName=findViewById(R.id.order_detail_mer_name_val);
        orderMerTotal=findViewById(R.id.order_detail_price_total_val);
        orderMerAddress=findViewById(R.id.order_detail_address_val);
        orderMerNote=findViewById(R.id.order_detail_note_val);

        intent =getIntent();
        Order order= new Order();
        if(intent.hasExtra("order")){
            order=(Order) intent.getSerializableExtra("order");
        }
        getOrderDetail(order.getId());
        orderMerName.setText(order.getMerchant().getName());
        orderMerTotal.setText(String.valueOf(order.getTotalPrice()));
        orderMerAddress.setText(order.getDeliveryAddress());
        orderMerNote.setText(order.getNote());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                intent.setClass(OrderDetailActivity.this, HomeUserActivity.class);
                switch (item.getItemId()) {
                    case R.id.tab1:
                        intent.putExtra("pageToDisplay", 0); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    case R.id.tab2:
                        intent.putExtra("pageToDisplay", 1); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                    case R.id.tab3:
                        intent.putExtra("pageToDisplay", 2); // 1 là trang bạn muốn hiển thị
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

    }

    private void getOrderDetail(Long orderId) {
        ApiService apiService= UtilApp.retrofitAuth(OrderDetailActivity.this).create(ApiService.class);
        Call<ApiResponse<List<OrderDetail>>> call= apiService.orderDetail(orderId);
        call.enqueue(new Callback<ApiResponse<List<OrderDetail>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<OrderDetail>>> call, Response<ApiResponse<List<OrderDetail>>> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    handlerOrderDetailList(response.body().getData());
                }else {
                    Notification.sweetAlert(OrderDetailActivity.this, SweetAlertDialog.ERROR_TYPE,"Error","Lỗi rồi thử lại đi");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<OrderDetail>>> call, Throwable t) {
                Notification.sweetAlert(OrderDetailActivity.this, SweetAlertDialog.ERROR_TYPE,"Error","Lỗi phía hệ thống");
            }
        });

    }

    private void handlerOrderDetailList(List<OrderDetail> data) {
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,1);
        rcvOrderDetail.setLayoutManager(gridLayoutManager);
        OrderDetailAdapter adapter= new OrderDetailAdapter(data);
        rcvOrderDetail.setAdapter(adapter);
    }
}