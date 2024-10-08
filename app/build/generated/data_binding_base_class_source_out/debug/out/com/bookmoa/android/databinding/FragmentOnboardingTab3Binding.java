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

public final class FragmentOnboardingTab3Binding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView onboardingPhone;

  @NonNull
  public final TextView onboardingTv;

  private FragmentOnboardingTab3Binding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView onboardingPhone, @NonNull TextView onboardingTv) {
    this.rootView = rootView;
    this.onboardingPhone = onboardingPhone;
    this.onboardingTv = onboardingTv;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentOnboardingTab3Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentOnboardingTab3Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_onboarding_tab3, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentOnboardingTab3Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.onboarding_phone;
      ImageView onboardingPhone = ViewBindings.findChildViewById(rootView, id);
      if (onboardingPhone == null) {
        break missingId;
      }

      id = R.id.onboarding_tv;
      TextView onboardingTv = ViewBindings.findChildViewById(rootView, id);
      if (onboardingTv == null) {
        break missingId;
      }

      return new FragmentOnboardingTab3Binding((ConstraintLayout) rootView, onboardingPhone,
          onboardingTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
