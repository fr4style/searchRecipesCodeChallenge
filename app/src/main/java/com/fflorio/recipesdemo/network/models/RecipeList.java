package com.fflorio.recipesdemo.network.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by francesco on 2017-09-06.
 */

public class RecipeList {
    @SerializedName("results") public final Recipe[] recipes;

    public RecipeList(Recipe[] recipes) {
        this.recipes = recipes;
    }


}
