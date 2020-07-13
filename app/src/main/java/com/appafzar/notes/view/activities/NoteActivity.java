package com.appafzar.notes.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.appafzar.notes.App;
import com.appafzar.notes.R;
import com.appafzar.notes.view.activities.base.ToolbarActivity;
import com.appafzar.notes.databinding.ActivityNoteBinding;
import com.appafzar.notes.helper.Const;
import com.appafzar.notes.helper.TextStyleHandler;
import com.appafzar.notes.helper.Tools;
import com.appafzar.notes.model.NoteModel;
import com.appafzar.notes.viewmodel.NoteViewModel;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class NoteActivity extends ToolbarActivity {
    private ActivityNoteBinding binding;
    private TextStyleHandler styleHandler;
    private NoteViewModel viewModel;
    private NoteModel note;

    public static void start(Activity activity, int noteId) {
        Intent intent = new Intent(activity, NoteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Const.NoteEntity.FIELD_ID, noteId);
        activity.startActivity(intent);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        binding = ActivityNoteBinding.inflate(getLayoutInflater());
        viewModel = new NoteViewModel(this);
        styleHandler = new TextStyleHandler(this);
        includeContentView(binding.getRoot());
    }

    @Override
    public void initExtra(Intent intent) {
        int nid = intent.getIntExtra(Const.FolderEntity.FIELD_ID, 0);
        note = viewModel.getNote(nid);
        if (note == null) {
            finish();
            return;
        }
        setData(note); //Activity is in edit mode
    }

    /**
     * If the viewing note is not a new note we need to retrieve user data.
     * This method set data on current views.
     *
     * @param note The NoteStruct which contains user's saved data.
     */
    private void setData(NoteModel note) {
        binding.edtTitle.setText(note.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.edtText.setText(Html.fromHtml(note.getText(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            binding.edtText.setText(Html.fromHtml(note.getText()));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_bold) {
            styleHandler.applyStyleToText(binding.edtText, Typeface.BOLD);
        } else if (id == R.id.action_italic) {
            styleHandler.applyStyleToText(binding.edtText, Typeface.ITALIC);
        } else if (id == R.id.action_undo) {
            setData(note);
        } else if (id == R.id.action_delete) {
            viewModel.deleteNote(note.getId());
            finishActivity();
        } else return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (Tools.isNullOrEmpty(binding.edtTitle.getText().toString().trim())) {
            showMessage(getString(R.string.title_is_required));
            return;
        }
        App.realm.executeTransaction(realm -> {
            note.setTitle(String.valueOf(binding.edtTitle.getText()));
            note.setText(Html.toHtml(binding.edtText.getText()));
        });
        super.onBackPressed();
    }

}