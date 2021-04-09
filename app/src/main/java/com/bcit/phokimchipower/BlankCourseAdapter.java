package com.bcit.phokimchipower;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class BlankCourseAdapter extends RecyclerView.Adapter<BlankCourseAdapter.BlankViewHolder> {

    @NonNull
    @Override
    public BlankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_insertion, parent, false);
        BlankViewHolder bvh = new BlankViewHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BlankViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static  class BlankViewHolder extends RecyclerView.ViewHolder {

        public Spinner spinner1;
        public Spinner spinner2;
        public Spinner spinner3;
        public Spinner spinner4;
        public Spinner spinner5;
        public Spinner spinner6;


        public BlankViewHolder(@NonNull View itemView) {
            super(itemView);
            spinner1 = itemView.findViewById(R.id.spinner_evalType1);
            spinner2 = itemView.findViewById(R.id.spinner_evalType2);
            spinner3 = itemView.findViewById(R.id.spinner_evalType3);
            spinner4 = itemView.findViewById(R.id.spinner_evalType4);
            spinner5 = itemView.findViewById(R.id.spinner_evalType5);
            spinner6 = itemView.findViewById(R.id.spinner_evalType6);

        }
    }

}
