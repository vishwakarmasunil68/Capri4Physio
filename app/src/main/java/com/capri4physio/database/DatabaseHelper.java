package com.capri4physio.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.capri4physio.model.chat.ChatPOJO;
import com.capri4physio.util.TagUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 04-05-2017.
 */

public class DatabaseHelper {

    DataBaseDef helper;

    public DatabaseHelper(Context context) {
        helper = new DataBaseDef(context);
    }

    private final String TAG = getClass().getSimpleName();

    public long insertchatdata(ChatPOJO chatPOJO) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseDef.chat_id, chatPOJO.getChat_id() + "");
        contentValues.put(DataBaseDef.chat_user_id, chatPOJO.getChat_user_id() + "");
        contentValues.put(DataBaseDef.chat_fri_id, chatPOJO.getChat_fri_id() + "");
        contentValues.put(DataBaseDef.chat_date, chatPOJO.getChat_date() + "");
        contentValues.put(DataBaseDef.chat_time, chatPOJO.getChat_time() + "");
        contentValues.put(DataBaseDef.chat_msg, chatPOJO.getChat_msg() + "");
        contentValues.put(DataBaseDef.chat_thumb, chatPOJO.getChat_thumb() + "");
        contentValues.put(DataBaseDef.chat_type, chatPOJO.getChat_type() + "");
        contentValues.put(DataBaseDef.chat_file, chatPOJO.getChat_file() + "");
        contentValues.put(DataBaseDef.chat_admin, chatPOJO.getAdmin() + "");

        long id = db.insert(DataBaseDef.TABLE_CHAT, null, contentValues);
        db.close();
        return id;
    }
    public void insertchatdataList(List<ChatPOJO> chatPOJOList) {
        SQLiteDatabase db = helper.getWritableDatabase();
        for(ChatPOJO chatPOJO:chatPOJOList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseDef.chat_id, chatPOJO.getChat_id() + "");
            contentValues.put(DataBaseDef.chat_user_id, chatPOJO.getChat_user_id() + "");
            contentValues.put(DataBaseDef.chat_fri_id, chatPOJO.getChat_fri_id() + "");
            contentValues.put(DataBaseDef.chat_date, chatPOJO.getChat_date() + "");
            contentValues.put(DataBaseDef.chat_time, chatPOJO.getChat_time() + "");
            contentValues.put(DataBaseDef.chat_msg, chatPOJO.getChat_msg() + "");
            contentValues.put(DataBaseDef.chat_type, chatPOJO.getChat_type() + "");
            contentValues.put(DataBaseDef.chat_thumb, chatPOJO.getChat_thumb() + "");
            contentValues.put(DataBaseDef.chat_file, chatPOJO.getChat_file() + "");
            contentValues.put(DataBaseDef.chat_admin, chatPOJO.getAdmin() + "");

            long id = db.insert(DataBaseDef.TABLE_CHAT, null, contentValues);
            Log.d(TagUtils.getTag(),"insert id:-"+id);
        }
        db.close();
    }

    public List<ChatPOJO> getAllCartData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<ChatPOJO> lst = new ArrayList<>();
        String[] columns = {DataBaseDef.ID,
                DataBaseDef.chat_id,
                DataBaseDef.chat_user_id,
                DataBaseDef.chat_fri_id,
                DataBaseDef.chat_date,
                DataBaseDef.chat_time,
                DataBaseDef.chat_msg,
                DataBaseDef.chat_type,
                DataBaseDef.chat_thumb,
                DataBaseDef.chat_file,
                DataBaseDef.chat_admin
        };
        Cursor cursor = db.query(DataBaseDef.TABLE_CHAT, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            ChatPOJO cartResultPOJO =
                    new ChatPOJO(cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(9),
                            cursor.getString(8),
                            cursor.getString(10)
                    );


            lst.add(cartResultPOJO);
        }
        cursor.close();
        db.close();
        return lst;
    }

    public List<ChatPOJO> getUserChatList(String user_id, String friend_id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<ChatPOJO> chatPOJOList = new ArrayList<>();
        String query = "SELECT * FROM " + DataBaseDef.TABLE_CHAT + " WHERE (" + DataBaseDef.chat_user_id
                + "=" + user_id + " AND " + DataBaseDef.chat_fri_id + " = " + friend_id + ") OR (" + DataBaseDef.chat_user_id
                + "=" + friend_id + " AND " + DataBaseDef.chat_fri_id + " = " + user_id + ")";
        Log.d(TagUtils.getTag(), "query:-" + query);
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {

            ChatPOJO cartResultPOJO =
                    new ChatPOJO(cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(9),
                            cursor.getString(8),
                            cursor.getString(10)
                    );
//            lst.add(newUrgentChatResultPOJO);
            chatPOJOList.add(cartResultPOJO);
        }
        cursor.close();
        db.close();

        return chatPOJOList;
    }

    public List<ChatPOJO> getUserChats(String user_id, String friend_id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<ChatPOJO> lst = new ArrayList<>();
        String[] columns = {DataBaseDef.ID,
                DataBaseDef.chat_id,
                DataBaseDef.chat_user_id,
                DataBaseDef.chat_fri_id,
                DataBaseDef.chat_date,
                DataBaseDef.chat_time,
                DataBaseDef.chat_msg,
                DataBaseDef.chat_type,
                DataBaseDef.chat_thumb,
                DataBaseDef.chat_file,
                DataBaseDef.chat_admin
        };
//        Cursor cursor=db.query(DataBaseHelper.OBSTACLE_TABLE, columns, null, null, null, null, null);
        Cursor cursor = db.query(DataBaseDef.TABLE_CHAT,
                columns,
                DataBaseDef.chat_user_id + " = " + user_id + " OR " + DataBaseDef.chat_fri_id + " = " + user_id,
                null, null, null, null);
        while (cursor.moveToNext()) {

            ChatPOJO cartResultPOJO =
                    new ChatPOJO(cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(9),
                            cursor.getString(8),
                            cursor.getString(10)
                    );
//            lst.add(newUrgentChatResultPOJO);
            lst.add(cartResultPOJO);
        }
        cursor.close();
        db.close();
        return lst;
    }

    public ChatPOJO getchatdata(String chat_id) {
        SQLiteDatabase db = helper.getWritableDatabase();
//        List<WishListResultPOJO> lst=new ArrayList<>();
        String[] columns = {DataBaseDef.ID,
                DataBaseDef.chat_id,
                DataBaseDef.chat_user_id,
                DataBaseDef.chat_fri_id,
                DataBaseDef.chat_date,
                DataBaseDef.chat_time,
                DataBaseDef.chat_msg,
                DataBaseDef.chat_type,
                DataBaseDef.chat_thumb,
                DataBaseDef.chat_file,
                DataBaseDef.chat_admin
        };
//        Cursor cursor=db.query(DataBaseHelper.OBSTACLE_TABLE, columns, null, null, null, null, null);
        Cursor cursor = db.query(DataBaseDef.TABLE_CHAT, columns, DataBaseDef.chat_id + " = " + chat_id, null, null, null, null);
        if (cursor.moveToNext()) {

            ChatPOJO cartResultPOJO =
                    new ChatPOJO(cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(9),
                            cursor.getString(8),
                            cursor.getString(10)
                    );
//            lst.add(newUrgentChatResultPOJO);
            return cartResultPOJO;
        }
        cursor.close();
        db.close();
        return null;
    }

    //    public int UpdateCartData(CartDataPOJO catCartResultPOJO){
//        //UPDATE TABLE SET Name='vav' where Name=?
//        SQLiteDatabase db=helper.getWritableDatabase();
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(DataBaseDef.cart_id, catCartResultPOJO.getCart_id()+"");
//        contentValues.put(DataBaseDef.product_id, catCartResultPOJO.getProduct_id()+"");
//        contentValues.put(DataBaseDef.product_price, catCartResultPOJO.getProduct_price()+"");
//        contentValues.put(DataBaseDef.quantity, catCartResultPOJO.getQuantity()+"");
//        contentValues.put(DataBaseDef.product_name, catCartResultPOJO.getProduct_name()+"");
//        contentValues.put(DataBaseDef.product_sku, catCartResultPOJO.getProduct_sku()+"");
//        contentValues.put(DataBaseDef.product_image, catCartResultPOJO.getProduct_image()+"");
//
//
//        String[] whereArgs={catCartResultPOJO.getCart_id()};
//        int count=db.update(DataBaseDef.TABLE_CART,contentValues, DataBaseDef.cart_id+" =? ",whereArgs);
//        db.close();
//        return count;
//    }
//
//    public int deleteCartData(String cart_id){
//        SQLiteDatabase db=helper.getWritableDatabase();
//        String[] whereArgs={cart_id};
//        int count=db.delete(DataBaseDef.TABLE_CART, DataBaseDef.cart_id+"=?", whereArgs);
//        db.close();
//        return count;
//    }
    public void deleteAllChats() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from " + DataBaseDef.TABLE_CHAT);
        db.close();
    }


    static class DataBaseDef extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "physio";

        //table names
        private static final String TABLE_CHAT = "chat_table";

        private static final int DATABASE_VERSION = 2;

        //        //columns for the ItemData
        private static final String ID = "_id";


        private static final String chat_id = "chat_id";
        private static final String chat_user_id = "chat_user_id";
        private static final String chat_fri_id = "chat_fri_id";
        private static final String chat_date = "chat_date";
        private static final String chat_time = "chat_time";
        private static final String chat_msg = "chat_msg";
        private static final String chat_type = "chat_type";
        private static final String chat_thumb = "chat_thumb";
        private static final String chat_file = "chat_file";
        private static final String chat_admin = "admin";


        private static final String CREATE_CHAT_TABLE = "CREATE TABLE " + TABLE_CHAT + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + chat_id + " VARCHAR(255), "
                + chat_user_id + " VARCHAR(255), "
                + chat_fri_id + " varchar(255), "
                + chat_date + " TEXT, "
                + chat_time + " VARCHAR(255), "
                + chat_msg + " VARCHAR(255), "
                + chat_type + " VARCHAR(255), "
                + chat_thumb + " VARCHAR(255), "
                + chat_file + " VARCHAR(255), "
                + chat_admin + " VARCHAR(255) "
                + ");";

        private static final String DROP_TABLE_CHAT = "DROP TABLE IF EXISTS " + TABLE_CHAT;


        private Context context;


        public DataBaseDef(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            try {

                db.execSQL(CREATE_CHAT_TABLE);

//                Toast.makeText(context, "database called", Toast.LENGTH_SHORT).show();
                Log.d("sunil", "database called");
            } catch (Exception e) {
//                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("sunil", e.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            try {
                db.execSQL(DROP_TABLE_CHAT);
//                Toast.makeText(context, "database droped", Toast.LENGTH_SHORT).show();
                Log.d("sunil", "database droped");
                onCreate(db);
            } catch (Exception e) {
//                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("sunil", e.toString());
            }
        }
    }
}
