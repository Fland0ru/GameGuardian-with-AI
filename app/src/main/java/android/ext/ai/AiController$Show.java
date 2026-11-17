package android.ext.ai;

class AiController$Show implements java.lang.Runnable {

    AiController$Show() {
    }

    @Override // java.lang.Runnable
    public void run() {
        if (android.ext.MainService.instance == null) {
            return;
        }
        if (!android.ext.ai.AiConfig.configured()) {
            android.ext.ai.AiController.openSettings();
            return;
        }
        android.ext.ai.AiController controller = android.ext.ai.AiController.instance;
        if (controller != null && !controller.closed) {
            if (controller.dialog != null && controller.dialog.isShowing()) {
                return;
            }
            /* live assistant: just re-attach a fresh dialog */
            controller.rebindDialog();
            return;
        }
        android.ext.ai.AiController.start();
    }
}
