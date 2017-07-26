package com.capri4physio.listener;

/**
 *This is callback listener to get web APIs call response.
 *
 * @version 1.0
 * @author prabhunathy
 * @since 12/22/15.
 */
public interface HttpUrlListener{

    public void onPostSuccess(Object response, int id);

    public void onPostError(String errMsg, int id);

}
