package com.example.depremfectherdemo1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
   
    
    protected  Fragment createFragment(){
        //getBodyText(this);
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new QuakeRecycler();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragment_container);
        
        if(fragment==null){
            fragment= createFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }


    }






}