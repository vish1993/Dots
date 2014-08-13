package com.example.dots;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Scores_Helper extends SQLiteOpenHelper {
	
	public static final String table_name = "scores";
	public static final String score_name = "name";
	public static final String score_val = "score";
	
	public static final String db_name = "high_scores.db";
	public static final int db_version = 1;
	
	public static final String db_create = "create table if not exists "
			+ table_name + "("
			+ score_name + " text, " 
			+ score_val + " integer not null)";
	
	public static final String db_upgrade = "drop table if exists " + table_name;
	
	public Scores_Helper(Context context){
		super(context, db_name, null, db_version);
	}
	
	public void onCreate (SQLiteDatabase database){
		try {
			database.execSQL(db_create);
		}
		catch (Exception ex){
			System.out.println (ex);
		}
	}
	
	public void onUpgrade (SQLiteDatabase db, int oldV, int newV){
		db.execSQL (db_upgrade);
		onCreate (db);
	}
}
