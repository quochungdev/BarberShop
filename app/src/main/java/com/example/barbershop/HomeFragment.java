package com.example.barbershop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.barbershop.Adaptor.CategoryAdapter;
import com.example.barbershop.Adaptor.ServiceAdapter;
import com.example.barbershop.Domain.Account;
import com.example.barbershop.Domain.Category;
import com.example.barbershop.Domain.Role;
import com.example.barbershop.Module.AccountDataSource;
import com.example.barbershop.Module.CategoryDataSource;
import com.google.android.material.textfield.TextInputEditText;
import com.example.barbershop.Adaptor.StaffAdapter;
import com.example.barbershop.Domain.Staff;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView txtName;
    TextInputEditText txtUsername;
    ImageButton imgPicCate;
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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleId", -1);
            // Lấy thông tin người dùng từ cơ sở dữ liệu
            StaffAdapter staffAdapter = new StaffAdapter(getActivity());
            RecyclerView rcvStaff = view.findViewById(R.id.rcvStaff);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            rcvStaff.setLayoutManager(linearLayoutManager);
            staffAdapter.setData(getListStaff());
            rcvStaff.setAdapter(staffAdapter);

            RecyclerView rcvCategory = view.findViewById(R.id.rcvCategory);
            CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity());
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
            rcvCategory.setLayoutManager(linearLayoutManager1);
            rcvCategory.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            categoryAdapter.setData(getListCategory());
            rcvCategory.setAdapter(categoryAdapter);

            txtName = view.findViewById(R.id.txtName);

            String txtUsername = sharedPreferences.getString("username", "Guest");
            txtName.setText(txtUsername);

        Button btnManageCategory = view.findViewById(R.id.btnManageCategory);
        if (roleId == 1) {
            // Nếu roleId là 1 (admin), hiển thị Button "Control Category"
            btnManageCategory.setVisibility(View.VISIBLE);
            btnManageCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý sự kiện khi nút được nhấn
                    // Ví dụ: chuyển đến hoạt động quản lý danh mục
                    Intent intent = new Intent(getActivity(), IndexCategoryActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            // Nếu roleId không phải là admin, ẩn Button "Control Category"
            btnManageCategory.setVisibility(View.GONE);
        }

    }

    private List<Account> getListStaff() {
        AccountDataSource accountDataSource = new AccountDataSource(getActivity());
        List<Account> staffList = accountDataSource.selectAccountsRoleStaff(getActivity());
        return staffList;
    }

    private List<Category> getListCategory(){
        CategoryDataSource categoryDataSource = new CategoryDataSource(getActivity());
        List<Category> categoryList = categoryDataSource.selectCategories(getActivity());
        return categoryList;
    }



}