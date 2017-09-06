package com.fflorio.recipesdemo.searchrecipes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fflorio.recipesdemo.R;
import com.fflorio.recipesdemo.network.models.Recipe;
import com.fflorio.recipesdemo.network.models.RecipeList;

/**
 * Created by francesco on 2017-09-06.
 */

public class SearchRecipesPresenter implements SearchRecipesMVP.Presenter {

    private static final String RESULTS = "RESULTS";

    private final SearchRecipesMVP.View view;
    private final SearchRecipesMVP.Model searchRecipesModel;

    SearchRecipesPresenter(final SearchRecipesMVP.View view) {
        this.view = view;
        searchRecipesModel = new SearchRecipesModel();
    }

    @Override
    public void onComplete(@Nullable final RecipeList recipeList, @Nullable final Throwable throwable) {
        if(recipeList != null){
            view.showRecipes(recipeList);
        }else{
            view.showError(R.string.generic_error);
        }
    }

    @Override
    public void search(String query) {
        searchRecipesModel.search(query, this);
    }

    @Override
    public void saveState(@NonNull Bundle outState) {
        final RecipeList recipeList = searchRecipesModel.getLastResponse();
        if(recipeList!=null) {
            outState.putParcelableArray(RESULTS, recipeList.recipes);
        }
    }

    @Override
    public Recipe[] restoreState(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState!=null) {
            return (Recipe[])savedInstanceState.getParcelableArray(RESULTS);
        }
        return null;
    }
}
