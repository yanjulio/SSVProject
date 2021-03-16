package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import com.soft.ssvapp.Data.Entity_Article;
import com.soft.ssvapp.DataRetrofit.Article.ArticleRetrofitRepository;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class ArticleDialogAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Entity_Article> arrayListAdapter_article;
    ArticleRetrofitRepository articleRetrofitRepository;

    public ArticleDialogAdapter(Context context)
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
            convertView = inflater.inflate(R.layout.model_article_dialog, null);

            holdView.textView_designation = convertView.findViewById(R.id.textView_model_article_dialog);
            holdView.linearLayout_article = convertView.findViewById(R.id.lienear_article_model_dialog);
            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_designation.setText("" + arrayListAdapter_article.get(position).getDesignationArticle());

//        holdView.linearLayout_article.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AjouterDetailBesoinActive ajouter = (AjouterDetailBesoinActive)context;
//                Entity_Article entity_article = new Entity_Article(arrayListAdapter_article.get(position).getCodeArticle(), "",
//                        arrayListAdapter_article.get(position).getDesignationArticle(),
//                        arrayListAdapter_article.get(position).getCategorieArticle(),
//                        arrayListAdapter_article.get(position).getPrixAchat(), arrayListAdapter_article.get(position).getPrixVente(),
//                        "", 0, 0, 0, 0
//                        );
//                ajouter.setCodeArticle_and_codeArticle(entity_article);
////                ArticleDialog articleDialog = new ArticleDialog();
//                ajouter.closeDialog(true);
//            }
//        });

        return convertView;
    }

    public void setArticle(List<Entity_Article> entity_articles)
    {
        arrayListAdapter_article.clear();
        arrayListAdapter_article.addAll(entity_articles);
        ArticleDialog.list_local.clear();
        ArticleDialog.list_local.addAll(entity_articles);
    }
}
