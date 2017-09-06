package com.fflorio.recipesdemo.network;

import com.fflorio.recipesdemo.network.models.RecipeList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by francesco on 2017-09-06.
 */

public interface WSRecipesDefinitions {

    @GET("api/")
    Observable<RecipeList> search(@Query("q") String query,
                                  @Query("p") String page);
}
