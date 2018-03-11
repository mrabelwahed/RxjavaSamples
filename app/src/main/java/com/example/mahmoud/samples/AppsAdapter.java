package com.example.mahmoud.samples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mahmoud on 08/03/18.
 */

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder>{

        private List<AppInfo> mApplications;

        private int mRowLayout;
        private Context context;

        public AppsAdapter(List<AppInfo> applications, int rowLayout) {
            mApplications = applications;
            mRowLayout = rowLayout;
        }

        public void addApplications(List<AppInfo> applications) {
            mApplications.clear();
            mApplications.addAll(applications);
            notifyDataSetChanged();
        }

        public void addApplication(int position, AppInfo appInfo) {
            if (position < 0) {
                position = 0;
            }
            mApplications.add(position, appInfo);
            notifyItemInserted(position);
        }

        public List<AppInfo> getmApplications(){
            return mApplications;
        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
           context = viewGroup.getContext();
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(mRowLayout, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, int i) {
            final AppInfo appInfo = mApplications.get(i);
            viewHolder.name.setText(appInfo.getName());
            getBitmap(appInfo.getIcon())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(viewHolder.image::setImageBitmap);
        }

    private Observable<Bitmap> getBitmap(String icon) {
        return Observable.create(subscriber -> {
            subscriber.onNext(BitmapFactory.decodeFile(icon));
            subscriber.onComplete();
        });
    }

        @Override
        public int getItemCount() {
            return mApplications == null ? 0 : mApplications.size();
        }



        public static class ViewHolder extends RecyclerView.ViewHolder {

            public TextView name;

            public ImageView image;

            public ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                image = (ImageView) itemView.findViewById(R.id.image);
            }
        }

}
