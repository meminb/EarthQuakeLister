package com.example.depremfectherdemo1;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuakeLab {
    private  static QuakeLab sQuakeLab;
    private static List<Quake> mQuakes;

    public static  QuakeLab get(Context context){
        if (sQuakeLab==null){
            sQuakeLab=new QuakeLab(context);
        }return  sQuakeLab;
    }

    private QuakeLab(Context context){
        mQuakes=new ArrayList<>();
    }


public void clear(){mQuakes.clear();}


    public void add(Quake q){mQuakes.add(q);}

    public Quake getQuake(Date date){
        for (Quake q:mQuakes) {
            if(q.getDate().equals(date)){
                return q;
            }
        }return null;

    }
    public static List<Quake> getmQuakes(){return mQuakes;}

}
