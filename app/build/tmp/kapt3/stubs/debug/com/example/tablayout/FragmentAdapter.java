package com.example.tablayout;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\tJ\u0010\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u0010R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/tablayout/FragmentAdapter;", "Landroidx/viewpager2/adapter/FragmentStateAdapter;", "activity", "Landroidx/fragment/app/FragmentActivity;", "(Landroidx/fragment/app/FragmentActivity;)V", "fragmentList", "", "Landroidx/fragment/app/Fragment;", "fragmentTitleList", "", "addFragment", "", "fragment", "title", "createFragment", "position", "", "getItemCount", "getPageTitle", "", "app_debug"})
public final class FragmentAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<androidx.fragment.app.Fragment> fragmentList = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> fragmentTitleList = null;
    
    public FragmentAdapter(@org.jetbrains.annotations.NotNull
    androidx.fragment.app.FragmentActivity activity) {
        super(null);
    }
    
    public final void addFragment(@org.jetbrains.annotations.NotNull
    androidx.fragment.app.Fragment fragment, @org.jetbrains.annotations.NotNull
    java.lang.String title) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public androidx.fragment.app.Fragment createFragment(int position) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.CharSequence getPageTitle(int position) {
        return null;
    }
}