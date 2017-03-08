
package com.jishang.bimeng.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Xing,Ming
 * @version 2016年6月3日 下午6:36:36
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;// 版本

    private static final String DB_NAME = "people.db";// 数据库名

    public static final String STUDENT_TABLE = "bimeng";// 表名

    public static final String _ID = "huanid";// 表中的列名

    public static final String NAME = "name";// 表中的列名

    public static final String IMG = "img";// 表中的列名

    // 创建数据库语句，STUDENT_TABLE，_ID ，NAME的前后都要加空格

    private static final String CREATE_TABLE = "create table " + STUDENT_TABLE + " ( " + _ID
            + " varchar(30), " + IMG + " varchar(100), " + NAME + " varchar(50))";

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.e("CREATE_TABLE", CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
