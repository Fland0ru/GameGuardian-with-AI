package android.ext.ai;

/* Long-press a history row: rename or delete that session, then refresh the list. */
class AiController$HistoryLong implements android.widget.AdapterView.OnItemLongClickListener {

    final java.util.ArrayList<java.lang.Object[]> f1;

    AiController$HistoryLong(java.util.ArrayList<java.lang.Object[]> arrayList) {
        this.f1 = arrayList;
    }

    @Override // android.widget.AdapterView$OnItemLongClickListener
    public boolean onItemLongClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
        int iSession = i - 1;
        if (iSession < 0 || iSession >= this.f1.size()) {
            return false;
        }
        final java.lang.String strId = (java.lang.String) this.f1.get(iSession)[0];
        java.lang.CharSequence[] charSequenceArr = {android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.ai_rename), android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.ai_delete)};
        android.app.AlertDialog alertDialogCreate = android.ext.f.b().setCustomTitle(android.ext.Tools.g0(catch_.me_.if_.you_.can_.R.string.ai_history_title)).setItems(charSequenceArr, new android.ext.ai.AiController$HistoryAction(strId)).create();
        android.ext.f.m(alertDialogCreate);
        return true;
    }
}
