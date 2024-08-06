package com.example.readme.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\fR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/example/readme/utils/RetrofitClient;", "", "()V", "aladdinRetrofit", "Lretrofit2/Retrofit;", "customRetrofit", "kakaoRetrofit", "getAladdinService", "Lcom/example/readme/data/remote/AladdinService;", "getKakaoLoginService", "Lcom/example/readme/data/remote/KakaoLoginService;", "getReadmeServerService", "Lcom/example/readme/data/remote/ReadmeServerService;", "app_debug"})
public final class RetrofitClient {
    @org.jetbrains.annotations.Nullable
    private static retrofit2.Retrofit aladdinRetrofit;
    @org.jetbrains.annotations.Nullable
    private static retrofit2.Retrofit kakaoRetrofit;
    @org.jetbrains.annotations.Nullable
    private static retrofit2.Retrofit customRetrofit;
    @org.jetbrains.annotations.NotNull
    public static final com.example.readme.utils.RetrofitClient INSTANCE = null;
    
    private RetrofitClient() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.readme.data.remote.AladdinService getAladdinService() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.readme.data.remote.KakaoLoginService getKakaoLoginService() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.readme.data.remote.ReadmeServerService getReadmeServerService() {
        return null;
    }
}