package com.betulas.roombasictry;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.betulas.roombasictry.databinding.ActivityAddNewUserBinding;
import com.betulas.roombasictry.db.AppDatabase;
import com.betulas.roombasictry.db.User;


import java.util.List;

public class AddNewUserActivity extends AppCompatActivity {
    ActivityAddNewUserBinding binding;
    Intent intent;
    private List<User> userList;
    UserListAdapter userListAdapter;
    RecyclerView recyclerView;
    EditText lastNameInput;
    EditText firstNameInput;
    Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void saveonclk(View view){
        final EditText firstNameInput=findViewById(R.id.fistNameInput);
        final EditText lastNameInput=findViewById(R.id.lastNameInput);
        Button btn=findViewById(R.id.saveButton);
        btn.setVisibility(View.INVISIBLE);
        saveNewUser(firstNameInput.getText().toString(), lastNameInput.getText().toString());
        ssss();


    }
    private void saveNewUser(String firstName,String lastName){
        AppDatabase db=AppDatabase.getDbInstance(this.getApplicationContext());
        User user= new User();
        user.firstName=firstName;
        user.lastName=lastName;
        db.userDao().insertUser(user);
        if(db.isOpen()==true)
            db.close();


    }
    public void runandhander(){
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                ssss();
                handler.postDelayed(this,2500);
            }
        };
        handler.post(runnable);
    }
    public void ssss(){
        AlertDialog.Builder alert = new AlertDialog.Builder(AddNewUserActivity.this);

        alert.setTitle("Again");
        alert.setMessage("Do you want to new record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=getIntent();
                finish();
                startActivity(intent);
                PendingIntent pendingIntent=PendingIntent.getActivity(AddNewUserActivity.this,1000,getIntent(),PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC,System.currentTimeMillis()+1000,pendingIntent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(AddNewUserActivity.this,MainActivity.class);
                startActivity(intent);
                PendingIntent pendingIntent=PendingIntent.getActivity(AddNewUserActivity.this,1000,getIntent(),PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC,System.currentTimeMillis()+1000,pendingIntent);
                userListAdapter.notifyDataSetChanged();

            }
        });

        alert.show();
    }


}