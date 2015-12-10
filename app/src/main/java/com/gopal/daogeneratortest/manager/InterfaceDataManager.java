package com.gopal.daogeneratortest.manager;


import com.gopal.model.Task;

import java.util.List;

/**
 * Interface that provides methods for managing the database inside the Application.
 *
 * @author grath92
 */
public interface InterfaceDataManager {
    /**
     * Closing available connections
     */
    public void closeDbConnections();

    /**
     * Delete all tables and content from our database
     */
    public void dropDatabase();

    /**
     * Insert a user into the DB
     *
     * @param task to be inserted
     */
    public Task insertTask(Task task);

    /**
     * List all the users from the DB
     *
     * @return list of users
     */
    public List<Task> listTasks();

    /**
     * Update a user from the DB
     *
     * @param user to be updated
     */
    public void updateTask(Task user);

    /**
     * Delete a user with a certain id from the DB
     *
     * @param taskId of users to be deleted
     */
    public boolean deleteTaskById(Long taskId);

    /**
     * @param taskId - of the user we want to fetch
     * @return Return a user by its id
     */
    Task getTaskById(Long taskId);

    /**
     * Delete all the users from the DB
     */
    public void deleteTasks();

}
