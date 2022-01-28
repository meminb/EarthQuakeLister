package com.example.depremfectherdemo1;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuakeRecycler extends Fragment {

    private RecyclerView mQuakeRecycler;
    private QuakeAdapter mQuakeAdapter;
    private SearchView mSearchView;

    private DataService dataService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.main_recycler,container,false);
        mQuakeRecycler=(RecyclerView) view.findViewById(R.id.recycler_view);
        mQuakeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mQuakeRecycler.getContext(), DividerItemDecoration.VERTICAL);
        mQuakeRecycler.addItemDecoration(dividerItemDecoration);

        dataService = new DataService();

        updateUI();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();updateUI();
    }


/**menu**/
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_bar,menu);
        mSearchView=(SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuakeAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mQuakeAdapter.filter(newText);
                return true;
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.refresh_button) {
            updateUI();
            mQuakeAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void updateUI(){


        try {
            setQuakesFromKandilliApi();
        } catch (IOException e) {
            e.printStackTrace();
        }
        QuakeLab ep=  QuakeLab.get(getActivity());
        List<Quake> Quakes= QuakeLab.getmQuakes();

        if (mQuakeAdapter==null){
            mQuakeAdapter=new QuakeAdapter(Quakes);
            mQuakeRecycler.setAdapter(mQuakeAdapter);
        }else{
            mQuakeAdapter.notifyDataSetChanged();
        }

    }

    private void setQuakesFromKandilliApi() throws IOException {

        List quakes = dataService.getQuakesFromOrhanApiAndKandilli();
        QuakeLab lab = QuakeLab.get(getActivity());

        for (Object entry: quakes         ) {
            lab.add(objectMapper.convertValue(entry,Quake.class));
        }

    }

/*
    private void getBodyText() {
        final StringBuilder builder = new StringBuilder();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String url = "https://www.koeri.boun.edu.tr/scripts/lst0.asp";//your website url
                    Document doc = Jsoup.connect(url).get();

                    Element body = doc.body();
                    builder.append(body.text());




                } catch (Exception e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] AllData;
        AllData = builder.toString().split("\n");
        System.out.println(Arrays.toString(AllData));
       // QuakeLab.get(getActivity()).clear();
        for (int i = 7; i < 500; i++) { System.out.println(AllData.length);
            String[] temp = AllData[i].split(" +");
                QuakeLab.get(getActivity()).add(
                        new Quake(temp[0] + " " + temp[1].substring(0,5),
                        Float.parseFloat(temp[2]), Float.parseFloat(temp[3]),
                        Float.parseFloat(temp[4]),
                                Float.parseFloat(temp[6]), temp[8] + " " + temp[9]));


        }

    }


*/


    private class QuakeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Quake mQuake;
        private TextView mDate;
        private TextView mEpicenter;
        private TextView mMagnitude;
        private ConstraintLayout mConstLay;

        public QuakeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.quake_item,parent,false));
            itemView.setOnClickListener(this);
            mDate=(TextView) itemView.findViewById(R.id.date_of_event);
            mEpicenter=(TextView) itemView.findViewById(R.id.epicenter);
            mMagnitude=(TextView) itemView.findViewById(R.id.magnitude);
            mConstLay=(ConstraintLayout) itemView.findViewById(R.id.item_view);
        }

        public void bind(Quake quake){
            mQuake=quake;
            mDate.setText(mQuake.getDate());
            mEpicenter.setText(mQuake.getEpicenter());
            mMagnitude.setText((mQuake.getMagnitude()+""));


        }

        @Override
        public void onClick(View v) {// pop up

        }
    }

    private class QuakeAdapter extends RecyclerView.Adapter<QuakeHolder>{
        private List<Quake> mQuakes;
        private List<Quake> mCopyQuakes;
        public QuakeAdapter(List<Quake> l){
            mCopyQuakes=new ArrayList<>();
            mCopyQuakes.addAll(l);
            this.mQuakes=l;
        }

        public void filter(String text) {
            mQuakes.clear();
            if(text.isEmpty()){
                mQuakes.addAll(mCopyQuakes);
            } else{
                text = text.toLowerCase();
                for(Quake item: mCopyQuakes){
                    if(item.getEpicenter().toLowerCase().contains(text) ){
                        mQuakes.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public QuakeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new QuakeHolder(li,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull QuakeHolder holder, int position) {
            Quake e=mQuakes.get(position);

            String Hex=Integer.toHexString(Math.round(255-25*e.getMagnitude()));
            if(Hex.length()==1){
                Hex="0"+Hex;
            }
           // holder.mConstLay.setBackgroundColor(Color.parseColor("#"+"ff"+Hex+Hex));

           // holder.mMagnitude.setBackgroundColor(Color.parseColor("#"+"ff"+Hex+Hex));


            Drawable background = holder.mMagnitude.getBackground();
            if (background instanceof ShapeDrawable) {
                ((ShapeDrawable)background).getPaint().setColor(Color.parseColor("#"+"ff"+Hex+Hex));
            } else if (background instanceof GradientDrawable) {
                ((GradientDrawable)background).setColor(Color.parseColor("#"+"ff"+Hex+Hex));
            } else if (background instanceof ColorDrawable) {
                ((ColorDrawable)background).setColor(Color.parseColor("#"+"ff"+Hex+Hex));
            }


            holder.bind(e);
        }

        @Override
        public int getItemCount() {
            return mQuakes.size();
        }
    }

}
