package nncb.kimxu.vck.modelev;

import nncb.kimxu.vck.model.Note;

/**
 * Created by xuzhiguo on 15/9/10.
 */
public class AddNote2Main {
    private Note note;
    private boolean isEdit;
    public AddNote2Main(Note note) {
        this.note = note;
    }

    public AddNote2Main(Note note, boolean isEdit) {
        this.note = note;
        this.isEdit = isEdit;
    }

    public Note getNote() {
        return note;
    }

    public boolean isEdit() {
        return isEdit;
    }
}
