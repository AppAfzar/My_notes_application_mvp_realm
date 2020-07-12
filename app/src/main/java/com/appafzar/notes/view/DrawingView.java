package com.appafzar.notes.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.appafzar.notes.adapter.CustomSpinnerAdapter;
import com.appafzar.notes.databinding.ActivityDrawingBinding;
import com.appafzar.notes.helper.Const;
import com.appafzar.notes.model.entity.Note;
import com.appafzar.notes.presenter.NotePresenter;


@SuppressLint("ViewConstructor")
public class DrawingView extends FrameLayout implements View.OnClickListener {
    private ActivityDrawingBinding binding;
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
    private NotePresenter presenter;

    public DrawingView(@NonNull final Activity activity) {
        super(activity);
        binding = ActivityDrawingBinding.inflate(LayoutInflater.from(activity), this, true);

        // Action item click listeners setup
        binding.txtClear.setOnClickListener(this);

        //Set custom spinner to choose color
        binding.spnColor.setAdapter(new CustomSpinnerAdapter(getContext(),
                CustomSpinnerAdapter.colorDrawables, CustomSpinnerAdapter.colorNames));
        binding.spnColor.setOnItemSelectedListener(spnColorListener);

        //Set custom spinner to choose brush
        binding.spnBrush.setAdapter(new CustomSpinnerAdapter(getContext(),
                CustomSpinnerAdapter.brushDrawables, CustomSpinnerAdapter.brushNames));
        binding.spnBrush.setOnItemSelectedListener(spnBrushListener);
    }

    private Drawable getIcon(int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }

    @Override
    public void onClick(View v) {
        binding.painting.clear();
    }

    /**
     * If the viewing note is not a new note we need to retrieve user data.
     * This method set them on current views.
     *
     * @param note The NoteStruct which contains user's saved data.
     */
    public void editMode(final Note note) {
        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.edtTitle.setText(note.getTitle());

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                Bitmap bmp = BitmapFactory.decodeByteArray(note.getDrawing(), 0, note.getDrawing().length, options);
                binding.painting.drawBitmap(bmp);

                binding.mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public interface OnSaveListener {
        void onSave(String title, DrawingView drawingView);
    }
}
