package edu.upc.dsa.minimo2dsa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<Repos> reposList;

    public MyAdapter(List<Repos> reposList) {
        this.reposList = reposList;
    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        //false es que no es el raiz
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        final Repos repositorio = reposList.get(position);
        holder.txtNom.setText(reposList.get(position).getName());
        holder.txtLanguage.setText(reposList.get(position).getLanguage());

    }

    @Override
    public int getItemCount() {
        return reposList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
       // public ImageView imagen;
        public TextView txtNom;
        public TextView txtLanguage;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtNom = v.findViewById(R.id.firstLine);
            txtLanguage = v.findViewById(R.id.secondLine);
            //imagen = v.findViewById(R.id.icon);
        }
    }









}
