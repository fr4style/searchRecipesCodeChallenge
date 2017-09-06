package com.fflorio.recipesdemo.searchrecipes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.fflorio.recipesdemo.network.models.Recipe;
import com.fflorio.recipesdemo.network.models.RecipeList;

/**
 * Created by francesco on 2017-09-06.
 */

public interface SearchRecipesMVP {

    interface View{
        void showRecipes(@NonNull final RecipeList recipeList);
        void showError(@StringRes final int errorMessageResId);
    }

    interface Model{
        interface RecipeListRequestCallback{
            void onComplete(@Nullable final RecipeList recipeList, @Nullable Throwable e);
        }

        void search(@NonNull final String query, @NonNull final RecipeListRequestCallback callback);

        @Nullable RecipeList getLastResponse();
    }

    interface Presenter extends Model.RecipeListRequestCallback{
        void search(@NonNull final String query);
        void saveState(@NonNull final Bundle outState);
        Recipe[] restoreState(@Nullable final Bundle savedInstanceState);
    }
}
