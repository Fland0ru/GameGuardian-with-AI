package android.ext.ai;

class AiController$Show implements java.lang.Runnable {

    AiController$Show() {
    }

    @Override // java.lang.Runnable
    public void run() {
        if (android.ext.MainService.instance == null) {
            return;
        }
        android.ext.ai.AiController controller = android.ext.ai.AiController.instance;
        if (controller != null && controller.closed == false && controller.dialog != null && controller.dialog.isShowing()) {
            return;
        }
        if (!android.ext.ai.AiConfig.configured()) {
            android.ext.ai.AiController.openSettings();
            return;
        }
        android.ext.ai.AiController controllerNew = new android.ext.ai.AiController();
        android.ext.ai.AiController.instance = controllerNew;
        controllerNew.showDialog();
        try {
            new android.ext.Script(android.ext.ai.AiScriptSource.LUA, 0, "AI").R3();
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI script launch", th);
            controllerNew.shutdown();
        }
    }
}
