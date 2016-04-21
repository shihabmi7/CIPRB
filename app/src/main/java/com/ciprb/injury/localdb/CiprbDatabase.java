package com.ciprb.injury.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ciprb.injury.Person;

import java.util.ArrayList;


public  class CiprbDatabase {
	private static String DATABASE_NAME = "CIPRDBDB";
	private static int DATABASE_VERSION = 1;

	private static String DATA_TABLE = "Data_Table";
	private static String DEATH_TABLE = "death_table";
	private static String PERSON_UNIQUE_ID = "person_id";
	private static String PERRSON_NAME = "extra_1";
	private static String INJURY_STATUS = "extra_2";

	private static DbHelper _DbHelper;
	private static Context context;
	public static SQLiteDatabase mynetDatabase;

	private static String SQL = "";

	public static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public static DbHelper getInstance(Context context) {

			// Use the application context, which will ensure that you
			// don't accidentally leak an Activity's context.
			// See this article for more information: http://bit.ly/6LRzfx
			if (_DbHelper == null) {
				_DbHelper = new DbHelper(context.getApplicationContext());
			}

			if (mynetDatabase == null || !mynetDatabase.isOpen()) {
				mynetDatabase = _DbHelper.getWritableDatabase();
			}
			while ((mynetDatabase.isDbLockedByCurrentThread() || mynetDatabase
					.isDbLockedByOtherThreads())) {

			}
			return _DbHelper;
		}
		public ArrayList<Cursor> getData(String Query){
			//get writable database
			SQLiteDatabase sqlDB = this.getWritableDatabase();
			String[] columns = new String[] { "mesage" };
			//an array list of cursor to save two cursors one has results from the query
			//other cursor stores error message if any errors are triggered
			ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
			MatrixCursor Cursor2= new MatrixCursor(columns);
			alc.add(null);
			alc.add(null);


			try{
				String maxQuery = Query ;
				//execute the query results will be save in Cursor c
				Cursor c = sqlDB.rawQuery(maxQuery, null);


				//add value to cursor2
				Cursor2.addRow(new Object[] { "Success" });

				alc.set(1,Cursor2);
				if (null != c && c.getCount() > 0) {


					alc.set(0,c);
					c.moveToFirst();

					return alc ;
				}
				return alc;
			} catch(SQLException sqlEx){
				Log.d("printing exception", sqlEx.getMessage());
				//if any exceptions are triggered save the error message to cursor an return the arraylist
				Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
				alc.set(1,Cursor2);
				return alc;
			} catch(Exception ex){

				Log.d("printing exception", ex.getMessage());

				//if any exceptions are triggered save the error message to cursor an return the arraylist
				Cursor2.addRow(new Object[] { ""+ex.getMessage() });
				alc.set(1,Cursor2);
				return alc;
			}


		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			SQL = "CREATE TABLE " +DATA_TABLE +"("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ PERSON_UNIQUE_ID + " nvarchar(1000) NULL,"
					+ PERRSON_NAME + " nvarchar(1000) NULL,"
					+ INJURY_STATUS + " INTEGER (10)  NULL"
					+ ")";
			db.execSQL(SQL);
			SQL = "CREATE TABLE " +DEATH_TABLE +"("
					+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ PERSON_UNIQUE_ID + " nvarchar(1000) NULL,"
					+ PERRSON_NAME + " nvarchar(1000) NULL,"
					+ INJURY_STATUS + " nvarchar(1000)  NULL"
					+ ")";
			db.execSQL(SQL);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "+DATA_TABLE);
			onCreate(db);
		}
	}
	public long insertIntoDB(String person_id,String personName,int status) {
		Log.i("test ki", "" + "sadsad");
		ContentValues contentValues = new ContentValues();
		contentValues.put(PERSON_UNIQUE_ID, person_id);
		contentValues.put(PERRSON_NAME, personName);
		contentValues.put(INJURY_STATUS, status);


		return mynetDatabase.insertOrThrow(DATA_TABLE, null,
				contentValues);
	}
	public long insertIntoDeathDB(String person_id,String personName) {
		Log.i("test ki", "" + "sadsad");
		ContentValues contentValues = new ContentValues();
		contentValues.put(PERSON_UNIQUE_ID, person_id);
		contentValues.put(PERRSON_NAME, personName);
		contentValues.put(INJURY_STATUS, "extra2");


		return mynetDatabase.insertOrThrow(DEATH_TABLE, null,
				contentValues);
	}

	public static ArrayList<Person> getAlivePersonList() {
		ArrayList<Person> customModes = new ArrayList<Person>();
		Cursor c = mynetDatabase.rawQuery(
				"SELECT  * FROM " +DATA_TABLE, null);
		Person customMode = null;
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
				customMode = new Person();
				customMode.setMembers_name(c.getString(2));
				customMode.setSex(c.getString(1));
				customMode.setPerson_id(c.getString(1));
				customModes.add(customMode);
				c.moveToNext();
			}
		}
		c.close();
		return customModes;
	}

    public static ArrayList<Person> getAllPersonWhoHaveInjury() {
        ArrayList<Person> customModes = new ArrayList<Person>();
        Cursor c = mynetDatabase.rawQuery(
                "SELECT  * FROM " + DATA_TABLE + " where " + INJURY_STATUS + "=1", null);
        Person customMode = null;
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
                customMode = new Person();
                customMode.setMembers_name(c.getString(2));
                //customMode.setSex(c.getString(1));
                customMode.setPerson_id(c.getString(1));
                customModes.add(customMode);
                c.moveToNext();
            }
        }
        c.close();
        return customModes;
    }
	public static ArrayList<Person> getDeathPersonList() {
		ArrayList<Person> customModes = new ArrayList<Person>();
		Cursor c = mynetDatabase.rawQuery(
				"SELECT  * FROM " +DEATH_TABLE, null);
		Person customMode = null;
		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			for (int rowIndex = 0; rowIndex < c.getCount(); rowIndex++) {
				customMode = new Person();
				customMode.setMembers_name(c.getString(2));
				customMode.setSex(c.getString(1));
				customMode.setPerson_id(c.getString(1));
				customModes.add(customMode);
				c.moveToNext();
			}
		}
		c.close();
		return customModes;
	}
	public void deleteRowByID(String id){

		String deleteQuery = "DELETE FROM " +
				DATA_TABLE
				+ " WHERE person_id"  + " = " + "'"+id+"'";
		mynetDatabase.execSQL(deleteQuery);
		//return mynetDatabase.delete(AddToCart, deleteQuery, null)>0;

	}
	public void deleteRowByIDFromDeath(String id){

		String deleteQuery = "DELETE FROM " +
				DEATH_TABLE
				+ " WHERE person_id"  + " = " + "'"+id+"'";
		mynetDatabase.execSQL(deleteQuery);
		//return mynetDatabase.delete(AddToCart, deleteQuery, null)>0;

	}
	public CiprbDatabase(Context _context) {
		context = _context;
		DbHelper.getInstance(context);
	}

	public CiprbDatabase open() {

		return this;
	}

	public void close() {
		 _DbHelper.close();
	}



}
