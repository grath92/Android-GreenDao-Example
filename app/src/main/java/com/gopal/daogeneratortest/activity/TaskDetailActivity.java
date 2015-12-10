package com.gopal.daogeneratortest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.gopal.daogeneratortest.R;
import com.gopal.daogeneratortest.manager.DataManager;
import com.gopal.daogeneratortest.manager.InterfaceDataManager;
import com.gopal.model.Task;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView txtTime, txtDate, txtTask;
    private Task task;
    private InterfaceDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // init database manager
        dataManager = new DataManager(this);

        long taskId = getIntent().getLongExtra(MainListActivity.TASK_ID, -1L);
        if (taskId != -1) {
            task = dataManager.getTaskById(taskId);
        }
        txtTime = (TextView) findViewById(R.id.tv_time);
        txtDate = (TextView) findViewById(R.id.tv_date);
        txtTask = (TextView) findViewById(R.id.tv_task);

        if (task != null) {
            txtTime.setText(task.getTime());
            txtDate.setText(task.getDate());
            txtTask.setText(task.getNote());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    /**
     * Called after your activity has been stopped, prior to it being started again.
     * Always followed by onStart()
     */
    @Override
    protected void onRestart() {
        if (dataManager == null)
            dataManager = new DataManager(this);

        super.onRestart();
    }

    /**
     * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(), for your activity
     * to start interacting with the user.
     */
    @Override
    protected void onResume() {
        // init database manager
        dataManager = DataManager.getInstance(this);

        super.onResume();
    }

    /**
     * Called when you are no longer visible to the user.
     */
    @Override
    protected void onStop() {
        if (dataManager != null)
            dataManager.closeDbConnections();

        super.onStop();
    }
}
