package android.ext.ai;

/* The AI chat controller: a GameGuardian-style overlay dialog with a transcript,
   an input line and Send / Settings / History buttons. The assistant logic runs as an
   embedded Lua script on the real GG script engine; this class feeds it user messages
   and renders its output. Chat sessions persist (AiStore) and are resumed by default. */
public final class AiController {

    static final java.lang.String END = "\u0000end";
    static final java.lang.String RESET = "\u0000reset";
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

    /* Resolve AiStore.pending ("auto" | "new" | session id) into activeId. */
    static void applyPendingSession() {
        java.lang.String strPending = android.ext.ai.AiStore.pending;
        android.ext.ai.AiStore.pending = "auto";
        java.lang.String strId = null;
        if (strPending == null || "auto".equals(strPending)) {
            strId = android.ext.ai.AiStore.lastId();
        } else if (!"new".equals(strPending)) {
            strId = strPending;
        }
        if (strId != null && !android.ext.ai.AiStore.file(strId).exists()) {
            strId = android.ext.ai.AiStore.lastId();
        }
        android.ext.ai.AiStore.activeId = strId;
    }

    /* Cold start: resolve the session, show the dialog and launch the assistant script. */
    static void start() {
        android.ext.ai.AiController.applyPendingSession();
        android.ext.ai.AiController controllerNew = new android.ext.ai.AiController();
        instance = controllerNew;
        controllerNew.showDialog();
        try {
            new android.ext.Script(android.ext.ai.AiScriptSource.LUA, 0, "AI").R3();
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI script launch", th);
            controllerNew.shutdown();
        }
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

    public static void clearAi() {
        android.ext.ai.AiController controller = instance;
        if (controller != null) {
            android.widget.TextView textView = controller.transcriptView;
            if (textView != null) {
                textView.setText("");
            }
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

    void doSend() {
        android.widget.EditText editText = this.inputView;
        if (editText == null) {
            return;
        }
        java.lang.String strText = editText.getText().toString().trim();
        if (strText.length() == 0) {
            return;
        }
        editText.setText("");
        this.appendInternal("You", strText);
        this.appendInternal("AI", "...");
        android.ext.ai.AiController.enqueue(strText);
    }

    void showDialog() {
        try {
            android.app.AlertDialog.Builder builder = android.ext.f.b();
            builder.setCustomTitle(android.ext.Tools.g0(catch_.me_.if_.you_.can_.R.string.ai_chat));
            android.view.View viewF = gg_a.d.f(catch_.me_.if_.you_.can_.R.layout.ai_chat, null);
            this.bindViews(viewF);
            viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_settings).setOnClickListener(new android.ext.ai.AiController$Settings());
            viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_history).setOnClickListener(new android.ext.ai.AiController$History());
            viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_send).setOnClickListener(new android.ext.ai.AiController$Send(this));
            this.inputView.setOnEditorActionListener(new android.ext.ai.AiController$InputAction(this));
            builder.setView(android.ext.InternalKeyboard.e(viewF, false));
            builder.setNegativeButton(android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.cancel), (android.content.DialogInterface.OnClickListener) null);
            android.app.AlertDialog alertDialogCreate = builder.create();
            this.dialog = alertDialogCreate;
            alertDialogCreate.setOnDismissListener(new android.ext.ai.AiController$Dismiss(this));
            android.ext.f.m(alertDialogCreate);
            this.scrollView.post(new android.ext.ai.AiController$Scroll(this.scrollView));
        } catch (java.lang.Throwable th) {
            android.ext.e1.l("AI dialog", th);
        }
    }

    /* Re-attach a fresh dialog to a live controller (assistant script keeps running). */
    void rebindDialog() {
        this.showDialog();
    }

    private void bindViews(android.view.View viewF) {
        this.transcriptView = (android.widget.TextView) viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_transcript);
        this.scrollView = (android.widget.ScrollView) viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_scroll);
        this.inputView = (android.widget.EditText) viewF.findViewById(catch_.me_.if_.you_.can_.R.id.ai_input);
        this.transcriptView.setText(initialTranscript());
    }

    /* Transcript text for the current active session (greeting when none). */
    private java.lang.String initialTranscript() {
        java.lang.String strInitial = android.ext.b2.f(catch_.me_.if_.you_.can_.R.string.ai_hello);
        if (android.ext.ai.AiStore.activeId != null) {
            org.json.JSONObject jSONObject = android.ext.ai.AiStore.loadFile(android.ext.ai.AiStore.activeId);
            if (jSONObject != null) {
                java.lang.String strTranscript = jSONObject.optString("transcript", "");
                if (strTranscript.trim().length() > 0) {
                    strInitial = strTranscript;
                }
            }
        }
        return strInitial;
    }

    /* Refresh the transcript of a showing dialog after switching sessions. */
    void refreshSessionViews() {
        android.widget.TextView textView = this.transcriptView;
        if (textView != null) {
            textView.setText(initialTranscript());
            android.widget.ScrollView scrollViewVar = this.scrollView;
            if (scrollViewVar != null) {
                scrollViewVar.post(new android.ext.ai.AiController$Scroll(this.scrollView));
            }
        }
    }

    /* Dialog closed: detach the views but keep the assistant script and its queue alive,
       so reopening is instant and in-flight replies still complete and get saved. */
    void detachViews() {
        this.dialog = null;
        this.transcriptView = null;
        this.scrollView = null;
        this.inputView = null;
    }

    /* Switch the live script to another session (or a new chat) without restarting it. */
    static void resetSession() {
        android.ext.ai.AiController controller = instance;
        if (controller != null && !controller.closed) {
            controller.queue.offer(RESET);
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

    static void openHistory() {
        android.ext.n2.i(new android.ext.ai.AiController$HistoryList());
    }

    static void reopen(java.lang.String strPending) {
        android.ext.n2.i(new android.ext.ai.AiController$Reopen(strPending));
    }
}
