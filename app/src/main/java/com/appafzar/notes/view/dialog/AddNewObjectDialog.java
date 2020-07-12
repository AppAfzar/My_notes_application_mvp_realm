package com.appafzar.notes.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.appafzar.notes.databinding.DialogNewFolderBinding;

/**
 * Created by: Hashemi
 * https://github.com/AppAfzar
 * Website: appafzar.com
 */
public class AddNewObjectDialog extends DialogFragment implements View.OnClickListener {
    DialogNewFolderBinding binding;
    private OnSaveClickListener onSaveClickListener;

    public static void start(FragmentActivity activity, OnSaveClickListener listener) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        AddNewObjectDialog dialog = new AddNewObjectDialog();
        dialog.setShowsDialog(true);
        dialog.setOnSaveClickListener(listener);
        dialog.show(fragmentManager, "NewFolderDialogFragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogNewFolderBinding.inflate(LayoutInflater.from(getContext()), container, false);

        binding.txtSave.setOnClickListener(this);
        binding.txtCancel.setOnClickListener(view1 -> dismiss());

        return binding.getRoot();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        String folderName = String.valueOf(binding.edtFolderName.getText()).trim();
        if (folderName.length() > 0) {
            onSaveClickListener.onSaveClick(folderName);
            dismiss();
        }
    }

    public void setOnSaveClickListener(OnSaveClickListener onSaveClickListener) {
        this.onSaveClickListener = onSaveClickListener;
    }


    public interface OnSaveClickListener {
        void onSaveClick(String text);
    }
}
