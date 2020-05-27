package com.example.m.urbandictionary

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.rv_layout_file.view.*


class MyAdapter(val dataArrayList: List<DataHelperClass>, val context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val defTv = view.resultsTv
        val likeCountTv = view.likeCountTv
        val un_likeCountTv = view.un_likeCountTv
    }

    override fun getItemCount(): Int {
        return dataArrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_layout_file, parent, false))
    }


    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.defTv?.text = dataArrayList.get(position).defArrayList
        holder.likeCountTv.text = dataArrayList.get(position).thumbsUpCount
        holder.un_likeCountTv.text = dataArrayList.get(position).thumbsDownCount
    }


}





