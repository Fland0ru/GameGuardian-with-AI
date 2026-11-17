package android.ext.ai;

/* Rename dialog for a saved chat session. */
class AiController$HistoryRename implements java.lang.Runnable {

    final java.lang.String f1;

    AiController$HistoryRename(java.lang.String strId) {
        this.f1 = strId;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            org.json.JSONObject jSONObject = android.ext.ai.AiStore.loadFile(this.f1);
            android.app.AlertDialog.Builder builder = android.ext.f.b();
            builder.setCustomTitle(android.ext.Tools.g0(catch_.me_.if_.you_.can_.R.string.ai_rename));
            android.view.View viewF = gg_a.d.f(catch_.me_.if_.you_.can_.R.layout.ai_rename, null);
            final android.widget.EditText editText = (android.widget.EditText) viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_rename_input);
            if (jSONObject != null) {
                editText.setText(jSONObject.optString("title", ""));
            }
            builder.setView(android.ext.InternalKeyboard.e(viewF, false));
            builder.setPositiveButton(android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.confirm), new android.ext.ai.AiController$HistoryRenameSave(this.f1, editText));
            builder.setNegativeButton(android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.cancel), (android.content.DialogInterface.OnClickListener) null);
            android.ext.f.m(builder.create());
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI rename dialog", th);
        }
    }
}
