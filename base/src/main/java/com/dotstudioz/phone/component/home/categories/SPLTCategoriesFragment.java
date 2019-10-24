package com.dotstudioz.phone.component.home.categories;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.model.SPLTChannel;
import com.dotstudioz.model.SPLTCompany;
import com.dotstudioz.phone.R;
import com.dotstudioz.phone.adapter.SPLTCategoryAdapter;
import com.dotstudioz.phone.baseclasses.SPLTBaseFragment;
import com.dotstudioz.phone.component.home.channels.SPLTChannelsFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class SPLTCategoriesFragment extends SPLTBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    protected String TAG = "SPLTCategoriesFragment";

    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    protected MenuItem searchItem;

    protected RecyclerView recyclerView;
    protected SPLTCategoryAdapter categoryAdapter;
    public List<SPLTCategory> mCategoryList = new ArrayList<>();
    protected int cardViewHeight = 120;
    protected int cardViewwidth = 120;
    protected int cellMargin = 5;
    protected int numberOfColumns = 3;

    public Callback callback;
    public interface Callback {
        void onCategoryClick(SPLTCategory category);
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public SPLTCategoriesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SPLTCategoriesFragment newInstance(String param1, String param2) {
        SPLTCategoriesFragment fragment = new SPLTCategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        showMenu();
        setUserVisibleHint(true);
        View mView = init(inflater,container,savedInstanceState);
        return mView;
    }

    protected void showMenu(){
        setHasOptionsMenu(true);
    }
    protected View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        loadList();

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //onCreateOptionsMenuDS(menu,inflater);
        if (this.searchItem != null) {
            this.searchItem.setVisible(true);
            this.searchItem.setEnabled(true);

            SearchView searchView = null;
            searchView = (SearchView) this.searchItem.getActionView();
            /*final SearchView finalSearchView = searchView;
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Toast like print
                    Toast.makeText(getActivity(),"Search "+query, Toast.LENGTH_SHORT).show();
                    if( ! finalSearchView.isIconified()) {
                        finalSearchView.setIconified(true);
                    }
                    searchItem.collapseActionView();
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                    // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                    return false;
                }
            });*/

            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSearchFragment();
                }
            });
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            Log.e("App","@@@ CategoriesFragment setUserVisibleHint");
            //getFragmentManager().beginTransaction().detach(this).attach(this).commit();


        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        //super.onHiddenChanged(hidden);
        if (hidden) {
            //do when hidden
            Log.e("App","@@@ CategoriesFragment onHiddenChanged true");
        } else {
            //do when show
            Log.e("App","@@@ CategoriesFragment onHiddenChanged false");
            reloadUI();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public void loadList(){ }

    public void reloadUI(){
//list of all category show into category data

        Log.d(TAG, "onViewCreated: "+this.mCategoryList.size());
        if(this.categoryAdapter != null ){
            this.categoryAdapter.refreshCategoryList(this.mCategoryList);
            //categoryAdapter.notifyDataSetChanged();
            //this.recyclerView.invalidate();
        }
        //loadList();
    }
    public void showSearchFragment(){}
}
