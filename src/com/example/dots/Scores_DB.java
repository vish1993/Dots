package com.example.dots;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Scores_DB {
	
	private static SQLiteDatabase db;
	private static Scores_Helper dbHelper;
	private static String[] columns = {Scores_Helper.score_name, Scores_Helper.score_val};
	
	public Scores_DB(Context context) {
		dbHelper = new Scores_Helper(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		dbHelper.onCreate(db);
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public void clear() throws SQLException {
		db = dbHelper.getWritableDatabase();
		dbHelper.onUpgrade(db,1,1);
	}

	public static void insertScore(String name, int score){
		ContentValues values = new ContentValues();
		values.put(columns[0], name);
		values.put(columns[1], score);
		db.insert(Scores_Helper.table_name, null, values);
	}
	
	public static void deleteScore(String name, int score){
		String[] deleteParam = {name, String.valueOf (score)};
		Cursor cursor = db.rawQuery("select count(*) from " + Scores_Helper.table_name + " WHERE name = ? and score = ?", deleteParam);
		cursor.moveToFirst();
		int num_of_rows = cursor.getInt(0) - 1;
		db.delete (Scores_Helper.table_name, columns[0] + "=\"" + name + "\" and " 
				+ columns[1] + "=" + score, null);
		for (int i = 0; i < num_of_rows; i++){
			insertScore (name, score);
		}
	}
	
	public static List<scores> getAllScores(){
		List<scores> all_scores = new ArrayList<scores>();
		Cursor cursor = db.query (Scores_Helper.table_name, columns, null,null,null,null,columns[1] + " DESC");		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			String name = cursor.getString(0);
			int score = cursor.getInt(1);
			scores temp = new scores (name, score);
			all_scores.add(temp);
			cursor.moveToNext();
		}
		cursor.close();

		return all_scores;
	}
}
