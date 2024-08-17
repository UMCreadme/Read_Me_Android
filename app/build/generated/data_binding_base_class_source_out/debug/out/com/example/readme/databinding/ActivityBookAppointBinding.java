// Generated by view binder compiler. Do not edit!
package com.example.readme.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.readme.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityBookAppointBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Spinner ampmSpinner;

  @NonNull
  public final Button confirmButton;

  @NonNull
  public final Spinner daySpinner;

  @NonNull
  public final GridLayout gridLayout;

  @NonNull
  public final Spinner hourSpinner;

  @NonNull
  public final TextView meetWhere;

  @NonNull
  public final LinearLayout meetingPlaceSection;

  @NonNull
  public final LinearLayout meetingTimeSection;

  @NonNull
  public final Spinner monthSpinner;

  @NonNull
  public final TextView placeBusan;

  @NonNull
  public final TextView placeCafemaru;

  @NonNull
  public final TextView placeChungbuk;

  @NonNull
  public final TextView placeChungnam;

  @NonNull
  public final TextView placeDaegu;

  @NonNull
  public final TextView placeDaejeon;

  @NonNull
  public final TextView placeGangwon;

  @NonNull
  public final TextView placeGwangju;

  @NonNull
  public final TextView placeGyeongbuk;

  @NonNull
  public final TextView placeGyeonggi;

  @NonNull
  public final TextView placeGyeongnam;

  @NonNull
  public final TextView placeIncheon;

  @NonNull
  public final TextView placeJeju;

  @NonNull
  public final TextView placeJeonbuk;

  @NonNull
  public final TextView placeJeonnam;

  @NonNull
  public final TextView placeSejong;

  @NonNull
  public final TextView placeSeoul;

  @NonNull
  public final TextView placeStudyCafe;

  @NonNull
  public final TextView placeStudyRoom;

  @NonNull
  public final TextView placeUlsan;

  @NonNull
  public final LinearLayout specificPlaceSection;

  @NonNull
  public final TextView title;

  private ActivityBookAppointBinding(@NonNull RelativeLayout rootView, @NonNull Spinner ampmSpinner,
      @NonNull Button confirmButton, @NonNull Spinner daySpinner, @NonNull GridLayout gridLayout,
      @NonNull Spinner hourSpinner, @NonNull TextView meetWhere,
      @NonNull LinearLayout meetingPlaceSection, @NonNull LinearLayout meetingTimeSection,
      @NonNull Spinner monthSpinner, @NonNull TextView placeBusan, @NonNull TextView placeCafemaru,
      @NonNull TextView placeChungbuk, @NonNull TextView placeChungnam,
      @NonNull TextView placeDaegu, @NonNull TextView placeDaejeon, @NonNull TextView placeGangwon,
      @NonNull TextView placeGwangju, @NonNull TextView placeGyeongbuk,
      @NonNull TextView placeGyeonggi, @NonNull TextView placeGyeongnam,
      @NonNull TextView placeIncheon, @NonNull TextView placeJeju, @NonNull TextView placeJeonbuk,
      @NonNull TextView placeJeonnam, @NonNull TextView placeSejong, @NonNull TextView placeSeoul,
      @NonNull TextView placeStudyCafe, @NonNull TextView placeStudyRoom,
      @NonNull TextView placeUlsan, @NonNull LinearLayout specificPlaceSection,
      @NonNull TextView title) {
    this.rootView = rootView;
    this.ampmSpinner = ampmSpinner;
    this.confirmButton = confirmButton;
    this.daySpinner = daySpinner;
    this.gridLayout = gridLayout;
    this.hourSpinner = hourSpinner;
    this.meetWhere = meetWhere;
    this.meetingPlaceSection = meetingPlaceSection;
    this.meetingTimeSection = meetingTimeSection;
    this.monthSpinner = monthSpinner;
    this.placeBusan = placeBusan;
    this.placeCafemaru = placeCafemaru;
    this.placeChungbuk = placeChungbuk;
    this.placeChungnam = placeChungnam;
    this.placeDaegu = placeDaegu;
    this.placeDaejeon = placeDaejeon;
    this.placeGangwon = placeGangwon;
    this.placeGwangju = placeGwangju;
    this.placeGyeongbuk = placeGyeongbuk;
    this.placeGyeonggi = placeGyeonggi;
    this.placeGyeongnam = placeGyeongnam;
    this.placeIncheon = placeIncheon;
    this.placeJeju = placeJeju;
    this.placeJeonbuk = placeJeonbuk;
    this.placeJeonnam = placeJeonnam;
    this.placeSejong = placeSejong;
    this.placeSeoul = placeSeoul;
    this.placeStudyCafe = placeStudyCafe;
    this.placeStudyRoom = placeStudyRoom;
    this.placeUlsan = placeUlsan;
    this.specificPlaceSection = specificPlaceSection;
    this.title = title;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityBookAppointBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityBookAppointBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_book_appoint, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityBookAppointBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ampm_spinner;
      Spinner ampmSpinner = ViewBindings.findChildViewById(rootView, id);
      if (ampmSpinner == null) {
        break missingId;
      }

      id = R.id.confirm_button;
      Button confirmButton = ViewBindings.findChildViewById(rootView, id);
      if (confirmButton == null) {
        break missingId;
      }

      id = R.id.day_spinner;
      Spinner daySpinner = ViewBindings.findChildViewById(rootView, id);
      if (daySpinner == null) {
        break missingId;
      }

      id = R.id.grid_layout;
      GridLayout gridLayout = ViewBindings.findChildViewById(rootView, id);
      if (gridLayout == null) {
        break missingId;
      }

      id = R.id.hour_spinner;
      Spinner hourSpinner = ViewBindings.findChildViewById(rootView, id);
      if (hourSpinner == null) {
        break missingId;
      }

      id = R.id.meet_where;
      TextView meetWhere = ViewBindings.findChildViewById(rootView, id);
      if (meetWhere == null) {
        break missingId;
      }

      id = R.id.meeting_place_section;
      LinearLayout meetingPlaceSection = ViewBindings.findChildViewById(rootView, id);
      if (meetingPlaceSection == null) {
        break missingId;
      }

      id = R.id.meeting_time_section;
      LinearLayout meetingTimeSection = ViewBindings.findChildViewById(rootView, id);
      if (meetingTimeSection == null) {
        break missingId;
      }

      id = R.id.month_spinner;
      Spinner monthSpinner = ViewBindings.findChildViewById(rootView, id);
      if (monthSpinner == null) {
        break missingId;
      }

      id = R.id.place_busan;
      TextView placeBusan = ViewBindings.findChildViewById(rootView, id);
      if (placeBusan == null) {
        break missingId;
      }

      id = R.id.place_cafemaru;
      TextView placeCafemaru = ViewBindings.findChildViewById(rootView, id);
      if (placeCafemaru == null) {
        break missingId;
      }

      id = R.id.place_chungbuk;
      TextView placeChungbuk = ViewBindings.findChildViewById(rootView, id);
      if (placeChungbuk == null) {
        break missingId;
      }

      id = R.id.place_chungnam;
      TextView placeChungnam = ViewBindings.findChildViewById(rootView, id);
      if (placeChungnam == null) {
        break missingId;
      }

      id = R.id.place_daegu;
      TextView placeDaegu = ViewBindings.findChildViewById(rootView, id);
      if (placeDaegu == null) {
        break missingId;
      }

      id = R.id.place_daejeon;
      TextView placeDaejeon = ViewBindings.findChildViewById(rootView, id);
      if (placeDaejeon == null) {
        break missingId;
      }

      id = R.id.place_gangwon;
      TextView placeGangwon = ViewBindings.findChildViewById(rootView, id);
      if (placeGangwon == null) {
        break missingId;
      }

      id = R.id.place_gwangju;
      TextView placeGwangju = ViewBindings.findChildViewById(rootView, id);
      if (placeGwangju == null) {
        break missingId;
      }

      id = R.id.place_gyeongbuk;
      TextView placeGyeongbuk = ViewBindings.findChildViewById(rootView, id);
      if (placeGyeongbuk == null) {
        break missingId;
      }

      id = R.id.place_gyeonggi;
      TextView placeGyeonggi = ViewBindings.findChildViewById(rootView, id);
      if (placeGyeonggi == null) {
        break missingId;
      }

      id = R.id.place_gyeongnam;
      TextView placeGyeongnam = ViewBindings.findChildViewById(rootView, id);
      if (placeGyeongnam == null) {
        break missingId;
      }

      id = R.id.place_incheon;
      TextView placeIncheon = ViewBindings.findChildViewById(rootView, id);
      if (placeIncheon == null) {
        break missingId;
      }

      id = R.id.place_jeju;
      TextView placeJeju = ViewBindings.findChildViewById(rootView, id);
      if (placeJeju == null) {
        break missingId;
      }

      id = R.id.place_jeonbuk;
      TextView placeJeonbuk = ViewBindings.findChildViewById(rootView, id);
      if (placeJeonbuk == null) {
        break missingId;
      }

      id = R.id.place_jeonnam;
      TextView placeJeonnam = ViewBindings.findChildViewById(rootView, id);
      if (placeJeonnam == null) {
        break missingId;
      }

      id = R.id.place_sejong;
      TextView placeSejong = ViewBindings.findChildViewById(rootView, id);
      if (placeSejong == null) {
        break missingId;
      }

      id = R.id.place_seoul;
      TextView placeSeoul = ViewBindings.findChildViewById(rootView, id);
      if (placeSeoul == null) {
        break missingId;
      }

      id = R.id.place_study_cafe;
      TextView placeStudyCafe = ViewBindings.findChildViewById(rootView, id);
      if (placeStudyCafe == null) {
        break missingId;
      }

      id = R.id.place_study_room;
      TextView placeStudyRoom = ViewBindings.findChildViewById(rootView, id);
      if (placeStudyRoom == null) {
        break missingId;
      }

      id = R.id.place_ulsan;
      TextView placeUlsan = ViewBindings.findChildViewById(rootView, id);
      if (placeUlsan == null) {
        break missingId;
      }

      id = R.id.specific_place_section;
      LinearLayout specificPlaceSection = ViewBindings.findChildViewById(rootView, id);
      if (specificPlaceSection == null) {
        break missingId;
      }

      id = R.id.title;
      TextView title = ViewBindings.findChildViewById(rootView, id);
      if (title == null) {
        break missingId;
      }

      return new ActivityBookAppointBinding((RelativeLayout) rootView, ampmSpinner, confirmButton,
          daySpinner, gridLayout, hourSpinner, meetWhere, meetingPlaceSection, meetingTimeSection,
          monthSpinner, placeBusan, placeCafemaru, placeChungbuk, placeChungnam, placeDaegu,
          placeDaejeon, placeGangwon, placeGwangju, placeGyeongbuk, placeGyeonggi, placeGyeongnam,
          placeIncheon, placeJeju, placeJeonbuk, placeJeonnam, placeSejong, placeSeoul,
          placeStudyCafe, placeStudyRoom, placeUlsan, specificPlaceSection, title);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
