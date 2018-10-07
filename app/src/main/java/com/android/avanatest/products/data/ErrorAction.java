package com.android.avanatest.products.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class ErrorAction {
    public static final int ACTION_TYPE_NO_OP = 0;
    public static final int ACTION_TYPE_SHOW_ERROR = 1;
    public static final int ACTION_TYPE_DESTROY_VIEW = 3;

    private final String messageTitle;
    private final String messageContent;
    private final int taskId;
    @ActionType
    private int actionType;

    public ErrorAction(int actionType, String messageTitle, String messageContent) {
        this.actionType = actionType;
        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
        this.taskId = -1;
    }

    public ErrorAction(int actionType, int taskId) {

        this.actionType = actionType;
        this.messageTitle = null;
        this.messageContent = null;
        this.taskId = taskId;
    }

    public ErrorAction(int actionType) {

        this.actionType = actionType;
        this.messageTitle = null;
        this.messageContent = null;
        this.taskId = -1;
    }

    public int getActionType() {
        return actionType;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public int getTaskId() {
        return taskId;
    }

    @IntDef({ACTION_TYPE_NO_OP, ACTION_TYPE_SHOW_ERROR, ACTION_TYPE_DESTROY_VIEW})
    @Retention(RetentionPolicy.SOURCE)
    @interface ActionType {
    }
}
