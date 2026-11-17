package android.ext.ai;

/* The AI chat controller: a GameGuardian-style overlay dialog with a transcript,
   an input line and Send / Settings buttons. The assistant logic runs as an embedded
   Lua script on the real GG script engine; this class feeds it user messages
   and renders its output. */
public final class AiController {

    static final java.lang.String END = "\u0000end";
    static volatile android.ext.ai.AiController instance;
    final java.util.concurrent.LinkedBlockingQueue<java.lang.String> queue = new java.util.concurrent.LinkedBlockingQueue<>();
    volatile boolean closed;
    android.app.AlertDialog dialog;
    android.widget.TextView transcriptView;
    android.widget.ScrollView scrollView;
    android.widget.EditText inputView;

    public static void show() {
        android.ext.n2.i(new android.ext.ai.AiController$Show());
    }

    static void enqueue(java.lang.String str) {
        android.ext.ai.AiController controller = instance;
        if (controller != null) {
            controller.queue.offer(str);
        }
    }

    public static java.lang.String awaitUser() {
        android.ext.ai.AiController controller = instance;
        if (controller == null || controller.closed) {
            return null;
        }
        try {
            java.lang.String str = controller.queue.take();
            if (controller.closed || END.equals(str)) {
                return null;
            }
            return str;
        } catch (java.lang.InterruptedException e2) {
            java.lang.Thread.currentThread().interrupt();
            return null;
        }
    }

    public static void appendAi(java.lang.String str) {
        android.ext.ai.AiController controller = instance;
        if (controller != null) {
            controller.appendInternal("AI", str);
        }
    }

    void appendInternal(java.lang.String strWho, java.lang.String strText) {
        android.widget.TextView textView = this.transcriptView;
        if (textView != null) {
            textView.append(strWho + ": " + strText + "\n\n");
            android.widget.ScrollView scrollViewVar = this.scrollView;
            if (scrollViewVar != null) {
                scrollViewVar.post(new android.ext.ai.AiController$Scroll(this.scrollView));
            }
        }
    }

    void showDialog() {
        try {
            android.app.AlertDialog.Builder builder = android.ext.f.b();
            builder.setCustomTitle(android.ext.Tools.g0(catch_.me_.if_.you_.can_.R.string.ai_chat));
            android.view.View viewF = gg_a.d.f(catch_.me_.if_.you_.can_.R.layout.ai_chat, null);
            this.transcriptView = (android.widget.TextView) viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_transcript);
            this.scrollView = (android.widget.ScrollView) viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_scroll);
            this.inputView = (android.widget.EditText) viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_input);
            viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_settings).setOnClickListener(new android.ext.ai.AiController$Settings());
            viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_send).setOnClickListener(new android.ext.ai.AiController$Send(this));
            builder.setView(android.ext.InternalKeyboard.e(viewF, false));
            builder.setNegativeButton(android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.cancel), (android.content.DialogInterface.OnClickListener) null);
            android.app.AlertDialog alertDialogCreate = builder.create();
            this.dialog = alertDialogCreate;
            alertDialogCreate.setOnDismissListener(new android.ext.ai.AiController$Dismiss(this));
            android.ext.f.m(alertDialogCreate);
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI dialog", th);
        }
    }

    void shutdown() {
        this.closed = true;
        this.queue.clear();
        this.queue.offer(END);
        this.dialog = null;
        this.transcriptView = null;
        this.scrollView = null;
        this.inputView = null;
        if (instance == this) {
            instance = null;
        }
    }

    static void openSettings() {
        android.ext.n2.i(new android.ext.ai.AiController$SettingsShow());
    }
}
