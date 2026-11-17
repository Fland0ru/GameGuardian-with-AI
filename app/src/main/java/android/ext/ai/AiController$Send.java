package android.ext.ai;

class AiController$Send implements android.view.View.OnClickListener {

    final android.ext.ai.AiController f1;

    AiController$Send(android.ext.ai.AiController controller) {
        this.f1 = controller;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        this.f1.doSend();
    }
}
