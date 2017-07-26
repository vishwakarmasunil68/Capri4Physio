package com.capri4physio.net;

/**
 * This interface to define network constants
 *
 * @version 1.0
 * @author prabhunathyadav
 * @since 24/04/16.
 */
public interface IConnectionStatus {
    String EX_CONNECTION = "Connection error";
    String EX_TIMEOUT = "Timeout error";
    String EX_IO = "Connection IO error";
    String EX_GSON = "GSON error";
    String EXCETION = "Exception";
    String SUCCESS = "success";
}
