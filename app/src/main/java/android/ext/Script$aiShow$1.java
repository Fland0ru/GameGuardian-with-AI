package android.ext;

final class Script$aiShow$1 implements java.lang.Runnable {

    final java.lang.String f1;

    Script$aiShow$1(java.lang.String str) {
        this.f1 = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        android.ext.ai.AiController.appendAi(this.f1);
    }
}
