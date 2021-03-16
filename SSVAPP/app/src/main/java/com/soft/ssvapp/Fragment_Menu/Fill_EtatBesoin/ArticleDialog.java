package com.soft.ssvapp.Fragment_Menu.Fill_EtatBesoin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.soft.ssvapp.Data.Entity_Article;
import com.soft.ssvapp.Fragment_Menu.FillArticle.ArticleViewModel;
import com.soft.ssvapp.R;

import java.util.ArrayList;
import java.util.List;

public class ArticleDialog extends DialogFragment {

    ArticleDialogAdapter adapter;
    AlertDialog.Builder builder;
    public static ArrayList<Entity_Article> list_local = new ArrayList<>();
    private ListView listView_article;
    private SearchView searchView;
    private boolean val1=false;

    ArticleViewModel articleViewModel;

    public ArticleDialog()
    {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        articleViewModel = ViewModelProviders.of(getActivity()).get(ArticleViewModel.class);

        View view = getActivity().getLayoutInflater().inflate(R.layout.article_dialog, null);
        listView_article = view.findViewById(R.id.listView_dialog_article);
        searchView = view.findViewById(R.id.search_dialog_article);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(onQueryTextListener);
        adapter = new ArticleDialogAdapter(getActivity());
//        setList();
        listView_article.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AjouterDetailBesoinActive ajouter = (AjouterDetailBesoinActive)getActivity();
                Entity_Article entity_article = new Entity_Article(list_local.get(position).getCodeArticle(), "",
                        list_local.get(position).getDesignationArticle(),
                        list_local.get(position).getCategorieArticle(),
                        list_local.get(position).getPrixAchat(), list_local.get(position).getPrixVente(),
                        "", 0, 0, 0, 0
                );
                ajouter.setCodeArticle_and_codeArticle(entity_article);
                dismiss();
            }
        });

        builder.setView(view);

        articleViewModel.getGetAllArticle().observe(getActivity(), new Observer<List<Entity_Article>>() {
            @Override
            public void onChanged(List<Entity_Article> entity_articles) {
                adapter.setArticle(entity_articles);
                listView_article.setAdapter(adapter);
            }
        });

        return builder.create();
    }

    public void setList()
    {
        articleViewModel.getGetAllArticle().observe(getActivity(), new Observer<List<Entity_Article>>() {
            @Override
            public void onChanged(List<Entity_Article> rapportOperationResponses) {
                adapter.setArticle(rapportOperationResponses);
                listView_article.setAdapter(adapter);
            }
        });
    }

    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            getArticleFromDb(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            getArticleFromDb(newText);
            return true;
        }

        private void getArticleFromDb(String searchQuery)
        {
            searchQuery = "%"+searchQuery+"%";
            articleViewModel.getArticleSearchQuery(searchQuery).observe(getActivity(), new Observer<List<Entity_Article>>() {
                @Override
                public void onChanged(List<Entity_Article> entity_articles) {
                    if (entity_articles == null)
                    {
                        return;
                    }
                    adapter.setArticle(entity_articles);
                    listView_article.setAdapter(adapter);
                }
            });
        }
    };
}
