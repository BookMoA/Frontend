// Generated by view binder compiler. Do not edit!
package com.bookmoa.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.bookmoa.android.R;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityFaqBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TabLayout FAQTl;

  @NonNull
  public final TextView FAQTv;

  @NonNull
  public final ImageView backIc;

  @NonNull
  public final FrameLayout tabContent;

  @NonNull
  public final View topbarGuideline;

  private ActivityFaqBinding(@NonNull ConstraintLayout rootView, @NonNull TabLayout FAQTl,
      @NonNull TextView FAQTv, @NonNull ImageView backIc, @NonNull FrameLayout tabContent,
      @NonNull View topbarGuideline) {
    this.rootView = rootView;
    this.FAQTl = FAQTl;
    this.FAQTv = FAQTv;
    this.backIc = backIc;
    this.tabContent = tabContent;
    this.topbarGuideline = topbarGuideline;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityFaqBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityFaqBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_faq, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityFaqBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.FAQ_tl;
      TabLayout FAQTl = ViewBindings.findChildViewById(rootView, id);
      if (FAQTl == null) {
        break missingId;
      }

      id = R.id.FAQ_tv;
      TextView FAQTv = ViewBindings.findChildViewById(rootView, id);
      if (FAQTv == null) {
        break missingId;
      }

      id = R.id.back_ic;
      ImageView backIc = ViewBindings.findChildViewById(rootView, id);
      if (backIc == null) {
        break missingId;
      }

      id = R.id.tab_content;
      FrameLayout tabContent = ViewBindings.findChildViewById(rootView, id);
      if (tabContent == null) {
        break missingId;
      }

      id = R.id.topbar_guideline;
      View topbarGuideline = ViewBindings.findChildViewById(rootView, id);
      if (topbarGuideline == null) {
        break missingId;
      }

      return new ActivityFaqBinding((ConstraintLayout) rootView, FAQTl, FAQTv, backIc, tabContent,
          topbarGuideline);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
