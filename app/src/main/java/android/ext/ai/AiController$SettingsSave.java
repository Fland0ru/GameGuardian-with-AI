package android.ext.ai;

class AiController$SettingsSave implements android.content.DialogInterface.OnClickListener {

    final android.widget.EditText f1;
    final android.widget.EditText f2;
    final android.widget.EditText f3;

    AiController$SettingsSave(android.widget.EditText editTextUrl, android.widget.EditText editTextKey, android.widget.EditText editTextModel) {
        this.f1 = editTextUrl;
        this.f2 = editTextKey;
        this.f3 = editTextModel;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        android.ext.ai.AiConfig.save(this.f1.getText().toString(), this.f2.getText().toString(), this.f3.getText().toString());
        try {
            android.widget.Toast.makeText(android.ext.Tools.f0(), android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.ai_saved), android.widget.Toast.LENGTH_SHORT).show();
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI toast", th);
        }
    }
}
