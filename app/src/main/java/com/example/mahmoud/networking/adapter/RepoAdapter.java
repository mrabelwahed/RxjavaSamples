package com.example.mahmoud.networking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mahmoud.networking.model.Repo;
import com.example.mahmoud.samples.R;

import java.util.List;

/**
 * Created by mahmoud on 20/03/18.
 */

public class RepoAdapter  extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Repo> repos;

    public  RepoAdapter(Context context , List<Repo>repos){
        this.context = context;
        this.repos = repos;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RepoAdapter.RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.repo_item,parent,false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepoAdapter.RepoViewHolder holder, int position) {
        Repo repo = repos.get(position);
        holder.setItemContent(repo);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder{

        TextView tvRepoName,tvRepoId;

        public RepoViewHolder(View itemView) {
            super(itemView);
            tvRepoName = (TextView) itemView.findViewById(R.id.repo_name);
            tvRepoId = (TextView) itemView.findViewById(R.id.repo_id);
        }

        void setItemContent(Repo repo){
            tvRepoName.setText(repo.getName());
            tvRepoId.setText(repo.getId());
        }
    }
}
