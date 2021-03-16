package com.soft.ssvapp.Data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class Entity_DetailBesoinWithEntity_Article {
    @Embedded
    Entity_DetailBesoin detailBesoin;
    @Embedded
    Entity_Article entity_article;

    @Relation(
            parentColumn = "CodeArticleDetailBesoin",
            entityColumn = "CodeArticle"
    )
    List<Entity_Article> entity_articles;

    public Entity_DetailBesoin getDetailBesoin() {
        return detailBesoin;
    }

    public Entity_Article getEntity_article()
    {
        return entity_article;
    }
}
