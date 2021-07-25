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

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder> implements Filterable {
    //array list of all movies
    private List<Dictionary> mDictionary;
    private List<Dictionary> mDictionaryFiltered;
    private Context mContext;
    DataSnapshot dataSnapshot;
    View mView;
    private DictionaryAdapter.RecyclerViewClickListener mListener;

    public DictionaryAdapter(Context context, List<Dictionary> dictionaries, DictionaryAdapter.RecyclerViewClickListener listener) {
        mContext= context;
        mDictionary = dictionaries;
        mDictionaryFiltered = dictionaries;
        mListener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    mDictionaryFiltered = mDictionary;
                } else {
                    List<Dictionary> filteredList = new ArrayList<>();
                    for(Dictionary dictionary : mDictionary) {
                        if(dictionary.getWord().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(dictionary);
                        }
                    }
                    mDictionaryFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDictionaryFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDictionaryFiltered = (List<Dictionary>) results.values;
                notifyDataSetChanged();
            }
        };

    }

    public interface RecyclerViewClickListener {
        void onClick(View view, String id);
    }


    @NonNull
    @Override
    public DictionaryAdapter.DictionaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_detail_row, parent, false);
        return new DictionaryAdapter.DictionaryViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryAdapter.DictionaryViewHolder holder, int position) {
        Dictionary dictionary = mDictionaryFiltered.get(position);
        holder.tvWord.setText(dictionary.getWord());
        holder.tvTranslation.setText(dictionary.getTranslation());
        holder.itemView.setTag(dictionary.getDictionary_id());

    }

    @Override
    public int getItemCount() {
        return mDictionaryFiltered.size();
    }

    public class DictionaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvWord, tvTranslation;
        private DictionaryAdapter.RecyclerViewClickListener listener;


        public DictionaryViewHolder(@NonNull View itemView, DictionaryAdapter.RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            tvWord = itemView.findViewById(R.id.tv_dictionary_word);
            tvTranslation = itemView.findViewById(R.id.tv_dictionary_translation);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (String) v.getTag());
        }

    }
}
