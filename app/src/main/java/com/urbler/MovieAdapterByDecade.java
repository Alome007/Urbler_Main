package com.urbler;

import java.util.List;

import android.content.Context;
public class MovieAdapterByDecade extends BaseMovieAdapter {
    public MovieAdapterByDecade(List<appPojo> itemList) {
        super(itemList);
    }
    @Override
    public boolean onPlaceSubheaderBetweenItems(int position) {
        final int movieDecade = movieList.get(position).getPosition() / 10;
        final int nextMovieDecade = movieList.get(position + 1).getPosition() / 10;

        return movieDecade != nextMovieDecade;
    }

    @Override
    public void onBindItemViewHolder(final MovieViewHolder holder, final int position) {
        final appPojo movie = movieList.get(position);
      //  holder.textMovieTitle.setText(movie.getDay());
        holder.textMovieGenre.setText(movie.getCity());
        holder.textMovieYear.setText(String.valueOf(movie.getPhoneNumber()));
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClicked(movie));
    }

    @Override
    public void onBindSubheaderViewHolder(SubheaderHolder subheaderHolder, int nextItemPosition) {
        super.onBindSubheaderViewHolder(subheaderHolder, nextItemPosition);
        final Context context = subheaderHolder.itemView.getContext();
        final appPojo nextMovie = movieList.get(nextItemPosition);
        final int sectionSize = getSectionSize(getSectionIndex(subheaderHolder.getAdapterPosition()));
    //    final String decade = String.valueOf(nextMovie.getPosition() - nextMovie.getPosition() % 10) + "s";
        final String subheaderText = String.format(
                context.getString(R.string.subheader),
                nextMovie.getDay(),
                context.getResources().getQuantityString(R.plurals.item, sectionSize, sectionSize)
        );
        subheaderHolder.mSubheaderText.setText(subheaderText);
    }
}