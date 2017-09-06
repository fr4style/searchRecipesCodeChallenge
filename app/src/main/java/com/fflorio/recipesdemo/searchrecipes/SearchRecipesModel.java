package com.fflorio.recipesdemo.searchrecipes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fflorio.recipesdemo.network.WSRecepies;
import com.fflorio.recipesdemo.network.models.Recipe;
import com.fflorio.recipesdemo.network.models.RecipeList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by francesco on 2017-09-06.
 */

public class SearchRecipesModel implements SearchRecipesMVP.Model {

    private RecipeList recipeList;
    private WSRecepies wsRecepies;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchRecipesModel() {
        wsRecepies = WSRecepies.create();
    }

    @Override
    public void search(@NonNull final String query,
                       @NonNull final RecipeListRequestCallback callback) {
        final Observable<RecipeList> queryPage1 = wsRecepies.search(query, "1");
        final Observable<RecipeList> queryPage2 = wsRecepies.search(query, "2");
        compositeDisposable.clear();
        compositeDisposable.add(
                queryPage1.flatMap(new Function<RecipeList, ObservableSource<RecipeList>>() {
                    @Override
                    public ObservableSource<RecipeList> apply(@io.reactivex.annotations.NonNull RecipeList recipeList) throws Exception {
                        return recipeList!=null && recipeList.recipes != null && recipeList.recipes.length == 10
                                ? queryPage1.mergeWith(queryPage2)
                                : queryPage1;
                    }
                })
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribeWith(new DisposableObserver<RecipeList>() {
                     private RecipeList currentRecipeList;
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull RecipeList recipeList) {
                        if(currentRecipeList == null){
                            currentRecipeList = recipeList;
                        }else{
                            currentRecipeList = merge(currentRecipeList.recipes, recipeList.recipes);
                        }
                    }

                    private RecipeList merge(final Recipe[] currentRecipes, final Recipe[] newRecipes){
                        final Recipe[] recipes = new Recipe[currentRecipes.length + newRecipes.length];
                        int i = 0;
                        for(Recipe recipe : currentRecipes){
                            recipes[i++] = recipe;
                        }
                        for(Recipe recipe : newRecipes){
                            recipes[i++] = recipe;
                        }
                        return new RecipeList(recipes);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        callback.onComplete(null, e);
                    }

                    @Override
                    public void onComplete() {
                        recipeList = currentRecipeList;
                        callback.onComplete(recipeList, null);
                    }
                })
        );
    }

    @Override
    @Nullable
    public RecipeList getLastResponse() {
        return recipeList;
    }
}
