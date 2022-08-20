package com.example.project_4_admin.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_4_admin.LoginActivity;
import com.example.project_4_admin.R;
import com.example.project_4_admin.SignUpActivity;
import com.example.project_4_admin.Store_dashboardActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ProfileManagerFragment extends Fragment {

    TextInputEditText fullName_edit_text;
    TextInputEditText email_edit_text;
    TextInputEditText phone_edit_text;
    String name, email, phone, site, siteName = "";

    RelativeLayout buttonContainer;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    LinearLayout update_container;
    Button btn_logout, btn_login, btn_signup;

    String[] weekDate = new String[7];
    float[] totalMoney = new float[7];
    List chartData = new ArrayList<>();
    LocalDateTime dateTime = null;
    float[] money = new float[7];
    BarChart incomeChart;
    BarData barData;
    BarDataSet barDataSet;

    SharedPreferences sharedPreferences;
    static String SHARED_PREF_NAME = "myPref";
    static String KEY_EMAIL = "email";
    static String KEY_NAME = "name";
    static String KEY_PHONE = "phone";
    static String KEY_SITE = "site";
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile_manager, container, false);

        buttonContainer = root.findViewById(R.id.buttonContainer);

        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        email = sharedPreferences.getString(KEY_EMAIL, null);
        name = sharedPreferences.getString(KEY_NAME, null);
        phone = sharedPreferences.getString(KEY_PHONE, null);
        site = sharedPreferences.getString(KEY_SITE, null);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        fullName_edit_text = root.findViewById(R.id.name_text);
        email_edit_text = root.findViewById(R.id.email_text);
        phone_edit_text = root.findViewById(R.id.phone_text);

        btn_login = root.findViewById(R.id.loginBtn);
        btn_signup = root.findViewById(R.id.signupBtn);
        btn_logout = root.findViewById(R.id.LogOutBtn);
        update_container = root.findViewById(R.id.profile_container);

        incomeChart = root.findViewById(R.id.incomeChart);
        incomeChart.setNoDataText("");
        incomeChart.setTouchEnabled(false);
        getIncome();

        checkLogin();
        loadInformation();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignUpActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return root;
    }

    private void loadInformation() {
        fullName_edit_text.setText(name);
        email_edit_text.setText(email);
        phone_edit_text.setText(phone);
    }

    private void checkLogin() {
        String phone = sharedPreferences.getString(KEY_PHONE, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        if (phone == null || email == null) {
            buttonContainer.setVisibility(View.VISIBLE);
            update_container.setVisibility(View.GONE);
        } else {
            buttonContainer.setVisibility(View.GONE);
            update_container.setVisibility(View.VISIBLE);
        }
    }

    private void getIncome() {

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        db.getReference("Site").child(site).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                siteName = snapshot.child("Address").getValue(String.class);
                Query query = db.getReference("Order").orderByChild("siteAddress").equalTo(siteName);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            String date = childSnapshot.child("timeOrder").getValue(String.class);
                            dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                            for (int i = 0; i < 7; i++) {
                                if (LocalDate.now().minusDays(i).equals(dateTime.toLocalDate()) && childSnapshot.child("status").getValue(Integer.class) == 3)  {
                                    money[i] = Float.valueOf(childSnapshot.child("total").getValue(Long.class));
                                    totalMoney[i] += money[i];
                                }

                                chartData.add(new BarEntry(i, totalMoney[i]));

                                barDataSet = new BarDataSet(chartData, "Doanh thu " + siteName);
                                //incomeChart.animateY(1000);
                                barData = new BarData(barDataSet);
                                barDataSet.setDrawValues(true);
                                incomeChart.setFitBars(true);
                                incomeChart.setData(barData);
                                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                barDataSet.setValueTextSize(10f);
                                incomeChart.invalidate();
                                weekDate[i] = LocalDate.now().minusDays(i).getDayOfMonth() + "/" + LocalDate.now().getMonthValue();
                                initBarChart();
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initBarChart() {
        incomeChart.getDescription().setEnabled(false);
        //hiding the grey background of the chart, default false if not set
        incomeChart.setDrawGridBackground(false);
        //remove the bar shadow, default false if not set
        incomeChart.setDrawBarShadow(false);
        //remove border of the chart, default false if not set
        incomeChart.setDrawBorders(false);
        incomeChart.setFitBars(true);
        barDataSet.setDrawValues(true);

        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
        incomeChart.animateY(1000);
        //setting animation for x-axis, the bar will pop up separately within the time we set
        incomeChart.animateX(1000);

        XAxis xAxis = incomeChart.getXAxis();
        //change the position of x-axis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //set the horizontal distance of the grid line
        xAxis.setGranularity(1f);
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false);
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return weekDate[(int) value];
            }
        });
        YAxis leftAxis = incomeChart.getAxisLeft();
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false);
        YAxis rightAxis = incomeChart.getAxisRight();
        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);
        Legend legend = incomeChart.getLegend();
        //setting the shape of the legend form to line, default square shape
        legend.setForm(Legend.LegendForm.SQUARE);
        //setting the text size of the legend
        legend.setTextSize(11f);
        //setting the alignment of legend toward the chart
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false);

    }}