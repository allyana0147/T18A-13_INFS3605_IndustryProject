package com.example.infs3605_industry_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> implements Filterable {

    //array list of all movies
    private List<Language> mLanguages;
    private List<Language> mLanguagesFiltered;
    private Context mContext;
    DataSnapshot dataSnapshot;
    View mView;
    private LanguageAdapter.RecyclerViewClickListener mListener;

    public LanguageAdapter(Context context, List<Language> languages, LanguageAdapter.RecyclerViewClickListener listener) {
        mContext= context;
        mLanguages = languages;
        mLanguagesFiltered = languages;
        mListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    mLanguagesFiltered = mLanguages;
                } else {
                    List<Language> filteredList = new ArrayList<>();
                    for(Language language : mLanguages) {
                        if(language.getLanguage().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(language);
                        }
                    }
                    mLanguagesFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mLanguagesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mLanguagesFiltered = (List<Language>) results.values;
                notifyDataSetChanged();
            }
        };

    }

    public interface RecyclerViewClickListener {
        void onClick(View view, String id);
    }


    @NonNull
    @Override
    public LanguageAdapter.LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_language_row, parent, false);
        return new LanguageAdapter.LanguageViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageAdapter.LanguageViewHolder holder, int position) {
        Language language = mLanguagesFiltered.get(position);
        holder.tvLanguage.setText(language.getLanguage());
        holder.itemView.setTag(language.getLanguage_id());

    }

    @Override
    public int getItemCount() {
        return mLanguagesFiltered.size();
    }

    public class LanguageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvLanguage;
        private LanguageAdapter.RecyclerViewClickListener listener;


        public LanguageViewHolder(@NonNull View itemView, LanguageAdapter.RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            tvLanguage = itemView.findViewById(R.id.tv_dictionary_language);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (String) v.getTag());
        }

    }
}
