package com.soft.ssvapp.Fragment_Menu.All_Controleur;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.soft.ssvapp.Data.EntityOperationWithEntityMvtCompte;
import com.soft.ssvapp.Data.Entity_MvtCompte;
import com.soft.ssvapp.Data.Entity_Operation;
import com.soft.ssvapp.DataRetrofit.PostControle.PosteControleResponse;
import com.soft.ssvapp.Fragment_Menu.Fill_Rapport.DetailRapport.DetailBalance;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListOperationControleAdapter extends RecyclerView.Adapter<ListOperationControleAdapter.AddAdapter> {

    private ArrayList<EntityOperationWithEntityMvtCompte> arrayList_operation_controle;
    private Context context;
    private TextView textView_total;
    private double total;

    public ListOperationControleAdapter(Context context, TextView textView_total) {
        this.context = context;
        this.arrayList_operation_controle = new ArrayList<>();
        this.textView_total = textView_total;
    }

    @NonNull
    @Override
    public AddAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_operation_controle, parent, false);
        return new AddAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddAdapter holder, int position) {
        Entity_Operation dataOperation = arrayList_operation_controle.get(position).getEntity_operation();
        Entity_MvtCompte dataMvt = arrayList_operation_controle.get(position).getEntity_mvtCompte();

//        Entity_MvtCompte dataMvt = arrayList_operation_controle.get(position);

        Log.e("data from Mvt : ===> ", "\n Code project : "+dataMvt.getCodeProjet()
                +"\n NumceroCompte" + dataMvt.getNumCompte()
                +"\n IdMvt : " + dataMvt.getIdMouvement()
                + "\n Entrée : " + dataMvt.getEntree()
                + "\n Details : " + dataMvt.getDetails()
                + "\n NumeroOperation : " + dataMvt.getNumOperation()
                +  "\n Qte : " + dataMvt.getQte()
                + "\n Sortie" + dataMvt.getSortie());

        holder.textView_matricule.setText(dataOperation.getNumOperationOp());
        holder.textView_designation.setText(dataOperation.getLibelle());
        holder.textView_montant.setText(""+dataMvt.getSortie());
//        holder.linearLayout_operation_controle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, ReleverOperationControle.class)
//                        .putExtra(DetailBalance.NUM_COMPTE, 1234)
//                        .putExtra(DetailBalance.DESIGANTION,
//                                arrayList_operation_controle.get(position).getDetails()+""));
//            }
//        });
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
            textView_designation = itemView.findViewById(R.id.designation_operation_controle);
            textView_matricule = itemView.findViewById(R.id.matricule_operation_controle);
            textView_montant = itemView.findViewById(R.id.montant_operation_controle);
        }
    }

    public void setArrayList_operation_controle(List<EntityOperationWithEntityMvtCompte> list){
//        Toast.makeText(context, "size liste : " +
//                list.size(), Toast.LENGTH_LONG ).show();
        this.arrayList_operation_controle.clear();
        this.arrayList_operation_controle.addAll(list);
        this.total= 0;
        for (int a = 0; a<list.size(); a++){
            this.total += list.get(a).getEntity_mvtCompte().getSortie();
            this.textView_total.setText("$"+this.total);
        }
    }
}
