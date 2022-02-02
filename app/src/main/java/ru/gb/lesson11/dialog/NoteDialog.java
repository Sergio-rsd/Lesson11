package ru.gb.lesson11.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ru.gb.lesson11.R;
import ru.gb.lesson11.data.Note;

public class NoteDialog extends DialogFragment {

    public static final String NOTE = "NOTE";
    private Note note;
    private String[] arrayInterest;
    private String selectSaved;
//    public static final String TAG = "happy";
    private String interest = "";
    private TextView currentDateTime;
    Calendar dataChoice = Calendar.getInstance();

    public interface NoteDialogController {
        void update(Note note);
        void create(String title, String description, String interest, String data);

    }

    private NoteDialogController controller;

    @Override
    public void onAttach(@NonNull Context context) {
        if (context instanceof NoteDialogController) {
            this.controller = (NoteDialogController) context;
        } else {
            throw new IllegalStateException("Activity must implement Controller");
        }
        super.onAttach(context);
    }

    public static NoteDialog getInstance(Note note) {
        NoteDialog noteDialog = new NoteDialog();
        Bundle args = new Bundle();
        args.putSerializable(NOTE, note);
        noteDialog.setArguments(args);
        return noteDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        note = (Note) args.getSerializable(NOTE);

        View dialog = getLayoutInflater().inflate(R.layout.dialog_edit_note, null);

        String title = "";
        String description = "";

        //  Spinner
        Spinner spinner = dialog.findViewById(R.id.interest_place);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(requireContext(), R.array.interest_name,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Вызываем адаптер spinner
        spinner.setAdapter(adapter);
        arrayInterest = getResources().getStringArray(R.array.interest_name);
// Spinner end
        currentDateTime = dialog.findViewById(R.id.current_date);

        if (note != null) {
            title = note.getTitle();
            description = note.getDescription();

            if (note.getDataPerformance() == null) {
                note.setDataPerformance("");
            }
            currentDateTime.setText(note.getDataPerformance());
            setInitialDate();

            selectSaved = note.getInterest();
            for (int i = 0; i < arrayInterest.length; i++) {
                if (selectSaved.equals(arrayInterest[i])) {
                    spinner.setSelection(i);
                    break;
                }
            }

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    note.setInterest(arrayInterest[position]);
                    interest = note.getInterest();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        EditText dialogTitle = dialog.findViewById(R.id.edit_note_title);
        EditText dialogDescription = dialog.findViewById(R.id.edit_note_description);

        dialogTitle.setText(title);
        dialogDescription.setText(description);

        currentDateTime.setOnClickListener(this::setDate);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        String buttonText = "";
        if (note == null) {
            buttonText = "Create";
            builder.setTitle("Create note");
            setInitialDate();
        } else {
            buttonText = "Modify";
            builder.setTitle("Modify note");
        }

        builder.setView(dialog)
                .setCancelable(true)
                .setNegativeButton("Cancel", (dialogInterface, which) -> dialogInterface.cancel())
                .setPositiveButton(buttonText, (dialogInterface, which) -> {
                    if (note == null) {
                        interest = spinner.getSelectedItem().toString();
                        setInitialDate();
                        controller.create(
                                dialogTitle.getText().toString(),
                                dialogDescription.getText().toString(),
                                interest,
                                currentDateTime.getText().toString()
                        );
                    } else {
                        note.setTitle(dialogTitle.getText().toString());
                        note.setDescription(dialogDescription.getText().toString());
                        note.setInterest(interest);
                        note.setDataPerformance(currentDateTime.getText().toString());
                        controller.update(note);
                    }
                    dialogInterface.dismiss();
                })
        ;

        return builder.create();
    }

    // установка начальной даты
    @SuppressLint("SimpleDateFormat")
    private void setInitialDate() {
        SimpleDateFormat initData;
        initData = new SimpleDateFormat("dd.MM.yy");
        String currentDate = initData.format(dataChoice.getTime());
        currentDateTime.setText(currentDate);
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(requireContext(), d,
                dataChoice.get(Calendar.YEAR),
                dataChoice.get(Calendar.MONTH),
                dataChoice.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dataChoice.set(Calendar.YEAR, year);
            dataChoice.set(Calendar.MONTH, monthOfYear);
            dataChoice.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };
}
