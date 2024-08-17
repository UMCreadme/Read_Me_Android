package com.example.readme.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u0000 \n2\u00020\u0001:\u0001\nJ\u001e\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\'J\"\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00032\b\b\u0001\u0010\u0006\u001a\u00020\u00072\b\b\u0001\u0010\t\u001a\u00020\u0005H\'\u00a8\u0006\u000b"}, d2 = {"Lcom/example/readme/data/remote/ReadmeServerService;", "", "getMessages", "Lretrofit2/Call;", "", "Lcom/example/readme/ui/community/Chat;", "communityId", "", "postMessage", "chat", "Companion", "app_debug"})
public abstract interface ReadmeServerService {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String BASE_URL = "https://your.api.base.url/";
    @org.jetbrains.annotations.NotNull
    public static final com.example.readme.data.remote.ReadmeServerService.Companion Companion = null;
    
    @retrofit2.http.GET(value = "community/{communityId}/messages")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<java.util.List<com.example.readme.ui.community.Chat>> getMessages(@retrofit2.http.Path(value = "communityId")
    @org.jetbrains.annotations.NotNull
    java.lang.String communityId);
    
    @retrofit2.http.POST(value = "community/{communityId}/messages")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<com.example.readme.ui.community.Chat> postMessage(@retrofit2.http.Path(value = "communityId")
    @org.jetbrains.annotations.NotNull
    java.lang.String communityId, @retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.example.readme.ui.community.Chat chat);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/readme/data/remote/ReadmeServerService$Companion;", "", "()V", "BASE_URL", "", "app_debug"})
    public static final class Companion {
        @org.jetbrains.annotations.NotNull
        public static final java.lang.String BASE_URL = "https://your.api.base.url/";
        
        private Companion() {
            super();
        }
    }
}