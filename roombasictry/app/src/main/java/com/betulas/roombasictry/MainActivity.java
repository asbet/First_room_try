package com.betulas.roombasictry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.betulas.roombasictry.db.AppDatabase;
import com.betulas.roombasictry.db.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UserListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addNewUserButton =findViewById(R.id.addNewUserButton);
        addNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadUserList();
                Intent intent=new Intent(MainActivity.this,AddNewUserActivity.class);
                startActivity(intent);
            }
        });
        initRecyclerView();
        loadUserList();
    }
    private void initRecyclerView(){
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        userListAdapter=new UserListAdapter(this);
        recyclerView.setAdapter(userListAdapter);
    }
    private void loadUserList(){
        AppDatabase db=AppDatabase.getDbInstance(this.getApplicationContext());
        List <User> userList=db.userDao().getAllUsers();
        userListAdapter.setUserList(userList);
    }


}