package com.capri4physio.listener;

/**
 * This is callback listener to get results from dialog.
 *
 * @version 1.0
 * @author prabhunathy
 * @since 12/22/15.
 */
public interface DialogListener<T> {

    public void onDialogResult(T t, int Id);

}
