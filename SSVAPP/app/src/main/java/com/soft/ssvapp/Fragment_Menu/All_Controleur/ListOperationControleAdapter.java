package com.soft.ssvapp.Fragment_Menu.All_Controleur;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.soft.ssvapp.DataRetrofit.PostControle.PosteControleResponse;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.DetailRapport.DetailBalance;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListOperationControleAdapter extends RecyclerView.Adapter<ListOperationControleAdapter.AddAdapter> {

    private ArrayList<PosteControleResponse> arrayList_operation_controle;
    private Context context;

    public ListOperationControleAdapter(Context context) {
        this.context = context;
        this.arrayList_operation_controle = new ArrayList<>();
    }

    @NonNull
    @Override
    public AddAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_operation_controle, parent, false);
        return new AddAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddAdapter holder, int position) {
//        PosteControleResponse data = arrayList_operation_controle.get(position);
        ArrayList<PosteControleResponse> data = PosteControleResponse.getList();

        holder.textView_matricule.setText(data.get(position).getMatricule_operation());
        holder.textView_designation.setText(data.get(position).getDesignation());
        holder.textView_montant.setText(""+data.get(position).getMontant());
        holder.linearLayout_operation_controle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ReleverOperationControle.class)
                        .putExtra(DetailBalance.NUM_COMPTE, arrayList_operation_controle.get(position).getCompte())
                        .putExtra(DetailBalance.DESIGANTION, arrayList_operation_controle.get(position).getDesignation()+""));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList_operation_controle.size();
    }

    class AddAdapter extends RecyclerView.ViewHolder{

        LinearLayout linearLayout_operation_controle;
        TextView textView_matricule, textView_designation, textView_montant;

        public AddAdapter(@NonNull View itemView) {
            super(itemView);
            linearLayout_operation_controle = itemView.findViewById(R.id.linear_operation_controle);
            textView_matricule = itemView.findViewById(R.id.matricule_operation_controle);
            textView_montant = itemView.findViewById(R.id.montant_operation_controle);
        }
    }

    public void setArrayList_operation_controle(List<PosteControleResponse> list){
        this.arrayList_operation_controle.clear();
        this.arrayList_operation_controle.addAll(list);
    }
}
