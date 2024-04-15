package com.example.smart_agri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.smart_agri.databinding.ActivityAddExpenseBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {
    com.example.smart_agri.databinding.ActivityAddExpenseBinding binding;
    private String type;
    private Expense_Model expenseModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type=getIntent().getStringExtra("Type");
        expenseModel=(Expense_Model) getIntent().getSerializableExtra("model");

        if(type==null){
            type=expenseModel.getType();
            binding.amount.setText(String.valueOf(expenseModel.getAmount()));
            binding.category.setText(expenseModel.getCategory());
            binding.note.setText(expenseModel.getNote());
        }
        if(expenseModel!=null){
        type=expenseModel.getType();}
        if(type.equals("Income")){
            binding.incomeradio.setChecked(true);
        }else{
            binding.incomeradio.setChecked(true);
        }

        binding.incomeradio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="Income";
            }
        });
        binding.expenseradio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="Expense";
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if(expenseModel==null){
            menuInflater.inflate(R.menu.add_menu,menu);
        }else{
            menuInflater.inflate(R.menu.update_menu,menu);
        }
        menuInflater.inflate(R.menu.add_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.saveExpense){
            if(type!=null){
                createExpense();
            }else{
                updateExpense();
            }
            createExpense();
            return true;
        }
        if(id==R.id.deleteExpense){
            deleteExpense();
        }
        return false;
    }

    private void deleteExpense() {
        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseModel.getExpenseId())
                .delete();
        finish();
    }

    private void createExpense() {
        String expenseId= UUID.randomUUID().toString();
        String amount = binding.amount.getText().toString();
        String note = binding.note.getText().toString();
        String category = binding.category.getText().toString();

        boolean incomechecked =binding.incomeradio.isChecked();
        if(incomechecked){
            type="Income";
        }else{
            type="Expense";
        }

        if(amount.trim().length()==0){
            binding.amount.setError("Empty");
            return;
        }
        Expense_Model expense_model=new Expense_Model(expenseId,note,category,type,Long.parseLong(amount), Calendar.getInstance().getTimeInMillis(),
                FirebaseAuth.getInstance().getUid());

        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseId)
                .set(expense_model);
        finish();
    }
    private void updateExpense() {

        String expenseId= expenseModel.getExpenseId();
        String amount = binding.amount.getText().toString();
        String note = binding.note.getText().toString();
        String category = binding.category.getText().toString();

        boolean incomechecked =binding.incomeradio.isChecked();
        if(incomechecked){
            type="Income";
        }else{
            type="Expense";
        }

        if(amount.trim().length()==0){
            binding.amount.setError("Empty");
            return;
        }
        Expense_Model model=new Expense_Model(expenseId,note,category,type,Long.parseLong(amount), expenseModel.getTime(),
                FirebaseAuth.getInstance().getUid());

        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseId)
                .set(model);
        finish();
    }

}