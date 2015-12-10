# greenDao-Example
Data Stored in SQLite Database using GreenDao

  greenDAO is an open source project to help Android developers working with data stored in SQLite. 
SQLite is an awesome embedded relational database. However, developing for it requires alot of additional work. 
Writing SQL and parsing query results are quite tedious tasks. 
greenDAO will do the work for you: it maps Java objects to database tables (often called ORM). 
This way you can store, update, delete, and query for Java objects using a simple object oriented API. 
Save time and focus on real problems!

# Explanation

1. Checkout this project in Android Studio.
2. In this project have two modules("app" and "daodatabase").
3. In "daodatabase" module deines the schema (Code for generating table and all database related query).

public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.gopal.model");

        Entity task = schema.addEntity("Task");
        task.addIdProperty().primaryKey().autoincrement();
        task.addStringProperty("time").notNull();
        task.addStringProperty("date").notNull();
        task.addStringProperty("note").notNull();
        //task.addStringProperty("image").notNull();

        new DaoGenerator().generateAll(schema, OUT_PATH);
    }

4. This code you get inside NoteDaoGenerator.java file.
5. When you wish to generate your DAO and Domain Objects Run NoteDaoGenerator as a Java Application via your IDE.

#how to use After Generating DAO

  Inside app->daogeneratortest->manager you look a file DataManager.java.
  Here implement all insert,delete,getAllData from table.

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
    
Finally, see the MainListActivity.java, how to work with table 
