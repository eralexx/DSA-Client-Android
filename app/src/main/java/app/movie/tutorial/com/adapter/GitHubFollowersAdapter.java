package app.movie.tutorial.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.model.GitHubUserResponse;



public class GitHubFollowersAdapter extends RecyclerView.Adapter<GitHubFollowersAdapter.FollowerViewHolder> {

    private List<GitHubUserResponse> followers;
    private int rowLayout;
    private Context context;
    public static final String IMAGE_URL_BASE_PATH="";

    public GitHubFollowersAdapter(List<GitHubUserResponse> followers, int rowLayout, Context context) {
        this.followers = followers;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    //A view holder inner class where we get reference to the views in the layout using their ID

    public static class FollowerViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView userName;
        ImageView userImage;

        public FollowerViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.profile_layout);
            userImage = (ImageView) v.findViewById(R.id.imageView);
            userName = (TextView) v.findViewById(R.id.title);
        }
    }


    @Override
    public GitHubFollowersAdapter.FollowerViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new FollowerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(FollowerViewHolder holder, final int position) {
        String image_url = IMAGE_URL_BASE_PATH + followers.get(position).getAvatar_url();
        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.userImage);
        holder.userName.setText(followers.get(position).getLogin());
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }
}
