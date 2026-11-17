package android.ext.ai;

class AiController$Dismiss implements android.content.DialogInterface.OnDismissListener {

    final android.ext.ai.AiController f1;

    AiController$Dismiss(android.ext.ai.AiController controller) {
        this.f1 = controller;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(android.content.DialogInterface dialogInterface) {
        /* Keep the assistant script and its queue alive: only detach the views,
           so reopening is instant and in-flight replies still complete and get saved. */
        this.f1.detachViews();
    }
}
