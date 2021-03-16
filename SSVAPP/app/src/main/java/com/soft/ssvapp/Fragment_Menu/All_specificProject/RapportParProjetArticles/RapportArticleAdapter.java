package com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportParProjetArticles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.soft.ssvapp.DataRetrofit.Article.ArticleRetrofitRepository;
import com.soft.ssvapp.DataRetrofit.RapportParProjet.RapportArticleResponse;
import com.soft.ssvapp.Fragment_Menu.All_specificProject.RapportLignePartArticle.RapportLigneParArticle;
import com.soft.ssvapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RapportArticleAdapter extends BaseAdapter implements Filterable {

    private LayoutInflater inflater;
    private Context context;
    //    private int resource_itemResultLayout;
    private ArrayList<RapportArticleResponse> arrayListAdapter_article;
    private ArrayList<RapportArticleResponse> mfilteredList;
    ArticleRetrofitRepository articleRetrofitRepository;
    private String codeProjet ="";

    public RapportArticleAdapter(Context context, String codeProjet)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.arrayListAdapter_article = new ArrayList<RapportArticleResponse>();
        this.mfilteredList = new ArrayList<RapportArticleResponse>();
        articleRetrofitRepository = ArticleRetrofitRepository.getInstance();
        this.codeProjet = codeProjet;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mfilteredList = arrayListAdapter_article;
                } else {

                    ArrayList<RapportArticleResponse> filteredList = new ArrayList<>();

                    for (RapportArticleResponse articleResponse : arrayListAdapter_article) {

                        if (articleResponse.getDesegnationArticle().toLowerCase().contains(charString)) {

                            filteredList.add(articleResponse);
                        }
                    }

                    mfilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mfilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                mfilteredList = (ArrayList<RapportArticleResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class HoldView
    {
        TextView textView_designation, textView_qte, textView_pu, textView_total;
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
            convertView = inflater.inflate(R.layout.model_list_article_rapport, null);

            holdView.textView_designation = convertView.findViewById(R.id.textView_article_model_designation_rapport);
            holdView.textView_qte = convertView.findViewById(R.id.textView_article_model_qte_rapport);
            holdView.textView_pu = convertView.findViewById(R.id.textView_article_model_pu_rapport);
            holdView.textView_total = convertView.findViewById(R.id.textView_article_model_total_rapport);
            holdView.linearLayout_article = convertView.findViewById(R.id.linear_article_model_rapport);
            convertView.setTag(holdView);
        }
        else
        {
            holdView = (HoldView)convertView.getTag();
        }

        holdView.textView_designation.setText("" + arrayListAdapter_article.get(position).getDesegnationArticle());
        holdView.textView_qte.setText("" + new DecimalFormat("##.##").format(arrayListAdapter_article.get(position).getQte()));
        holdView.textView_pu.setText("" + new DecimalFormat("##.##").format(arrayListAdapter_article.get(position).getPu()));
        holdView.textView_total.setText("" +
                new DecimalFormat("##.##").format(arrayListAdapter_article.get(position).getTotalConsommation()));

        holdView.linearLayout_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity app = (AppCompatActivity)context;
                app.startActivity(new Intent(context, RapportLigneParArticle.class)
                        .putExtra(RapportLigneParArticle.CODE_PROJET, codeProjet)
                        .putExtra(RapportLigneParArticle.CODE_ARTICLE, arrayListAdapter_article.get(position).getCodeArticle())
                        .putExtra(RapportLigneParArticle.DESIGNATION_ARTICLE, arrayListAdapter_article.get(position).getDesegnationArticle()));
            }
        });

        return convertView;
    }

    public void setArticle(List<RapportArticleResponse> entity_articles)
    {
        arrayListAdapter_article.clear();
        arrayListAdapter_article.addAll(entity_articles);
    }
}
