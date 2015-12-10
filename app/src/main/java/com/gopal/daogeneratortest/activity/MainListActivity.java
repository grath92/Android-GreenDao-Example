package com.gopal.daogeneratortest.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gopal.daogeneratortest.R;
import com.gopal.daogeneratortest.adapter.MainListAdapter;
import com.gopal.daogeneratortest.manager.DataManager;
import com.gopal.daogeneratortest.manager.InterfaceDataManager;
import com.gopal.model.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainListActivity extends AppCompatActivity {

    public static final String TASK_ID = "taskID";

    private ListView mTaskList;
    private MainListAdapter mTaskListAdapter;
    private TextView txtTime, txtDate, txtSave;
    private EditText editTxtField;
    private Calendar calendar;
    private int year, month, day, hour, minute;
    private String dateView, timeView, AMPM;

    /**
     * Manages the database for this application..
     */
    private InterfaceDataManager dataManager;
    private List<Task> mTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        // init database manager
        dataManager = new DataManager(this);
        mTasks = new ArrayList<Task>();
        mTaskList = (ListView) findViewById(R.id.rv_main);
        createCalendarDetail();
        refreshTaskList();

         /*
        * Go To the Next Activity (TaskDetailActivity) on list item click........
        * */
        mTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = mTasks.get(position);
                if (task != null) {
                    Intent intent = new Intent(MainListActivity.this, TaskDetailActivity.class);
                    intent.putExtra(TASK_ID, task.getId());
                    startActivityForResult(intent, 1);
                }
            }
        });

        /*
        * Delete a selected Item from list, When taking long press.....
        * */
        mTaskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Task task = mTasks.get(position);
                DataManager.getInstance(getApplicationContext()).deleteTaskById(task.getId());
                refreshTaskList();
                return true;
            }
        });

    }


    /*
    * SetUp Calendar for TIME and DATE from today.....
    * */
    private void createCalendarDetail() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
    }

    /*
    * SetUp Data to list and Update list also, after Adding another data or Removing Data..
    * */
    private void refreshTaskList() {
        try {
            mTasks = DataManager.getInstance(this).listTasks();
            if (mTasks != null) {
                mTaskListAdapter = new MainListAdapter(getApplicationContext());
                mTaskListAdapter.addAll(mTasks);
                mTaskList.setAdapter(mTaskListAdapter);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_sign, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_task:
                // About option clicked.
                addTaskDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    * Adding a Task Dialog.....
    * */
    private void addTaskDialog() {
        final Dialog setTaskDialog = new Dialog(this);
        setTaskDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTaskDialog.setContentView(R.layout.dialog_add_task);
        txtTime = (TextView) setTaskDialog.findViewById(R.id.tv_time);
        txtDate = (TextView) setTaskDialog.findViewById(R.id.tv_date);
        editTxtField = (EditText) setTaskDialog.findViewById(R.id.et_field);
        txtSave = (TextView) setTaskDialog.findViewById(R.id.tv_save);

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MainListActivity.this, myTimeListener, hour, minute, false).show();
            }
        });
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainListActivity.this, myDateListener, year, month, day).show();

            }
        });
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataToDataBase();
                refreshTaskList();
                setTaskDialog.dismiss();

            }
        });
        setTaskDialog.show();
    }

    /*
    * Insert Task Detail into Database Table.....
    * */
    private void addDataToDataBase() {
        Task task = new Task();
        task.setTime(txtTime.getText().toString().trim());
        task.setDate(txtDate.getText().toString().trim());
        task.setNote(editTxtField.getText().toString().trim());
        dataManager.insertTask(task);
    }

    /*
    * Set the Date from DatePicker Dialog.....
    * */
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            if ((month + 1) < 10) {
                dateView = (new StringBuilder().append(day).append("/").append("0").append(month + 1).append("/").append(year)).toString();
            } else {
                dateView = (new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year)).toString();
            }
            txtDate.setText(dateView);
        }
    };

    /*
    * Set yhe Time from TimePicker Dialog.....
    * */
    private TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            if (hour < 12) {
                AMPM = "AM";
            } else {
                AMPM = "PM";
                hour = hour - 12;
            }
            if (minute < 10) {
                timeView = (new StringBuilder().append(hour).append(":").append("0").append(minute).append(AMPM)).toString();
            } else {
                timeView = (new StringBuilder().append(hour).append(":").append(minute).append(AMPM)).toString();
            }
            txtTime.setText(timeView);
        }
    };

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
