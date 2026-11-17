package android.ext.ai;

class AiController$Dismiss implements android.content.DialogInterface.OnDismissListener {

    final android.ext.ai.AiController f1;

    AiController$Dismiss(android.ext.ai.AiController controller) {
        this.f1 = controller;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(android.content.DialogInterface dialogInterface) {
        this.f1.shutdown();
    }
}
