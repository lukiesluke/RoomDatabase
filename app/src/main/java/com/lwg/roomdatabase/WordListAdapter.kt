package com.lwg.roomdatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lwg.roomdatabase.room.Word

class WordListAdapter(mLister: OnClickItemAdapter) :
    ListAdapter<Word, WordListAdapter.WordViewHolder>(WordsComparator()) {

    private var lister: OnClickItemAdapter = mLister

    interface OnClickItemAdapter {
        fun onClickItem(v: View?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position), lister)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)
        var iLister: OnClickItemAdapter? = null
        var word : Word? = null

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(itemWord: Word, lister: OnClickItemAdapter) {
            word = itemWord
            wordItemView.text = itemWord.word
            iLister = lister
        }

        override fun onClick(v: View?) {
            v?.tag = word
            iLister?.onClickItem(v)
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.word == newItem.word
        }
    }
}