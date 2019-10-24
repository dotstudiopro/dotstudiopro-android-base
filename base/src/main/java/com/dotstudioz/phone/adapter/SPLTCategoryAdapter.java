package com.dotstudioz.phone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.dotstudioz.model.SPLTCategory;
import java.util.List;

public class SPLTCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public String TAG ="CategoryAdapter";
    public List<SPLTCategory> mCategoryList;
    public Context context;
    public int cardViewHeight = 120;
    public int cardViewwidth = 120;


    // for category list
    public SPLTCategoryAdapter(Context context, List<SPLTCategory> categoryList, int cardViewHeight, int cardViewwidth){
        //for category adapter
        this.context = context;
        this.mCategoryList = categoryList;
        this.cardViewHeight = cardViewHeight;
        this.cardViewwidth = cardViewwidth;
    }

   @NonNull
   @Override
   public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       return null;
   }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        SPLTCategoryViewHolder spltCategoryViewHolder = (SPLTCategoryViewHolder)viewHolder;
        spltCategoryViewHolder.setCategory(this.mCategoryList.get(position));
    }


    @Override
    public int getItemCount() {
        if(this.mCategoryList != null && this.mCategoryList.size()>0){
            return this.mCategoryList.size();
        }else return 0;
    }

    public void refreshCategoryList(List<SPLTCategory> adapterList){
        this.mCategoryList.clear();
        this.mCategoryList.addAll(adapterList);
        this.notifyDataSetChanged();
    }
}
