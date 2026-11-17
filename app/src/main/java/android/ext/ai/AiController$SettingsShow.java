package android.ext.ai;

class AiController$SettingsShow implements java.lang.Runnable {

    AiController$SettingsShow() {
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            android.app.AlertDialog.Builder builder = android.ext.f.b();
            builder.setCustomTitle(android.ext.Tools.g0(catch_.me_.if_.you_.can_.R.string.ai_settings));
            android.view.View viewF = gg_a.d.f(catch_.me_.if_.you_.can_.R.layout.ai_settings, null);
            android.widget.EditText editTextUrl = (android.widget.EditText) viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_url);
            android.widget.EditText editTextKey = (android.widget.EditText) viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_key);
            android.widget.EditText editTextModel = (android.widget.EditText) viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_model);
            editTextUrl.setText(android.ext.ai.AiConfig.url());
            editTextKey.setText(android.ext.ai.AiConfig.key());
            editTextModel.setText(android.ext.ai.AiConfig.model());
            builder.setView(android.ext.InternalKeyboard.e(viewF, false));
            builder.setPositiveButton(android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.confirm), new android.ext.ai.AiController$SettingsSave(editTextUrl, editTextKey, editTextModel));
            builder.setNegativeButton(android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.cancel), (android.content.DialogInterface.OnClickListener) null);
            android.ext.f.m(builder.create());
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI settings dialog", th);
        }
    }
}
