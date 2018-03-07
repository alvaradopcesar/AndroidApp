package iosco.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

import iosco.app.R;
import iosco.app.model.dao.NoteDao;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.Exhibitor;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {

    private LinearLayout layoutNotesSave;
    private LinearLayout layoutNotesShare;
    private EditText editNote;

    private int idNote;
    private CalendarEntity calendar;

    private static NotesFragment instance;

    public NotesFragment() {}

    public static NotesFragment getInstance(){
        if(instance == null){
            instance = new NotesFragment();
        }
        return instance;
    }

    public static NotesFragment newInstance(int idNote, CalendarEntity calendar) {
        NotesFragment fragment = new NotesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("idNote", idNote);
        bundle.putSerializable("calendar",calendar);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);

        if (getArguments() != null && getArguments().containsKey("idNote"))
            idNote = (int) getArguments().getSerializable("idNote");
        else
            idNote = 0;

        if (getArguments() != null && getArguments().containsKey("calendar"))
            calendar = (CalendarEntity) getArguments().getSerializable("calendar");

        layoutNotesSave = (LinearLayout)rootView.findViewById(R.id.layoutNotesSave);
        layoutNotesShare = (LinearLayout)rootView.findViewById(R.id.layoutNotesShare);

        editNote = (EditText)rootView.findViewById(R.id.editNote);

        editNote.setText(NoteDao.getInstance(getActivity()).getNote(idNote));

        layoutNotesSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDao.getInstance(getActivity()).updateNote(editNote.getText().toString(), idNote);
                Toast.makeText(getActivity(), "Content save", Toast.LENGTH_SHORT).show();
            }
        });

        layoutNotesShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "IOSCO Note");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, editNote.getText().toString());
                startActivity(Intent.createChooser(sharingIntent, ("Share via")));
            }
        });
        return rootView;
    }

}
