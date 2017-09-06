package com.fflorio.recipesdemo.network;

import android.support.annotation.NonNull;

import com.fflorio.recipesdemo.network.models.RecipeList;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by francesco on 2017-09-06.
 */

public class WSRecepies extends WS{

    private final WSRecipesDefinitions wsRecipesDefinitions;

    public static WSRecepies create(){
        final Retrofit retrofit = createDefaultRetrofitConfiguration();
        return new WSRecepies(retrofit.create(WSRecipesDefinitions.class));
    }

    WSRecepies(final WSRecipesDefinitions wsRecipesDefinitions){
        this.wsRecipesDefinitions = wsRecipesDefinitions;
    }

    public Observable<RecipeList> search(@NonNull final String query, @NonNull final String page){
        return wsRecipesDefinitions.search(query, page);
    }
}
