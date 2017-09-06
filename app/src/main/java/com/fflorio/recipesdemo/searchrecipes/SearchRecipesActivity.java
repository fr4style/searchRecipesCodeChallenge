package com.fflorio.recipesdemo.searchrecipes;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.fflorio.recipesdemo.R;
import com.fflorio.recipesdemo.network.models.RecipeList;

public class SearchRecipesActivity extends AppCompatActivity implements SearchRecipesMVP.View {

    private SearchView search;
    private RecyclerView recipeList;
    private RecipeListAdapter recipeListAdapter;
    private SearchRecipesMVP.Presenter presenter;
    private Toast toastError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__search);
        presenter = new SearchRecipesPresenter(this);

        search = findViewById(R.id.sv_search);
        recipeList = findViewById(R.id.rv_recipelist);

        recipeListAdapter = new RecipeListAdapter(presenter.restoreState(savedInstanceState));
        recipeList.setHasFixedSize(true);
        recipeList.setLayoutManager(new LinearLayoutManager(this));
        recipeList.setAdapter(recipeListAdapter);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()){
                    recipeListAdapter.updateDataset(null);
                }else {
                    presenter.search(newText);
                }
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        presenter.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showRecipes(RecipeList recipeList) {
        recipeListAdapter.updateDataset(recipeList);
    }

    @Override
    public void showError(@StringRes int errorMessageResId) {
        if(toastError!=null){
            toastError.cancel();
        }
        toastError = Toast.makeText(this, errorMessageResId, Toast.LENGTH_SHORT);
        toastError.show();
        recipeListAdapter.updateDataset(null);
    }
}
