package com.mohammedaliyu.smartswitchlite;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.LightHolder> {

    private List<Light> lights = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public LightHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.light_item, parent, false);
        return new LightHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LightHolder holder, int position) {
        Light currentLight = lights.get(position);
        holder.textViewLight.setText(currentLight.getName());
        Log.d("lighttttt", currentLight.getName());
    }

    @Override
    public int getItemCount() {
        return lights.size();
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
        notifyDataSetChanged();
    }

    public Light getLightAt(int position) {
        return lights.get(position);
    }


    class LightHolder extends RecyclerView.ViewHolder {
        private TextView textViewLight;

        public LightHolder(@NonNull View itemView) {
            super(itemView);
            textViewLight = itemView.findViewById(R.id.text_view_light);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(lights.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Light light);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
