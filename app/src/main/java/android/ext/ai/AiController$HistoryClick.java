package android.ext.ai;

class AiController$HistoryClick implements android.content.DialogInterface.OnClickListener {

    final java.lang.CharSequence[] f1;
    final java.util.ArrayList<java.lang.Object[]> f2;

    AiController$HistoryClick(java.lang.CharSequence[] charSequenceArr, java.util.ArrayList<java.lang.Object[]> arrayList) {
        this.f1 = charSequenceArr;
        this.f2 = arrayList;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        if (i == 0) {
            android.ext.ai.AiController.reopen("new");
            return;
        }
        int iSession = i - 1;
        if (iSession < 0 || iSession >= this.f2.size()) {
            return;
        }
        java.lang.String strId = (java.lang.String) this.f2.get(iSession)[0];
        if (strId == null || strId.length() == 0) {
            return;
        }
        android.ext.ai.AiController.reopen(strId);
    }
}
