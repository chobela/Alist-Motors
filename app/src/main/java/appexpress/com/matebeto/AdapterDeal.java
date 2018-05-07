package appexpress.com.matebeto;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class AdapterDeal  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context context;
    private LayoutInflater inflater;
    ArrayList<Deals> data = new ArrayList<>();

    Deals current;
    int currentPos=0;

    // create constructor to initialize context and data sent from MainActivity
    public AdapterDeal(Context context, List<Deals> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data= (ArrayList<Deals>) data;
    }

    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.deal_items, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        final Deals current=data.get(position);


        myHolder.textViewMeal.setText(current.getMeal());

        myHolder.textViewOldPrice.setText(current.getOldprice());

        myHolder.textViewPrice.setText(current.getPrice());

        myHolder.textViewVenue.setText(current.getVenue());

        myHolder.textViewMealid.setText(current.getMealid());

        //Picasso.with(context).load("http://" + mydeals.getImage()).into(holder.imageView);

        Glide.with(context).load("http://" + current.getImage())
                .placeholder(R.drawable.fff)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(myHolder.imageView);

        myHolder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {

                    case (R.id.shareme):

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = current.getMeal() + " https://play.google.com/store/apps/details?id=com.bolayapazed.applink";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Wire Direct");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        break;

                    case (R.id.getdeal):

                        try {
                            String uri = "tel:" + "+26095500802";
                            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));

                            context.startActivity(dialIntent);
                        } catch (Exception e) {
                            Toast.makeText(context.getApplicationContext(), "Your call has failed...",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        break;
                }
            }

        });

        myHolder.textGetDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {


                    case (R.id.getdeal):

                        try {
                            String uri = "tel:" + "+26095500802";
                            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));

                            context.startActivity(dialIntent);
                        } catch (Exception e) {
                            Toast.makeText(context.getApplicationContext(), "Your call has failed...",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        break;
                }
            }

        });
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewMeal, textViewOldPrice, textViewPrice, textViewVenue, textViewMealid, textGetDeal;
        ImageView imageView, shareBtn;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);


            textViewMealid = (TextView) itemView.findViewById(R.id.mealid) ;
            textViewMeal = (TextView) itemView.findViewById(R.id.meal) ;
            textViewOldPrice = (TextView) itemView.findViewById(R.id.oldprice) ;
            textViewPrice = (TextView) itemView.findViewById(R.id.price) ;
            textViewVenue = (TextView) itemView.findViewById(R.id.venue) ;
            imageView = (ImageView) itemView.findViewById(R.id.imageq);
            shareBtn = (ImageView) itemView.findViewById(R.id.shareme);
            textGetDeal = (TextView) itemView.findViewById(R.id.getdeal) ;
            itemView.setOnClickListener(this);
        }

        // Click event for all items
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, FoodDetails.class);
            intent.putExtra("jobs",  data.get(getAdapterPosition()));
            context.startActivity(intent);

        }

    }
} 