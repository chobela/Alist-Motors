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

import java.util.List;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<Deals> deals;

    public RecyclerViewAdapter(List<Deals> deals, Context context){

        super();

        this.deals = deals;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Deals mydeals =  deals.get(position);

        holder.textViewMeal.setText(mydeals.getMeal());

        holder.textViewOldPrice.setText(mydeals.getOldprice());

        holder.textViewPrice.setText(mydeals.getPrice());

        holder.textViewVenue.setText(mydeals.getVenue());

        holder.textViewMealid.setText(mydeals.getMealid());

        //Picasso.with(context).load("http://" + mydeals.getImage()).into(holder.imageView);

        Glide.with(context).load(mydeals.getImage())
                .placeholder(R.drawable.fff)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {

                    case (R.id.shareme):

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = mydeals.getMeal() + " https://play.google.com/store/apps/details?id=com.bolayapazed.applink";
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

        holder. textGetDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {


                    case (R.id.getdeal):

                        Intent intent = new Intent(context, CarDetalis.class);
                        intent.putExtra("jobs",  deals.get(position));
                        context.startActivity(intent);
                        break;
                }
            }

        });


    }

    @Override
    public int getItemCount() {

        return deals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewMeal, textViewOldPrice, textViewPrice, textViewVenue, textViewMealid, textGetDeal;
        ImageView imageView, shareBtn;


        public ViewHolder(View itemView) {

            super(itemView);

            textViewMealid = (TextView) itemView.findViewById(R.id.mealid) ;
            textViewMeal = (TextView) itemView.findViewById(R.id.meal) ;
            textViewOldPrice = (TextView) itemView.findViewById(R.id.oldprice) ;
            textViewPrice = (TextView) itemView.findViewById(R.id.price) ;
            textViewVenue = (TextView) itemView.findViewById(R.id.venue) ;
            imageView = (ImageView) itemView.findViewById(R.id.imageq);
            shareBtn = (ImageView) itemView.findViewById(R.id.shareme);
            textGetDeal = (TextView) itemView.findViewById(R.id.getdeal) ;

        }

        // Click event for all items
        @Override
        public void onClick(View v) {



        }


    }
} 