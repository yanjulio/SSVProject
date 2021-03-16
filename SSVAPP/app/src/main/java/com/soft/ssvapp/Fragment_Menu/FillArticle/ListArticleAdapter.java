package com.soft.ssvapp.Fragment_Menu.FillArticle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.soft.ssvapp.Data.Entity_Article;
import com.soft.ssvapp.DataRetrofit.Article.ArticleRetrofitRepository;
import com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin.AjouterArticle;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListArticleAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
//    private int resource_itemResultLayout;
    private ArrayList<Entity_Article> arrayListAdapter_article;
    ArticleRetrofitRepository articleRetrofitRepository;

    public ListArticleAdapter(Context context)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.arrayListAdapter_article = new ArrayList<Entity_Article>();
        articleRetrofitRepository = ArticleRetrofitRepository.getInstance();
    }

    class HoldView
    {
        TextView textView_designation, textView_prixAchat, textView_prixVente;
        LinearLayout linearLayout_article;
    }

    @Override
    public int getCount() {
        return arrayListAdapter_article.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListAdapter_article.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HoldView holdView;

        if (convertView == null)
        {
            holdView = new HoldView();
            convertView = inflater.inflate(R.layout.model_list_article, null);

            holdView.textView_designation = convertView.findViewById(R.id.textView_article_model_designation);
            holdView.textView_prixAchat = convertView.findViewById(R.id.textView_article_model_prixAchat);
            holdView.textView_prixVente = convertView.findViewById(R.id.textView_article_model_prixVente);
            holdView.linearLayout_article = convertView.findViewById(R.id.linear_article_model);
            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_designation.setText("" + arrayListAdapter_article.get(position).getDesignationArticle());
        holdView.textView_prixAchat.setText("" + arrayListAdapter_article.get(position).getPrixAchat());
        holdView.textView_prixVente.setText("" + arrayListAdapter_article.get(position).getPrixVente());

        holdView.linearLayout_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListArticle listArticle = (ListArticle)context;
                listArticle.startActivity(new Intent(context, AjouterArticle.class)
                        .putExtra(AjouterArticle.MODIFIERARTICLE, "Modifier")
                        .putExtra(AjouterArticle.IDARTICLE, arrayListAdapter_article.get(position).getIdArticle())
                        .putExtra(AjouterArticle.CODEARTICLE, arrayListAdapter_article.get(position).getCodeArticle()));
            }
        });

        holdView.linearLayout_article.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setMessage("Voulez-vous supprimer " + arrayListAdapter_article.get(position).getDesignationArticle());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Entity_Article entity_article = new Entity_Article(arrayListAdapter_article.get(position).getCodeArticle(),
                                arrayListAdapter_article.get(position).getCodeDepartement(),
                                arrayListAdapter_article.get(position).getDesignationArticle(),
                                arrayListAdapter_article.get(position).getCategorieArticle(),
                                arrayListAdapter_article.get(position).getPrixAchat(),
                                arrayListAdapter_article.get(position).getPrixVente(),
                                arrayListAdapter_article.get(position).getUniteEnDEtaille(),
                                arrayListAdapter_article.get(position).getQteEnDet(),
                                arrayListAdapter_article.get(position).getEnStock(),
                                arrayListAdapter_article.get(position).getSolde(),
                                arrayListAdapter_article.get(position).getComptefournisseur());
                        entity_article.setIdArticle(arrayListAdapter_article.get(position).getIdArticle());
                        ListArticle delete_article = (ListArticle) context;
                        delete_article.deleteArticle(entity_article);
                    }
                });
                builder.show();
                return false;
            }
        });


        return convertView;
    }

    public void setArticle(List<Entity_Article> entity_articles)
    {
        arrayListAdapter_article.clear();
        arrayListAdapter_article.addAll(entity_articles);
    }
}
