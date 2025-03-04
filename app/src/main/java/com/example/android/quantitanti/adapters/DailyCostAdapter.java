package com.example.android.quantitanti.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.quantitanti.R;
import com.example.android.quantitanti.database.PicsEntry;
import com.example.android.quantitanti.fragments.PhotosDialogFragment;
import com.example.android.quantitanti.helpers.Helper;
import com.example.android.quantitanti.models.DailyExpenseTagsWithPicsPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.example.android.quantitanti.database.CostEntry.CATEGORY_1;
import static com.example.android.quantitanti.database.CostEntry.CATEGORY_2;
import static com.example.android.quantitanti.database.CostEntry.CATEGORY_3;
import static com.example.android.quantitanti.database.CostEntry.CATEGORY_4;
import static com.example.android.quantitanti.database.CostEntry.CATEGORY_5;
import static com.example.android.quantitanti.database.CostEntry.CATEGORY_6;
import static com.example.android.quantitanti.database.CostEntry.CATEGORY_7;
import static com.example.android.quantitanti.database.CostEntry.CATEGORY_8;
import static com.example.android.quantitanti.database.CostEntry.CATEGORY_9;


public class DailyCostAdapter extends RecyclerView.Adapter<DailyCostAdapter.DailyCostViewHolder> {

    // Member variable to handle item clicks
    final private DailyItemClickListener mDailyItemClickListener;

    // Class variables for the List that holds cost data and the Context
    private List<DailyExpenseTagsWithPicsPojo> mdailyExpenseTagsWithPicsPojos;

    private Context mContext;
    //private CostDatabase mDb;

    public DailyCostAdapter(DailyItemClickListener listener, Context context) {
        mDailyItemClickListener = listener;
        mContext = context;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new DailyCostViewHolder that holds the view for daily costs
     */
    @NonNull
    @Override
    public DailyCostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout to the view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.one_cost_item_view, parent, false);

        return new DailyCostViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(@NonNull final DailyCostViewHolder holder, int position) {
        // Determine the values of the wanted data
        DailyExpenseTagsWithPicsPojo dailyExpenseTagsWithPicsPojo = mdailyExpenseTagsWithPicsPojos.get(position);

        //individual cost on particular day
        String oneCostCategory = dailyExpenseTagsWithPicsPojo.getCostEntry().getCategory();
        String oneCostName = dailyExpenseTagsWithPicsPojo.getCostEntry().getName();
        int oneCostValue = dailyExpenseTagsWithPicsPojo.getCostEntry().getCost();
        String oneCostValueString = Helper.fromIntToDecimalString(oneCostValue);
        String currency = dailyExpenseTagsWithPicsPojo.getCostEntry().getCurrency();
        List<String> oneCostTags = dailyExpenseTagsWithPicsPojo.getTagNames();
        List<PicsEntry> picsEntries = dailyExpenseTagsWithPicsPojo.getPicsEntries();

        holder.iv_getPic.setVisibility(View.GONE);
        Map<String, String> photos = new HashMap<>();
        for (PicsEntry picsEntry : picsEntries) {
            photos.put(picsEntry.getPic_name(), picsEntry.getPic_uri());
        }

        if (!photos.isEmpty()) {
            holder.iv_getPic.setVisibility(View.VISIBLE);
            holder.iv_getPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = ((AppCompatActivity) mContext).getSupportFragmentManager();
                    PhotosDialogFragment photosDialogFragment = PhotosDialogFragment.newInstance(photos);
                    photosDialogFragment.show(fm, "fragment_photo");
                }
            });
        }

        //Set holder values
        holder.tv_costDescription.setText(oneCostName);
        holder.tv_costValue.setText(oneCostValueString);
        holder.tv_costCurrency.setText(currency);

        holder.cg_tags.removeAllViews();
        if (oneCostTags != null) {
            for (String s : oneCostTags) {
                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                Chip chip = (Chip) layoutInflater.inflate(R.layout.single_tag_chip_layout, null);
                chip.setText(s);
                chip.setClickable(false);
                holder.cg_tags.addView(chip);
            }
        }

        // setting imgv depending on category
        holder.imgv_category.setBackgroundResource(R.drawable.ic_img_background_v);
        holder.imgv_category.setColorFilter(mContext.getResources().getColor(R.color.color_primary_light), PorterDuff.Mode.SRC_IN);
        switch (oneCostCategory) {
            case CATEGORY_1:
                //   holder.imgv_category_all.setBackgroundResource(R.drawable.car);
                holder.imgv_category.setImageResource(R.drawable.ic_car);
                break;
            case CATEGORY_2:
                holder.imgv_category.setImageResource(R.drawable.ic_clothes);
                break;
            case CATEGORY_3:
                holder.imgv_category.setImageResource(R.drawable.ic_food);
                break;
            case CATEGORY_4:
                holder.imgv_category.setImageResource(R.drawable.ic_utilities);
                break;
            case CATEGORY_5:
                holder.imgv_category.setImageResource(R.drawable.ic_groceries);
                break;
            case CATEGORY_6:
                holder.imgv_category.setImageResource(R.drawable.ic_education);
                break;
            case CATEGORY_7:
                holder.imgv_category.setImageResource(R.drawable.ic_sport);
                break;
            case CATEGORY_8:
                holder.imgv_category.setImageResource(R.drawable.ic_cosmetics);
                break;
            case CATEGORY_9:
                holder.imgv_category.setImageResource(R.drawable.ic_other);
                break;
            default:
                break;
        }
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mdailyExpenseTagsWithPicsPojos == null) {
            return 0;
        }
        return mdailyExpenseTagsWithPicsPojos.size();
    }

    public List<DailyExpenseTagsWithPicsPojo> getDailyCosts() {
        return mdailyExpenseTagsWithPicsPojos;
    }


    /**
     * When data changes, this method updates the list of costEntries
     * and notifies the adapter to use the new values on it
     */
    public void setmDailyCosts(List<DailyExpenseTagsWithPicsPojo> dailyExpenseTagsWithPicsPojos) {
        mdailyExpenseTagsWithPicsPojos = dailyExpenseTagsWithPicsPojos;
        notifyDataSetChanged();
    }

    public interface DailyItemClickListener {
        void onDailyItemClickListener(int itemId);

        void onDailyItemLongClickListener(int itemId);
    }

    public class DailyCostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView imgv_category;
        TextView tv_costDescription;
        TextView tv_costValue;
        TextView tv_costCurrency;
        ChipGroup cg_tags;
        ImageView iv_getPic;

        /**
         * Constructor for the DailyCostViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public DailyCostViewHolder(@NonNull View itemView) {
            super(itemView);
            // TextViews for itemView
            imgv_category = itemView.findViewById(R.id.imgv_category);
            tv_costDescription = itemView.findViewById(R.id.tv_costDescription);
            tv_costValue = itemView.findViewById(R.id.tv_costValue);
            tv_costCurrency = itemView.findViewById(R.id.tv_costCurrency);
            cg_tags = itemView.findViewById(R.id.cg_tags);
            iv_getPic = itemView.findViewById(R.id.iv_getPic);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int elementId = mdailyExpenseTagsWithPicsPojos.get(getAbsoluteAdapterPosition()).getCostEntry().getId();  //get(getAdapterPosition) -> deprecated
            mDailyItemClickListener.onDailyItemClickListener(elementId);
        }

        @Override
        public boolean onLongClick(View v) {
            int elementId = mdailyExpenseTagsWithPicsPojos.get(getAbsoluteAdapterPosition()).getCostEntry().getId();
            mDailyItemClickListener.onDailyItemLongClickListener(elementId);
            return true;
        }
    }
}
