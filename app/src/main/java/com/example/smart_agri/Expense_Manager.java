package com.example.smart_agri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.smart_agri.databinding.ActivityExpenseManagerBinding;
import com.example.smart_agri.databinding.ActivityMainBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Expense_Manager extends AppCompatActivity implements OnItemsClick{
    ActivityExpenseManagerBinding binding;
    private ExpenseAdapter expenseAdapter;
    Intent intent;
    private long income=0,expense=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpenseManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        expenseAdapter=new ExpenseAdapter(this,this);
        binding.recycler.setAdapter(expenseAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        intent = new Intent(Expense_Manager.this, AddExpenseActivity.class);

        binding.addincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Type","Income");
                startActivity(intent);
            }
        });

        binding.addexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Type","Expense");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please");
        progressDialog.setMessage("Wait");
        progressDialog.setCancelable(false);
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            progressDialog.show();
            FirebaseAuth.getInstance()
                    .signInAnonymously()
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressDialog.cancel();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.cancel();
                            Toast.makeText(Expense_Manager.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        income=0;expense=0;
        getData();
    }

    private void getData() {
        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .whereEqualTo("uid",FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        expenseAdapter.clear();
                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds:dsList){
                            Expense_Model expenseModel=ds.toObject(Expense_Model.class);
                            if(expenseModel.getType().equals("Income")){
                                income+=expenseModel.getAmount();
                            }else {
                                expense+=expenseModel.getAmount();
                            }
                            expenseAdapter.add(expenseModel);
                        }
                        setUpGraph();
                    }

                    private void setUpGraph() {
                        List<PieEntry> pieEntryList=new ArrayList<>();
                        List<Integer> colorsList=new ArrayList<>();
                        if(income!=0){
                           pieEntryList.add(new PieEntry(income,"Income"));
                           colorsList.add(getResources().getColor(R.color.teal_700));
                        }
                        if(expense!=0){
                            pieEntryList.add(new PieEntry(expense,"Expense"));
                            colorsList.add(getResources().getColor(R.color.red));
                        }
                        PieDataSet pieDataSet=new PieDataSet(pieEntryList,String.valueOf(income=expense));
                        pieDataSet.setColors(colorsList);
                        pieDataSet.setValueTextColor(getResources().getColor(R.color.white));
                        PieData pieData=new PieData(pieDataSet);

                        binding.piechart.setData(pieData);
                        binding.piechart.invalidate();


                    }
                });
    }

    @Override
    public void onClick(Expense_Model expenseModel) {
        intent.putExtra("model",expenseModel);
        startActivity(intent);
    }
}