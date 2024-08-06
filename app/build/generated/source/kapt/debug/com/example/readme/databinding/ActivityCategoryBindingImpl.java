package com.example.readme.databinding;
import com.example.readme.R;
import com.example.readme.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityCategoryBindingImpl extends ActivityCategoryBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.back_btn, 1);
        sViewsWithIds.put(R.id.category_title_tv, 2);
        sViewsWithIds.put(R.id.category_subtitle_tv, 3);
        sViewsWithIds.put(R.id.category_hint_tv, 4);
        sViewsWithIds.put(R.id.linear1, 5);
        sViewsWithIds.put(R.id.checkBox_1, 6);
        sViewsWithIds.put(R.id.checkBox_2, 7);
        sViewsWithIds.put(R.id.checkBox_3, 8);
        sViewsWithIds.put(R.id.linear2, 9);
        sViewsWithIds.put(R.id.checkBox_4, 10);
        sViewsWithIds.put(R.id.checkBox_5, 11);
        sViewsWithIds.put(R.id.linear3, 12);
        sViewsWithIds.put(R.id.checkBox_6, 13);
        sViewsWithIds.put(R.id.checkBox_7, 14);
        sViewsWithIds.put(R.id.linear4, 15);
        sViewsWithIds.put(R.id.checkBox_8, 16);
        sViewsWithIds.put(R.id.checkBox_9, 17);
        sViewsWithIds.put(R.id.linear5, 18);
        sViewsWithIds.put(R.id.checkBox_10, 19);
        sViewsWithIds.put(R.id.checkBox_11, 20);
        sViewsWithIds.put(R.id.checkBox_12, 21);
        sViewsWithIds.put(R.id.linear6, 22);
        sViewsWithIds.put(R.id.checkBox_13, 23);
        sViewsWithIds.put(R.id.checkBox_14, 24);
        sViewsWithIds.put(R.id.linear7, 25);
        sViewsWithIds.put(R.id.checkBox_15, 26);
        sViewsWithIds.put(R.id.checkBox_16, 27);
        sViewsWithIds.put(R.id.linear8, 28);
        sViewsWithIds.put(R.id.checkBox_17, 29);
        sViewsWithIds.put(R.id.checkBox_18, 30);
        sViewsWithIds.put(R.id.category_next_btn, 31);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityCategoryBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 32, sIncludes, sViewsWithIds));
    }
    private ActivityCategoryBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageButton) bindings[1]
            , (android.widget.TextView) bindings[4]
            , (android.widget.Button) bindings[31]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[2]
            , (android.widget.ToggleButton) bindings[6]
            , (android.widget.ToggleButton) bindings[19]
            , (android.widget.ToggleButton) bindings[20]
            , (android.widget.ToggleButton) bindings[21]
            , (android.widget.ToggleButton) bindings[23]
            , (android.widget.ToggleButton) bindings[24]
            , (android.widget.ToggleButton) bindings[26]
            , (android.widget.ToggleButton) bindings[27]
            , (android.widget.ToggleButton) bindings[29]
            , (android.widget.ToggleButton) bindings[30]
            , (android.widget.ToggleButton) bindings[7]
            , (android.widget.ToggleButton) bindings[8]
            , (android.widget.ToggleButton) bindings[10]
            , (android.widget.ToggleButton) bindings[11]
            , (android.widget.ToggleButton) bindings[13]
            , (android.widget.ToggleButton) bindings[14]
            , (android.widget.ToggleButton) bindings[16]
            , (android.widget.ToggleButton) bindings[17]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.LinearLayout) bindings[22]
            , (android.widget.LinearLayout) bindings[25]
            , (android.widget.LinearLayout) bindings[28]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.viewModel == variableId) {
            setViewModel((com.example.readme.ui.category.CategoryViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.example.readme.ui.category.CategoryViewModel ViewModel) {
        this.mViewModel = ViewModel;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}