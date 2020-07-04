package com.database.schoolbusschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 *
 */
public class DBAdapter {
    private static final String DB_NAME = "SchoolBusSchedule.db";// 数据库名
    public static final String USER_TABLE = "user";// 用户表、
    public static final String SCHEDULE_TABLE = "schedule";// 行程表
    public static final String RELATION_TABLE = "relation";// 用户、行程关系表
    public static final String BUS_TABLE = "bus";     //校车表
    private static final int DB_VERSION = 1;// 数据库版本号
    //用户表
    public static final String ACCOUNT_ID = "account";// 用户账号
    public static final String PASSWORD="passwd";
    public static final String USER_NAME = "myName";// 用户姓名
    public static final String USER_CLASS = "myClass";// 学生班级/职工部门
    public static final String USER_IDENTIFY="myIdentify";
    //行程表
    public static final String SCHEDULE_ID = "scheduleID";
    public static final String DEPARTURE_DATE = "departureDate";
    public static final String DEPARTURE_PALCE = "departurePlace";
    public static final String DEPARTURE_END = "departureEnd";
    public static final String DRIVER_NAME = "driverName";
    public static final String DRIVER_PHONE = "driverPhone";
    public static final String REMAIN_SEAT = "remain_seat";
    public static final String STATUS="bus_status";
    public static final String SCHEDULE_OWNER="owner_account";
    //校车表
    public static final String BUS_ID = "busID";
    public static final String BUS_STATUS = "busStatus";
    public static final String SEAT_NUM = "seat_num";

    public static final String RELATION_ID = "relationId";

    private SQLiteDatabase db;
    private Context mcontext;
    private DBOpenHelper dbOpenHelper;

    public DBAdapter(Context context) {
        mcontext = context;
    }

    public void open() throws SQLiteException {
        // 创建一个DBOpenHelper实例
        dbOpenHelper = new DBOpenHelper(mcontext, DB_NAME, null, DB_VERSION);
        // 通过dbOpenHelper.getWritableDatabase()或者dbOpenHelper.getReadableDatabase()
        //创建或打开一个数据库SQLiteDatabase实例，其中dbOpenHelper.getWritableDatabase()
        //得到的数据库具有读写的权限，而dbOpenHelper.getReadableDatabase()得到的数据库则具有只读的权限。
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    /**
     * 创建账号
     * */
    public void insertAccount(User user){
        ContentValues values=new ContentValues();
        values.put(ACCOUNT_ID,user.getAccount());
        values.put(PASSWORD,user.getPasswd());
        values.put(USER_NAME,user.getName());
        values.put(USER_CLASS,user.getMyclass());
        values.put(USER_IDENTIFY,user.getPosition());
        long insertRow = db.insert(USER_TABLE, null, values);
        if(insertRow==-1){
            Log.e("accountRegisterStatus","fail");
        }
    }
    /**
     * 单行添加
     * */
    public void insertSchedule(BusSchedule  schedule){
        ContentValues values=new ContentValues();
        values.put(DEPARTURE_PALCE,schedule.getDeparturePlace());
        values.put(DEPARTURE_END,schedule.getDepartureEnd());
        values.put(DEPARTURE_DATE,schedule.getDepartureDate());
        values.put(DRIVER_NAME,schedule.getDriverName());
        values.put(DRIVER_PHONE,schedule.getDriverPhone());
        values.put(REMAIN_SEAT,schedule.getRemain_seat());
        values.put(STATUS,schedule.getStatus());
        values.put(SCHEDULE_OWNER,schedule.getOwner_account());
        long line=db.insert(SCHEDULE_TABLE,null,values);
        if(line==-1){
            Log.e("insertSchedule","fail");
        }
    }
    /**
     * 多行添加
     * */
    public void insertSchedules(List<BusSchedule> schedules){
        if(schedules!=null){
            int index=0;
            db.beginTransaction();
            try {
                while(schedules.size()>index){
                    BusSchedule schedule=schedules.get(index);
                    ContentValues values=new ContentValues();
                    values.put(DEPARTURE_PALCE,schedule.getDeparturePlace());
                    values.put(DEPARTURE_END,schedule.getDepartureEnd());
                    values.put(DEPARTURE_DATE,schedule.getDepartureDate());
                    values.put(DRIVER_NAME,schedule.getDriverName());
                    values.put(DRIVER_PHONE,schedule.getDriverPhone());
                    values.put(REMAIN_SEAT,schedule.getRemain_seat());
                    values.put(STATUS,schedule.getStatus());
                    values.put(SCHEDULE_OWNER,schedule.getOwner_account());
                    db.insert(SCHEDULE_TABLE,null,values);
                }
                db.setTransactionSuccessful();
            }catch (Exception i){
                Log.i("insertSchedules","fail");
            }finally {
               db.endTransaction();
            }

        }
    }
    /**通过ownerID找到所属行程
     * */
    public List<BusSchedule> querySchedules(String ownID){
        List<BusSchedule>schedules=new ArrayList<BusSchedule>();
        Cursor cursor=db.query(SCHEDULE_TABLE,null,SCHEDULE_OWNER+"=?",new String[]{ownID},
                null,null,null);
        while(cursor.moveToNext()){
            BusSchedule schedule;
            Integer id,remainSeat;
            String departureDate,departurePlace,departureEnd,driverName,driverPhone,busStatus;
            id=cursor.getInt(cursor.getColumnIndex(SCHEDULE_ID));
            departurePlace=cursor.getString(cursor.getColumnIndex(DEPARTURE_PALCE));
            departureEnd=cursor.getString(cursor.getColumnIndex(DEPARTURE_END));
            departureDate=cursor.getString(cursor.getColumnIndex(DEPARTURE_DATE));
            driverName=cursor.getString(cursor.getColumnIndex(DRIVER_NAME));
            driverPhone=cursor.getString(cursor.getColumnIndex(DRIVER_PHONE));
            remainSeat=cursor.getInt(cursor.getColumnIndex(REMAIN_SEAT));
            busStatus=cursor.getString(cursor.getColumnIndex(STATUS));
            schedule=new BusSchedule(id,departurePlace,departureEnd,departureDate,driverName,driverPhone,remainSeat,busStatus,ownID);
            schedules.add(schedule);
        }
        return schedules;
    }
    /**通过行程id找到单一行程
     * */
    public BusSchedule querySchedule(Integer scheduleID){
        BusSchedule schedule=null;
        Cursor cursor=db.query(SCHEDULE_TABLE,null,SCHEDULE_ID+"=?",new String[]{scheduleID+""},
                null,null,null);
        if(cursor.moveToNext()){
            Integer remainSeat;
            String ownID, departureDate,departurePlace,departureEnd,driverName,driverPhone,busStatus;
            ownID=cursor.getString(cursor.getColumnIndex(SCHEDULE_OWNER));
            departurePlace=cursor.getString(cursor.getColumnIndex(DEPARTURE_PALCE));
            departureEnd=cursor.getString(cursor.getColumnIndex(DEPARTURE_END));
            departureDate=cursor.getString(cursor.getColumnIndex(DEPARTURE_DATE));
            driverName=cursor.getString(cursor.getColumnIndex(DRIVER_NAME));
            driverPhone=cursor.getString(cursor.getColumnIndex(DRIVER_PHONE));
            remainSeat=cursor.getInt(cursor.getColumnIndex(REMAIN_SEAT));
            busStatus=cursor.getString(cursor.getColumnIndex(STATUS));
            schedule=new BusSchedule(scheduleID,departurePlace,departureEnd,departureDate,driverName,driverPhone,remainSeat,busStatus,ownID);
        }
        return schedule;
    }
    /**
     * 通过userAccount 和 ownerID 找到所有行程
     * */
    public List<BusSchedule>  querySchedules(String account,String ownID){
        List<BusSchedule>schedules=new ArrayList<BusSchedule>();
        String condition=SCHEDULE_OWNER+"=? or "+SCHEDULE_ID+" in (select "+SCHEDULE_ID+" from "+RELATION_TABLE+" where "+ ACCOUNT_ID+"=?)";
        Log.i("condition",condition);

        Cursor cursor=db.query(SCHEDULE_TABLE,null,condition,new String[]{ownID,account},null,null,null);
        while(cursor.moveToNext()){
            BusSchedule schedule;
            Integer id,remainSeat;
            String departureDate,departurePlace,departureEnd,driverName,driverPhone,busStatus;
            id=cursor.getInt(cursor.getColumnIndex(SCHEDULE_ID));
            departurePlace=cursor.getString(cursor.getColumnIndex(DEPARTURE_PALCE));
            departureEnd=cursor.getString(cursor.getColumnIndex(DEPARTURE_END));
            departureDate=cursor.getString(cursor.getColumnIndex(DEPARTURE_DATE));
            driverName=cursor.getString(cursor.getColumnIndex(DRIVER_NAME));
            driverPhone=cursor.getString(cursor.getColumnIndex(DRIVER_PHONE));
            remainSeat=cursor.getInt(cursor.getColumnIndex(REMAIN_SEAT));
            busStatus=cursor.getString(cursor.getColumnIndex(STATUS));
            ownID=cursor.getString(cursor.getColumnIndex(SCHEDULE_OWNER));
            schedule=new BusSchedule(id,departurePlace,departureEnd,departureDate,driverName,driverPhone,remainSeat,busStatus,ownID);
            schedules.add(schedule);
        }
        return schedules;
    }

    /**
     * 更新车程
     * */
    public void updateSchedules(String ownerAccount,BusSchedule busSchedule){

        ContentValues values=new ContentValues();
        values.put(DEPARTURE_PALCE,busSchedule.getDeparturePlace());
        values.put(DEPARTURE_END,busSchedule.getDepartureEnd());
        values.put(DEPARTURE_DATE,busSchedule.getDepartureDate());
        values.put(DRIVER_NAME,busSchedule.getDriverName());
        values.put(DRIVER_PHONE,busSchedule.getDriverPhone());
        values.put(STATUS,busSchedule.getStatus());
        values.put(REMAIN_SEAT,busSchedule.getRemain_seat());
        db.update(SCHEDULE_TABLE,values,SCHEDULE_OWNER+"=?",new String[]{ownerAccount});
    }
    public void updateSchedule(BusSchedule busSchedule){
        String ownerAccount=busSchedule.getOwner_account();
        String scheduleID=busSchedule.getScheduleID()+"";
        ContentValues values=new ContentValues();
        values.put(DEPARTURE_PALCE,busSchedule.getDeparturePlace());
        values.put(DEPARTURE_END,busSchedule.getDepartureEnd());
        values.put(DEPARTURE_DATE,busSchedule.getDepartureDate());
        values.put(DRIVER_NAME,busSchedule.getDriverName());
        values.put(DRIVER_PHONE,busSchedule.getDriverPhone());
        values.put(STATUS,busSchedule.getStatus());
        values.put(REMAIN_SEAT,busSchedule.getRemain_seat());
        long line=db.update(SCHEDULE_TABLE,values,SCHEDULE_ID+"=? and "+SCHEDULE_OWNER+"=?",
                new String[]{scheduleID,ownerAccount});
        if(line==-1){
            Log.i("updateSchedule","fail");
        }
    }
    /**
     * 删除行程
     * */
    public void deleteSchedule(BusSchedule schedule){
        long line=db.delete(SCHEDULE_TABLE,SCHEDULE_ID+"=? and "+SCHEDULE_OWNER+"=?"
                ,new String[]{schedule.getScheduleID()+"",schedule.getOwner_account()});
        if(line==-1){
            Log.i("deleteSchedule","fail");
        }
    }




    /**
     * 验证账号
     * */
    public User queryAccount(String account,String passwd){
        User user=null;
        String selection=ACCOUNT_ID+"=? and "+PASSWORD+"=?";
        Cursor cursor=db.query(USER_TABLE,null,selection,new String[]{account,passwd},null,null,null);
        if(cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex(USER_NAME));
            String myclass=cursor.getString(cursor.getColumnIndex(USER_CLASS));
            String position=cursor.getString(cursor.getColumnIndex(USER_IDENTIFY));
            user=new User(account,null,name,myclass,position);
        }else{
            Log.e("LoginStatus","fail");
        }
        cursor.close();
        return user;
    }


    /**
     * 查询行程登记
     * */
    public boolean queryRelation(String userAccount,Integer scheduleID){
        Cursor cursor=db.query(RELATION_TABLE,null,ACCOUNT_ID+"=? and "+SCHEDULE_ID+"=?",new String[]{userAccount,""+scheduleID},
                null,null,null);
        boolean isSign=cursor.moveToNext();
        cursor.close();
        return isSign;
    }
    public void addRelation(String userAccount,Integer scheduleID){
        ContentValues values=new ContentValues();
        values.put(ACCOUNT_ID,userAccount);
        values.put(SCHEDULE_ID,scheduleID);
        long line=db.insert(RELATION_TABLE,null,values);
        if(line==-1){
            Log.i("addRelation","fail");
        }
    }
    public void deleteRelation(String userAccount,Integer scheduleID){
        long line=db.delete(RELATION_TABLE,ACCOUNT_ID+"=? and "+SCHEDULE_ID+"=?",
                new String[]{userAccount,scheduleID+""});
        if(line==-1){
            Log.i("deleteRelation","fail");
        }
    }

    // 关闭数据库
    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    /**
     * 静态Helper类，用于建立、更新和打开数据库
     */
    private static class DBOpenHelper extends SQLiteOpenHelper {
        // 创建数据库的sql语句
        private static final String USER_CREATE = "CREATE TABLE "
                + USER_TABLE + "(" + ACCOUNT_ID + " text primary key,"
                + PASSWORD+" text not null, "
                + USER_NAME + " text not null," + USER_CLASS + " text, "+USER_IDENTIFY+" text);";
        private static final String SCHEDULE_CREATE = "CREATE TABLE "
                + SCHEDULE_TABLE + "(" + SCHEDULE_ID + " Integer primary key autoincrement, "
                + DEPARTURE_PALCE + " text not null," + DEPARTURE_END + " text,"
                + DEPARTURE_DATE + " text not null," + BUS_ID+ " integer,"
                + DRIVER_NAME + " text not null," + DRIVER_PHONE + " integer,"
                + SCHEDULE_OWNER + " text not null," + STATUS+" text ,"
                + REMAIN_SEAT + " integer);";
        private static final String RELATION_CREATE = "CREATE TABLE "
                + RELATION_TABLE + "(" + ACCOUNT_ID + " text not null,"
                + SCHEDULE_ID + " Integer not null);";
        private static final String SCHOOLBUS_CREATE = "CREATE TABLE "
                + BUS_TABLE + "(" + BUS_ID + " Integer primary key autoincrement, "
                + BUS_STATUS + " text not null, "
                + SEAT_NUM + " Integer not null);";

        // 在用户创建DBOpenHelper的构造函数，其自动调用自身的onCreate(SQLiteDatabase db)函数
        public DBOpenHelper(Context context, String name,
                            CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        /**
         * 创建表
         * */
        @Override
        public void onCreate(SQLiteDatabase db) {
            // 执行sql语句，创建数据库
            Log.i("table",USER_CREATE);
            Log.i("table",SCHEDULE_CREATE);
            Log.i("table",RELATION_CREATE);
            Log.i("table",SCHOOLBUS_CREATE);
			//测试账号
            String initAccount="insert into "+USER_TABLE+"("+ACCOUNT_ID+","+PASSWORD+","+USER_NAME+","+USER_CLASS +","+USER_IDENTIFY+")"
                    +"values('123456789011','123456','校方','学校','仲恺')";
            Date date=new Date();
            date.setHours(9);
            date.setMinutes(0);
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd/HH:mm");
            String result=sdf.format(date);
			//测试行程
            String initSchedule="insert into "+SCHEDULE_TABLE+"("+DEPARTURE_PALCE+","+DEPARTURE_END+","+DEPARTURE_DATE+","+DRIVER_NAME+
                    ","+DRIVER_PHONE+","+REMAIN_SEAT+","+STATUS+","+SCHEDULE_OWNER+")"+" values('海珠区','白云区','"+result+"','张伟','10086115433',"
                    +"60,'等待','123456789011')";
            Log.i("table",initAccount);
            Log.i("scdedules",initSchedule);
            db.execSQL(USER_CREATE);
            db.execSQL(SCHEDULE_CREATE);
            db.execSQL(RELATION_CREATE);
            db.execSQL(SCHOOLBUS_CREATE);
            db.execSQL(initAccount);
            for(int i=0;i<4;i++) {
                db.execSQL(initSchedule);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            // 函数在数据库需要升级时被调用，
            // 一般用来删除旧的数据库表，
            // 并将数据转移到新版本的数据库表中
            _db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + RELATION_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + BUS_TABLE);
            onCreate(_db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + RELATION_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + BUS_TABLE);
            onCreate(_db);
        }
    }
}
