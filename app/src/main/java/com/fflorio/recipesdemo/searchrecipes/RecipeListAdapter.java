package com.fflorio.recipesdemo.searchrecipes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fflorio.recipesdemo.R;
import com.fflorio.recipesdemo.network.models.Recipe;
import com.fflorio.recipesdemo.network.models.RecipeList;

/**
 * Created by francesco on 2017-09-06.
 */

class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private Recipe[] recipes;

    public RecipeListAdapter(Recipe[] recipes) {
        this.recipes = (recipes != null) ? recipes : new Recipe[0];
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter__recipe_item, parent, false);
        final RecipeViewHolder recipeViewHolder = new RecipeViewHolder(v);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.label.setText(recipes[position].toString());
    }

    @Override
    public int getItemCount() {
        return recipes.length;
    }

    public void updateDataset(final RecipeList recipeList){
        recipes =  (recipeList != null && recipeList.recipes != null )
                        ? recipeList.recipes
                        : new Recipe[0];
        notifyDataSetChanged();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder{

        private TextView label;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.tv_label);
        }
    }
}
