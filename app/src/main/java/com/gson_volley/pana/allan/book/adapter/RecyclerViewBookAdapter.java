package com.gson_volley.pana.allan.book.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gson_volley.pana.allan.book.R;
import com.gson_volley.pana.allan.book.models.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allan on 01/07/15.
 */
public class RecyclerViewBookAdapter extends RecyclerView.Adapter<RecyclerViewBookAdapter.ViewHolderBook>{

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Book>bookList = new ArrayList<>();


    public RecyclerViewBookAdapter( Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderBook onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_layout_for_book,viewGroup,false);
        ViewHolderBook viewHolderBook = new ViewHolderBook(view);
        return viewHolderBook;
    }

    @Override
    public void onBindViewHolder(ViewHolderBook viewHolderBook, int i) {
        Book book = bookList.get(i);
        viewHolderBook.tv_title.setText(book.getTitle());
        viewHolderBook.tv_author.setText(book.getAuthor());

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    /**
     * INNER CLAZZ
     */
    public class ViewHolderBook extends RecyclerView.ViewHolder{

        private TextView tv_title;
        private TextView tv_author;

        public ViewHolderBook(View itemView) {
            super(itemView);

            tv_author = (TextView) itemView.findViewById(R.id.tv_author);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
