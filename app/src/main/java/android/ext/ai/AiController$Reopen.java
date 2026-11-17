package android.ext.ai;

class AiController$Reopen implements java.lang.Runnable {

    final java.lang.String f1;

    AiController$Reopen(java.lang.String strPending) {
        this.f1 = strPending;
    }

    @Override // java.lang.Runnable
    public void run() {
        android.ext.ai.AiController controller = android.ext.ai.AiController.instance;
        if (controller != null && !controller.closed) {
            /* live assistant: switch sessions without restarting the script */
            android.ext.ai.AiStore.pending = this.f1;
            controller.applyPendingSession();
            controller.resetSession();
            controller.refreshSessionViews();
            return;
        }
        android.ext.ai.AiStore.pending = this.f1;
        android.ext.ai.AiController.show();
    }
}
