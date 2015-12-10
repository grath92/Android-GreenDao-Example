package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class NoteDaoGenerator {

    private static final String PROJECT_DIR = System.getProperty("user.dir").replace("\\", "/");
    public static final String OUT_PATH =PROJECT_DIR +"/app/src/main/java";

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
}
