package com.example.readme.databinding;
import com.example.readme.R;
import com.example.readme.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentBookDetailBindingImpl extends FragmentBookDetailBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.back_community_list, 1);
        sViewsWithIds.put(R.id.book_name_layout, 2);
        sViewsWithIds.put(R.id.bookTitle, 3);
        sViewsWithIds.put(R.id.bookAuthor, 4);
        sViewsWithIds.put(R.id.bookCover, 5);
        sViewsWithIds.put(R.id.user_layout, 6);
        sViewsWithIds.put(R.id.user_profile, 7);
        sViewsWithIds.put(R.id.userName, 8);
        sViewsWithIds.put(R.id.userLocation, 9);
        sViewsWithIds.put(R.id.userComment, 10);
        sViewsWithIds.put(R.id.tagsLabel, 11);
        sViewsWithIds.put(R.id.tagsContainer, 12);
        sViewsWithIds.put(R.id.tagsFlow, 13);
        sViewsWithIds.put(R.id.btn_layout, 14);
        sViewsWithIds.put(R.id.member_layout, 15);
        sViewsWithIds.put(R.id.member, 16);
        sViewsWithIds.put(R.id.memberCount, 17);
        sViewsWithIds.put(R.id.chatButton, 18);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentBookDetailBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private FragmentBookDetailBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[1]
            , (android.widget.TextView) bindings[4]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.Button) bindings[18]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.TextView) bindings[17]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[12]
            , (androidx.constraintlayout.helper.widget.Flow) bindings[13]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[10]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[8]
            , (android.widget.ImageView) bindings[7]
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
            setViewModel((com.example.readme.ui.community.BookDetailViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable com.example.readme.ui.community.BookDetailViewModel ViewModel) {
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