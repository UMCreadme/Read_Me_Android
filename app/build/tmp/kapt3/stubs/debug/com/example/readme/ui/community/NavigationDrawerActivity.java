package com.example.readme.ui.community;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0015J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/example/readme/ui/community/NavigationDrawerActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/google/android/material/navigation/NavigationView$OnNavigationItemSelectedListener;", "()V", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onNavigationItemSelected", "", "item", "Landroid/view/MenuItem;", "replaceFragment", "fragment", "Landroidx/fragment/app/Fragment;", "app_debug"})
public final class NavigationDrawerActivity extends androidx.appcompat.app.AppCompatActivity implements com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener {
    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    
    public NavigationDrawerActivity() {
        super();
    }
    
    @java.lang.Override
    @android.annotation.SuppressLint(value = {"MissingInflatedId"})
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    public boolean onNavigationItemSelected(@org.jetbrains.annotations.NotNull
    android.view.MenuItem item) {
        return false;
    }
    
    private final void replaceFragment(androidx.fragment.app.Fragment fragment) {
    }
}