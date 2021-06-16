package com.dalyel.dalyelaltaleb.bataBase;

import android.provider.BaseColumns;

public class DBContract {


    public static class Question implements BaseColumns {
        public  static final String TABLE_NAME="questions";
        public  static final String COLUMN_ID="_id";
        public  static final String COLUMN_question="question";
        public  static final String COLUMN_image_answer="image_answer";
        public  static final String CREATE_STATMENT=" CREATE TABLE "+TABLE_NAME+" ( "+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "
                +COLUMN_question+" TEXT , "+COLUMN_image_answer+" TEXT " + ")";

        public  static final String DELEATE_STATMENT=" DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
