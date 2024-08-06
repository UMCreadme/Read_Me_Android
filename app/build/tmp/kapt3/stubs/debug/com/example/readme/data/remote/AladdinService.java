package com.example.readme.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u0000 \t2\u00020\u0001:\u0001\tJ,\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\u00062\b\b\u0001\u0010\b\u001a\u00020\u0006H\'\u00a8\u0006\n"}, d2 = {"Lcom/example/readme/data/remote/AladdinService;", "", "searchBooks", "Lretrofit2/Call;", "Lcom/example/readme/data/remote/AladdinResponse;", "ttbKey", "", "query", "output", "Companion", "app_debug"})
public abstract interface AladdinService {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String BASE_URL = "https://www.aladin.co.kr/ttb/api/";
    @org.jetbrains.annotations.NotNull
    public static final com.example.readme.data.remote.AladdinService.Companion Companion = null;
    
    @retrofit2.http.GET(value = "ItemSearch.aspx")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<com.example.readme.data.remote.AladdinResponse> searchBooks(@retrofit2.http.Query(value = "ttbkey")
    @org.jetbrains.annotations.NotNull
    java.lang.String ttbKey, @retrofit2.http.Query(value = "Query")
    @org.jetbrains.annotations.NotNull
    java.lang.String query, @retrofit2.http.Query(value = "output")
    @org.jetbrains.annotations.NotNull
    java.lang.String output);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/readme/data/remote/AladdinService$Companion;", "", "()V", "BASE_URL", "", "app_debug"})
    public static final class Companion {
        @org.jetbrains.annotations.NotNull
        public static final java.lang.String BASE_URL = "https://www.aladin.co.kr/ttb/api/";
        
        private Companion() {
            super();
        }
    }
}