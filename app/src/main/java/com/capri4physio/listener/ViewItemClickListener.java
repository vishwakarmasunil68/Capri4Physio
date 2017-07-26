package com.capri4physio.listener;

/**
 * This is callback listener to get view item click like listview, recyclerview item click
 *
 * @version 1.0
 * @author prabhunathy
 * @since 12/22/15.
 */
public interface ViewItemClickListener<T> {

    public void onViewItemClick(T t, int position, int actionId);

}
