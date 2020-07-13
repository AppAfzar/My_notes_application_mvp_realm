package com.appafzar.notes.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;

import com.appafzar.notes.App;
import com.appafzar.notes.R;
import com.appafzar.notes.view.activities.base.ToolbarActivity;
import com.appafzar.notes.view.adapters.CustomSpinnerAdapter;
import com.appafzar.notes.databinding.ActivityDrawingBinding;
import com.appafzar.notes.helper.Const;
import com.appafzar.notes.helper.Tools;
import com.appafzar.notes.model.NoteModel;
import com.appafzar.notes.viewmodel.NoteViewModel;


/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class DrawingActivity extends ToolbarActivity {

    private ActivityDrawingBinding binding;
    private NoteViewModel viewModel;
    private NoteModel note;

    //to start a new note creation
    public static void start(Activity activity, int noteId) {
        Intent intent = new Intent(activity, DrawingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Const.NoteEntity.FIELD_ID, noteId);
        activity.startActivity(intent);
    }

    /**
     * An OnItemSelectedListener to change color based on user interests
     */
    AdapterView.OnItemSelectedListener spnColorListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            binding.painting.changeColor(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            binding.painting.changeColor(Const.BLACK);
        }
    };
    /**
     * An OnItemSelectedListener to change color based on user interests
     */
    AdapterView.OnItemSelectedListener spnBrushListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    binding.painting.changeBrush(Const.PEN);
                    break;
                case 1:
                    binding.painting.changeBrush(Const.BRUSH);
                    break;
                case 2:
                    binding.painting.changeBrush(Const.MARKER);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            binding.painting.changeColor(Const.BLACK);
        }
    };

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        binding = ActivityDrawingBinding.inflate(getLayoutInflater());
        includeContentView(binding.getRoot());

        viewModel = new NoteViewModel(this);
        // Action item click listeners setup
        binding.txtClear.setOnClickListener(v -> binding.painting.clear());

        //Set custom spinner to choose color
        binding.spnColor.setAdapter(new CustomSpinnerAdapter(this,
                CustomSpinnerAdapter.colorDrawables, CustomSpinnerAdapter.colorNames));
        binding.spnColor.setOnItemSelectedListener(spnColorListener);

        //Set custom spinner to choose brush
        binding.spnBrush.setAdapter(new CustomSpinnerAdapter(this,
                CustomSpinnerAdapter.brushDrawables, CustomSpinnerAdapter.brushNames));
        binding.spnBrush.setOnItemSelectedListener(spnBrushListener);
    }

    @Override
    public void initExtra(Intent intent) {
        int nid = intent.getIntExtra(Const.FolderEntity.FIELD_ID, 0);
        note = viewModel.getNote(nid);
        if (note == null) {
            finish();
            return;
        }
        editMode(note); //Activity is in edit mode
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawing_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
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
            note.setDrawing(binding.painting.getByteArray());
        });

        super.onBackPressed();
    }

    /**
     * If the viewing note is not a new note we need to retrieve user data.
     * This method set them on current views.
     *
     * @param note The NoteStruct which contains user's saved data.
     */
    public void editMode(final NoteModel note) {
        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.edtTitle.setText(note.getTitle());

                if (note.getDrawing() != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inMutable = true;
                    Bitmap bmp = BitmapFactory.decodeByteArray(note.getDrawing(), 0, note.getDrawing().length, options);
                    binding.painting.drawBitmap(bmp);
                }

                binding.getRoot().getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


}
