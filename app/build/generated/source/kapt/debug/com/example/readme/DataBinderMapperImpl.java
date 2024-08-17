package com.example.readme;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.readme.databinding.ActivityAgreementBindingImpl;
import com.example.readme.databinding.ActivityCategoryBindingImpl;
import com.example.readme.databinding.ActivityLoginBindingImpl;
import com.example.readme.databinding.ActivityMainBindingImpl;
import com.example.readme.databinding.ActivityStartBindingImpl;
import com.example.readme.databinding.FragmentBookDetailBindingImpl;
import com.example.readme.databinding.FragmentHomeBindingImpl;
import com.example.readme.databinding.FragmentMypageBindingImpl;
import com.example.readme.databinding.FragmentSearchBindingImpl;
import com.example.readme.databinding.StartImageBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYAGREEMENT = 1;

  private static final int LAYOUT_ACTIVITYCATEGORY = 2;

  private static final int LAYOUT_ACTIVITYLOGIN = 3;

  private static final int LAYOUT_ACTIVITYMAIN = 4;

  private static final int LAYOUT_ACTIVITYSTART = 5;

  private static final int LAYOUT_FRAGMENTBOOKDETAIL = 6;

  private static final int LAYOUT_FRAGMENTHOME = 7;

  private static final int LAYOUT_FRAGMENTMYPAGE = 8;

  private static final int LAYOUT_FRAGMENTSEARCH = 9;

  private static final int LAYOUT_STARTIMAGE = 10;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(10);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.readme.R.layout.activity_agreement, LAYOUT_ACTIVITYAGREEMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.readme.R.layout.activity_category, LAYOUT_ACTIVITYCATEGORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.readme.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.readme.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.readme.R.layout.activity_start, LAYOUT_ACTIVITYSTART);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.readme.R.layout.fragment_book_detail, LAYOUT_FRAGMENTBOOKDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.readme.R.layout.fragment_home, LAYOUT_FRAGMENTHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.readme.R.layout.fragment_mypage, LAYOUT_FRAGMENTMYPAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.readme.R.layout.fragment_search, LAYOUT_FRAGMENTSEARCH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.readme.R.layout.start_image, LAYOUT_STARTIMAGE);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYAGREEMENT: {
          if ("layout/activity_agreement_0".equals(tag)) {
            return new ActivityAgreementBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_agreement is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCATEGORY: {
          if ("layout/activity_category_0".equals(tag)) {
            return new ActivityCategoryBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_category is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSTART: {
          if ("layout/activity_start_0".equals(tag)) {
            return new ActivityStartBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_start is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTBOOKDETAIL: {
          if ("layout/fragment_book_detail_0".equals(tag)) {
            return new FragmentBookDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_book_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTHOME: {
          if ("layout/fragment_home_0".equals(tag)) {
            return new FragmentHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_home is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMYPAGE: {
          if ("layout/fragment_mypage_0".equals(tag)) {
            return new FragmentMypageBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_mypage is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTSEARCH: {
          if ("layout/fragment_search_0".equals(tag)) {
            return new FragmentSearchBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_search is invalid. Received: " + tag);
        }
        case  LAYOUT_STARTIMAGE: {
          if ("layout/start_image_0".equals(tag)) {
            return new StartImageBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for start_image is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(2);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "viewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(10);

    static {
      sKeys.put("layout/activity_agreement_0", com.example.readme.R.layout.activity_agreement);
      sKeys.put("layout/activity_category_0", com.example.readme.R.layout.activity_category);
      sKeys.put("layout/activity_login_0", com.example.readme.R.layout.activity_login);
      sKeys.put("layout/activity_main_0", com.example.readme.R.layout.activity_main);
      sKeys.put("layout/activity_start_0", com.example.readme.R.layout.activity_start);
      sKeys.put("layout/fragment_book_detail_0", com.example.readme.R.layout.fragment_book_detail);
      sKeys.put("layout/fragment_home_0", com.example.readme.R.layout.fragment_home);
      sKeys.put("layout/fragment_mypage_0", com.example.readme.R.layout.fragment_mypage);
      sKeys.put("layout/fragment_search_0", com.example.readme.R.layout.fragment_search);
      sKeys.put("layout/start_image_0", com.example.readme.R.layout.start_image);
    }
  }
}
