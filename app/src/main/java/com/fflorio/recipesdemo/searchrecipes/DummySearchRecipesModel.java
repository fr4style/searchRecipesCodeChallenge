package com.fflorio.recipesdemo.searchrecipes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fflorio.recipesdemo.network.models.Recipe;
import com.fflorio.recipesdemo.network.models.RecipeList;

import java.util.Locale;

/**
 * Created by francesco on 2017-09-06.
 */

public class DummySearchRecipesModel implements SearchRecipesMVP.Model {

    private RecipeList recipeList;

    @Override
    public void search(@NonNull String query, @NonNull RecipeListRequestCallback callback) {
        final Recipe[] recipes = new Recipe[query.length()];
        for(int i=0; i<query.length(); i++){
            recipes[i] = new Recipe(String.format(Locale.getDefault(), "Recipe %d", i+1));
        }
        recipeList = new RecipeList(recipes);

        callback.onComplete(recipeList, null);
    }

    @Override public RecipeList getLastResponse(){
        return recipeList;
    }


}
