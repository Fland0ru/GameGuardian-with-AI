package android.ext.ai;

/* Keyboard "send" action on the message input forwards to the Send button. */
class AiController$InputAction implements android.widget.TextView.OnEditorActionListener {

    final android.ext.ai.AiController f1;

    AiController$InputAction(android.ext.ai.AiController controller) {
        this.f1 = controller;
    }

    @Override // android.widget.TextView$OnEditorActionListener
    public boolean onEditorAction(android.widget.TextView textView, int i, android.view.KeyEvent keyEvent) {
        if (i == android.view.inputmethod.EditorInfo.IME_ACTION_SEND || i == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
            this.f1.doSend();
            return true;
        }
        return false;
    }
}
