package android.ext.ai;

class AiController$HistoryAction implements android.content.DialogInterface.OnClickListener {

    final java.lang.String f1;

    AiController$HistoryAction(java.lang.String strId) {
        this.f1 = strId;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        if (i == 0) {
            android.ext.n2.i(new android.ext.ai.AiController$HistoryRename(this.f1));
            return;
        }
        if (i == 1) {
            android.ext.ai.AiStore.delete(this.f1);
            android.ext.ai.AiController.openHistory();
        }
    }
}
