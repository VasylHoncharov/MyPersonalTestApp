package com.vgsoft.mypersonaltestapp.ui.adapters;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vgsoft.mypersonaltestapp.R;
import com.vgsoft.mypersonaltestapp.model.MovieTrailer;
import com.vgsoft.mypersonaltestapp.utility.Utils;

import java.util.ArrayList;


@SuppressWarnings("ALL")
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private static final String TAG = TrailerAdapter.class.getName();
    private final ArrayList<MovieTrailer> trailerIds;

    public TrailerAdapter(ArrayList<MovieTrailer> trailerIds) {
        this.trailerIds = trailerIds;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_card_view, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        MovieTrailer movieTrailer = this.trailerIds.get(position);
        holder.bind(movieTrailer);

    }

    @Override
    public int getItemCount() {
        return this.trailerIds.size();
    }

    @Override
    public void onViewRecycled(TrailerViewHolder holder) {
        super.onViewRecycled(holder);
    }


    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        TextView mTrailerName;
        ImageView mPreview;



        public TrailerViewHolder(final View itemView) {
            super(itemView);
            mTrailerName = itemView.findViewById(R.id.tv_trailer_name);
            mPreview = itemView.findViewById(R.id.iv_trailer_poster);
        }

        public void bind(final MovieTrailer movieTrailer) {
            mTrailerName.setText(movieTrailer.getName());
            Log.d(TAG, "Traler name: " + movieTrailer.getName());
            Picasso.with(mPreview.getContext()).load(Utils.getPreviewUri(movieTrailer.getKey())).placeholder(R.drawable.placeholder).fit().centerCrop().into(mPreview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.playPosterInYoutube(movieTrailer.getKey());
                }
            });
        }

        private int getScreenWidth() {
            return Resources.getSystem().getDisplayMetrics().widthPixels;
        }
        private int getMeasuredPosterHeight(int width) {
            return (int) (width * 1.5f);
        }

    }




}
