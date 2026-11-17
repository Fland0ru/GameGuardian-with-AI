package android.ext.ai;

class AiController$Send implements android.view.View.OnClickListener {

    final android.ext.ai.AiController f1;

    AiController$Send(android.ext.ai.AiController controller) {
        this.f1 = controller;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        android.widget.EditText editText = this.f1.inputView;
        if (editText == null) {
            return;
        }
        java.lang.String strText = editText.getText().toString().trim();
        if (strText.length() == 0) {
            return;
        }
        editText.setText("");
        this.f1.appendInternal("You", strText);
        this.f1.appendInternal("AI", "...");
        android.ext.ai.AiController.enqueue(strText);
    }
}
