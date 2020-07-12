package com.appafzar.notes.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.appafzar.notes.activity.base.ToolbarActivity;
import com.appafzar.notes.helper.Const;
import com.appafzar.notes.model.entity.Note;
import com.appafzar.notes.model.interfaces.NoteInterface;
import com.appafzar.notes.presenter.NotePresenter;
import com.appafzar.notes.view.DrawingView;


/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class DrawingActivity extends ToolbarActivity implements NoteInterface {

    private DrawingView view;
    private NotePresenter presenter;
    private Note note;
    private Menu menu;

    //to start a new note creation
    public static void start(Activity activity) {
        start(activity, 0);
    }

    public static void start(Activity activity, int noteId) {
        Intent intent = new Intent(activity, DrawingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Const.NoteEntity.FIELD_ID, noteId);
        activity.startActivity(intent);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        view = new DrawingView(this);
        includeContentView(view);

        presenter = new NotePresenter(this, this);
    }

    @Override
    public void initExtra(Intent intent) {
        super.initExtra(intent);
    }


}
