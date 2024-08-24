// Generated by view binder compiler. Do not edit!
package com.bookmoa.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.bookmoa.android.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMypageBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView FAQBtn;

  @NonNull
  public final TextView bookGroupNameTv;

  @NonNull
  public final TextView changeProfileBtn;

  @NonNull
  public final View guideline;

  @NonNull
  public final TextView inquiryBtn;

  @NonNull
  public final TextView introMoaBtn;

  @NonNull
  public final TextView logoutBtn;

  @NonNull
  public final TextView mypageTv;

  @NonNull
  public final TextView nicknameTv;

  @NonNull
  public final TextView noticeSettingBtn;

  @NonNull
  public final TextView privacyPolicyBtn;

  @NonNull
  public final ImageView profileIv;

  @NonNull
  public final View questionGuideline;

  @NonNull
  public final TextView ratingBtn;

  @NonNull
  public final TextView serviceTermsBtn;

  @NonNull
  public final View settingGuideline;

  @NonNull
  public final ImageView topBarBackIc;

  @NonNull
  public final View topbarGuideline;

  private FragmentMypageBinding(@NonNull ConstraintLayout rootView, @NonNull TextView FAQBtn,
      @NonNull TextView bookGroupNameTv, @NonNull TextView changeProfileBtn,
      @NonNull View guideline, @NonNull TextView inquiryBtn, @NonNull TextView introMoaBtn,
      @NonNull TextView logoutBtn, @NonNull TextView mypageTv, @NonNull TextView nicknameTv,
      @NonNull TextView noticeSettingBtn, @NonNull TextView privacyPolicyBtn,
      @NonNull ImageView profileIv, @NonNull View questionGuideline, @NonNull TextView ratingBtn,
      @NonNull TextView serviceTermsBtn, @NonNull View settingGuideline,
      @NonNull ImageView topBarBackIc, @NonNull View topbarGuideline) {
    this.rootView = rootView;
    this.FAQBtn = FAQBtn;
    this.bookGroupNameTv = bookGroupNameTv;
    this.changeProfileBtn = changeProfileBtn;
    this.guideline = guideline;
    this.inquiryBtn = inquiryBtn;
    this.introMoaBtn = introMoaBtn;
    this.logoutBtn = logoutBtn;
    this.mypageTv = mypageTv;
    this.nicknameTv = nicknameTv;
    this.noticeSettingBtn = noticeSettingBtn;
    this.privacyPolicyBtn = privacyPolicyBtn;
    this.profileIv = profileIv;
    this.questionGuideline = questionGuideline;
    this.ratingBtn = ratingBtn;
    this.serviceTermsBtn = serviceTermsBtn;
    this.settingGuideline = settingGuideline;
    this.topBarBackIc = topBarBackIc;
    this.topbarGuideline = topbarGuideline;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMypageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMypageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_mypage, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMypageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.FAQ_btn;
      TextView FAQBtn = ViewBindings.findChildViewById(rootView, id);
      if (FAQBtn == null) {
        break missingId;
      }

      id = R.id.bookGroupName_tv;
      TextView bookGroupNameTv = ViewBindings.findChildViewById(rootView, id);
      if (bookGroupNameTv == null) {
        break missingId;
      }

      id = R.id.change_profile_btn;
      TextView changeProfileBtn = ViewBindings.findChildViewById(rootView, id);
      if (changeProfileBtn == null) {
        break missingId;
      }

      id = R.id.guideline;
      View guideline = ViewBindings.findChildViewById(rootView, id);
      if (guideline == null) {
        break missingId;
      }

      id = R.id.inquiry_btn;
      TextView inquiryBtn = ViewBindings.findChildViewById(rootView, id);
      if (inquiryBtn == null) {
        break missingId;
      }

      id = R.id.intro_moa_btn;
      TextView introMoaBtn = ViewBindings.findChildViewById(rootView, id);
      if (introMoaBtn == null) {
        break missingId;
      }

      id = R.id.logout_btn;
      TextView logoutBtn = ViewBindings.findChildViewById(rootView, id);
      if (logoutBtn == null) {
        break missingId;
      }

      id = R.id.mypage_tv;
      TextView mypageTv = ViewBindings.findChildViewById(rootView, id);
      if (mypageTv == null) {
        break missingId;
      }

      id = R.id.nickname_tv;
      TextView nicknameTv = ViewBindings.findChildViewById(rootView, id);
      if (nicknameTv == null) {
        break missingId;
      }

      id = R.id.notice_setting_btn;
      TextView noticeSettingBtn = ViewBindings.findChildViewById(rootView, id);
      if (noticeSettingBtn == null) {
        break missingId;
      }

      id = R.id.privacyPolicy_btn;
      TextView privacyPolicyBtn = ViewBindings.findChildViewById(rootView, id);
      if (privacyPolicyBtn == null) {
        break missingId;
      }

      id = R.id.profile_iv;
      ImageView profileIv = ViewBindings.findChildViewById(rootView, id);
      if (profileIv == null) {
        break missingId;
      }

      id = R.id.question_guideline;
      View questionGuideline = ViewBindings.findChildViewById(rootView, id);
      if (questionGuideline == null) {
        break missingId;
      }

      id = R.id.rating_btn;
      TextView ratingBtn = ViewBindings.findChildViewById(rootView, id);
      if (ratingBtn == null) {
        break missingId;
      }

      id = R.id.serviceTerms_btn;
      TextView serviceTermsBtn = ViewBindings.findChildViewById(rootView, id);
      if (serviceTermsBtn == null) {
        break missingId;
      }

      id = R.id.setting_guideline;
      View settingGuideline = ViewBindings.findChildViewById(rootView, id);
      if (settingGuideline == null) {
        break missingId;
      }

      id = R.id.top_bar_back_ic;
      ImageView topBarBackIc = ViewBindings.findChildViewById(rootView, id);
      if (topBarBackIc == null) {
        break missingId;
      }

      id = R.id.topbar_guideline;
      View topbarGuideline = ViewBindings.findChildViewById(rootView, id);
      if (topbarGuideline == null) {
        break missingId;
      }

      return new FragmentMypageBinding((ConstraintLayout) rootView, FAQBtn, bookGroupNameTv,
          changeProfileBtn, guideline, inquiryBtn, introMoaBtn, logoutBtn, mypageTv, nicknameTv,
          noticeSettingBtn, privacyPolicyBtn, profileIv, questionGuideline, ratingBtn,
          serviceTermsBtn, settingGuideline, topBarBackIc, topbarGuideline);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
