package com.example.readme.ui.community;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/example/readme/ui/community/ChatActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/readme/databinding/ActivityChatBinding;", "chatAdapter", "Lcom/example/readme/ui/community/ChatAdapter;", "chatList", "", "Lcom/example/readme/ui/community/Chat;", "communityId", "", "userId", "fetchMessages", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "app_debug"})
public final class ChatActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.readme.databinding.ActivityChatBinding binding;
    private com.example.readme.ui.community.ChatAdapter chatAdapter;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.example.readme.ui.community.Chat> chatList = null;
    @org.jetbrains.annotations.NotNull
    private java.lang.String userId = "";
    @org.jetbrains.annotations.NotNull
    private final java.lang.String communityId = "1";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String USERID = "userid";
    @org.jetbrains.annotations.NotNull
    public static final com.example.readme.ui.community.ChatActivity.Companion Companion = null;
    
    public ChatActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void fetchMessages() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/readme/ui/community/ChatActivity$Companion;", "", "()V", "USERID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}