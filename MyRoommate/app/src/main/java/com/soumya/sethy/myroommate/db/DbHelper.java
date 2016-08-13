package com.soumya.sethy.myroommate.db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "MyRoomMate.db";

	public static final String DETAIL_TABLE = "detail_table";
	public static final String USER_BY_TABLE = "user_table";
	public static final String AMOUNT_TABLE = "amount_table";

	public DbHelper(Context context)

	{
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL("create table detail_table"
				+ "(_id integer primary key AUTOINCREMENT,item text ,item_type text, src_transaction_dt integer NOT NULL)");
		db.execSQL("create table user_table" + "(_id integer primary key AUTOINCREMENT ,name text NOT NULL UNIQUE, phone_num text NOT NULL UNIQUE NOT NULL)");
		db.execSQL("create table amount_table"
				+ "(_id integer NOT NULL,amount double NOT NULL,name text NOT NULL ,spilt text NOT NULL,PRIMARY KEY ( _id, name) )");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + DETAIL_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + USER_BY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + AMOUNT_TABLE);
		onCreate(db);

	}

	public boolean insertDetail_table(String item, String item_type,int src_transaction_dt) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("item", item);
		contentValues.put("item_type", item_type);
		contentValues.put("src_transaction_dt", src_transaction_dt);
		db.insert("detail_table", null, contentValues);
		return true;
	}
	public boolean insertUser_table(String phone_num, String name) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("phone_num", phone_num);
		contentValues.put("name", name);
		db.insert("user_table", null, contentValues);
		return true;
	}

	public boolean insertAmount_table(int _id, double amount, String name, String spilt_status) {

			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			contentValues.put("_id", _id);
			contentValues.put("amount", amount);
			contentValues.put("name", name);
			contentValues.put("spilt", spilt_status);
			db.insert("amount_table", null, contentValues);
			return true;

	}

	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
	public int get_id() {
		SQLiteDatabase db = this.getReadableDatabase();

		 
		Cursor cursor = db.rawQuery("SELECT _id FROM detail_table WHERE _id = (SELECT MAX(_id)  FROM detail_table);", null);
		if (cursor.getCount() < 1) // STUDYCODE Not Exist
			return 0;
		cursor.moveToFirst();
		int researchname = 0 ;
		try {
		 researchname = cursor.getInt(cursor
					.getColumnIndex("_id"));	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return researchname;
	}

	public Cursor getAllUser() {
		SQLiteDatabase db = this.getReadableDatabase();

		return db.rawQuery("SELECT * FROM user_table", null);

	}

	public Cursor getAllAmount() {
		SQLiteDatabase db = this.getReadableDatabase();

		return db.rawQuery("SELECT * FROM amount_table", null);

	}

	public Cursor getAllDetails() {
		SQLiteDatabase db = this.getReadableDatabase();

		return db.rawQuery("SELECT _ID as _id,item, group_concat(conc_amount, '') AS mix_amount,SRC_TRANSACTION_DT FROM ("
				+"SELECT _ID,item,  name  || ':' || group_concat(amount, ':') ||'-'||spilt ||',' AS conc_amount,SRC_TRANSACTION_DT FROM"
				+"(SELECT A._ID,B.ITEM,A.NAME,A.AMOUNT,A.SPILT,B.SRC_TRANSACTION_DT FROM AMOUNT_TABLE A INNER JOIN detail_table B on A._ID = B._ID)"
				+"GROUP BY _ID,name, amount)GROUP BY _ID ORDER BY _ID DESC", null);

	}

	public Cursor getExpense() {
		SQLiteDatabase db = this.getReadableDatabase();

		return db
				.rawQuery(
						"SELECT NAME AS _id,SUM(AMOUNT) AS AMOUNT FROM (SELECT _ID,NAME,CASE  WHEN (AMOUNT>=0  AND SPILT ='N' ) THEN AMOUNT ELSE AMOUNT - ( SELECT SUM(AMOUNT) FROM AMOUNT_TABLE WHERE _ID =ATM._ID )/(SELECT COUNT(*) FROM AMOUNT_TABLE WHERE _ID =ATM._ID AND SPILT ='Y') END   AS AMOUNT FROM AMOUNT_TABLE ATM ) GROUP BY NAME ORDER BY AMOUNT DESC",null);

	}
}