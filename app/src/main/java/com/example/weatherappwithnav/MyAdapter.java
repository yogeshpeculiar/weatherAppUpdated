package com.example.weatherappwithnav;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<ListItem> mDataset;
    private Context context;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView city;
        private TextView temp;
        private  ImageView image;


        public MyViewHolder(View view) {
            super(view);
            this.city=view.findViewById(R.id.city);
            this.temp=view.findViewById(R.id.temp);
//            this.image=view.findViewById(R.id.imageView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context mcontext, ArrayList<ListItem> myDataset) {
         context = mcontext;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @NonNull            // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
      ListItem listItem=mDataset.get(position);
      holder.city.setText(listItem.getCity());
      holder.temp.setText(listItem.getTemperature());

      Double temperature=Double.parseDouble(listItem.getTemperature());

        GradientDrawable magnitudeCircle = (GradientDrawable) holder.temp.getBackground();
        int color=getTempColor(temperature);
        magnitudeCircle.setColor(ContextCompat.getColor(context,color));

        holder.city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent=new Intent(context,ViewCitySpecificDetails.class);
                    intent.putExtra("selectedCity",mDataset.get(position).getCity());
                    context.startActivity(intent);
}
        });

    }
 // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public int getTempColor(Double temperature){
        int colorId;
        int temperatureFloor=(int)Math.floor(temperature);
        if(temperatureFloor<0)
            colorId=R.color.temperature1;
        else if(temperatureFloor<5)
            colorId=R.color.temperature2;
        else if(temperatureFloor<10)
            colorId=R.color.temperature3;
        else if(temperatureFloor<15)
            colorId=R.color.temperature4;
        else if(temperatureFloor<20)
            colorId=R.color.temperature5;
        else if(temperatureFloor<25)
            colorId=R.color.temperature6;
        else if(temperatureFloor<30)
            colorId=R.color.temperature7;
        else if(temperatureFloor<35)
            colorId=R.color.temperature8;
        else if(temperatureFloor<40)
            colorId=R.color.temperature9;
        else
            colorId=R.color.temperature10;
        return colorId;

    }



}
