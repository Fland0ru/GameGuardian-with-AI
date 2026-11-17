package android.ext.ai;

/* Chat history list: New chat row plus every saved session (title + time).
   Tap resumes a session, long-press offers rename / delete. */
class AiController$HistoryList implements java.lang.Runnable {

    AiController$HistoryList() {
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            final java.util.ArrayList<java.lang.Object[]> sessions = android.ext.ai.AiStore.list();
            java.util.ArrayList<java.lang.CharSequence> items = new java.util.ArrayList<>();
            items.add(android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.ai_new_chat));
            if (sessions.isEmpty()) {
                items.add(android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.ai_history_empty));
            }
            java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("MM-dd HH:mm", java.util.Locale.US);
            for (java.lang.Object[] objArr : sessions) {
                items.add(java.lang.String.valueOf(objArr[1]) + "   " + simpleDateFormat.format(new java.util.Date(((java.lang.Long) objArr[2]).longValue())));
            }
            final java.lang.CharSequence[] charSequenceArr = items.toArray(new java.lang.CharSequence[0]);
            android.app.AlertDialog.Builder builder = android.ext.f.b();
            builder.setCustomTitle(android.ext.Tools.g0(catch_.me_.if_.you_.can_.R.string.ai_history_title));
            builder.setItems(charSequenceArr, new android.ext.ai.AiController$HistoryClick(charSequenceArr, sessions));
            builder.setNegativeButton(android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.cancel), (android.content.DialogInterface.OnClickListener) null);
            android.app.AlertDialog alertDialogCreate = builder.create();
            android.widget.ListView listView = alertDialogCreate.getListView();
            if (listView != null) {
                listView.setOnItemLongClickListener(new android.ext.ai.AiController$HistoryLong(sessions));
            }
            android.ext.f.m(alertDialogCreate);
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI history dialog", th);
        }
    }
}
