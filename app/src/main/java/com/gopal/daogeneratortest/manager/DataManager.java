package com.gopal.daogeneratortest.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.gopal.model.DaoMaster;
import com.gopal.model.DaoSession;
import com.gopal.model.Task;
import com.gopal.model.TaskDao;

import java.util.List;

public class DataManager implements InterfaceDataManager {
    /**
     * Instance of DatabaseManager
     */
    private static DataManager mInstance;
    /**
     * The Android Activity reference for access to DatabaseManager.
     */
    private Context mContext;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    /**
     * Constructs a new DatabaseManager with the specified arguments.
     *
     * @param context The Android {@link android.content.Context}.
     */

    public DataManager(final Context context) {
        this.mContext = context;
        mHelper = new DaoMaster.DevOpenHelper(this.mContext, "my-tasks", null);
    }

    /**
     * @param context The Android {@link android.content.Context}.
     * @return this.mInstance
     */
    public static DataManager getInstance(Context context) {

        if (mInstance == null) {
            mInstance = new DataManager(context);
        }

        return mInstance;
    }


    /**
     * Query for readable DB
     */
    public void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    /**
     * Query for writable DB
     */
    public void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    @Override
    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (mInstance != null) {
            mInstance = null;
        }
    }

    @Override
    public synchronized void dropDatabase() {
        try {
            openWritableDb();
            DaoMaster.dropAllTables(database, true); // drops all tables
            mHelper.onCreate(database);              // creates the tables
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public synchronized Task insertTask(Task task) {
        try {
            if (task != null) {
                openWritableDb();
                TaskDao taskDao = daoSession.getTaskDao();
                taskDao.insert(task);
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public synchronized List<Task> listTasks() {
        List<Task> tasks = null;
        try {
            openReadableDb();
            TaskDao taskDao = daoSession.getTaskDao();
            tasks = taskDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public synchronized void updateTask(Task task) {
        try {
            if (task != null) {
                openWritableDb();
                daoSession.update(task);
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public synchronized boolean deleteTaskById(Long taskId) {
        try {
            openWritableDb();
            TaskDao taskDao = daoSession.getTaskDao();
            taskDao.deleteByKey(taskId);
            daoSession.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public synchronized Task getTaskById(Long userId) {
        Task task = null;
        try {
            openReadableDb();
            TaskDao taskDao = daoSession.getTaskDao();
            task = taskDao.load(userId);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public synchronized void deleteTasks() {
        try {
            openWritableDb();
            TaskDao taskDao = daoSession.getTaskDao();
            taskDao.deleteAll();
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
