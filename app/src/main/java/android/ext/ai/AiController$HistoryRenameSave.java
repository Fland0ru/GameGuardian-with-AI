package android.ext.ai;

class AiController$HistoryRenameSave implements android.content.DialogInterface.OnClickListener {

    final java.lang.String f1;
    final android.widget.EditText f2;

    AiController$HistoryRenameSave(java.lang.String strId, android.widget.EditText editText) {
        this.f1 = strId;
        this.f2 = editText;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        android.ext.ai.AiStore.rename(this.f1, this.f2.getText().toString());
        android.ext.ai.AiController.openHistory();
    }
}
