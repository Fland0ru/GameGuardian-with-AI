package android.ext.ai;

class AiController$Scroll implements java.lang.Runnable {

    final android.widget.ScrollView f1;

    AiController$Scroll(android.widget.ScrollView scrollView) {
        this.f1 = scrollView;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1.fullScroll(android.view.View.FOCUS_DOWN);
    }
}
