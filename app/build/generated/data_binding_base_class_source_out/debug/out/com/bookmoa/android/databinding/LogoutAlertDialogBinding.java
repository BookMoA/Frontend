// Generated by view binder compiler. Do not edit!
package com.bookmoa.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.bookmoa.android.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class LogoutAlertDialogBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final View alertGuideline;

  @NonNull
  public final Button cancelBtn;

  @NonNull
  public final Button confirmBtn;

  @NonNull
  public final TextView logoutMessage;

  private LogoutAlertDialogBinding(@NonNull LinearLayout rootView, @NonNull View alertGuideline,
      @NonNull Button cancelBtn, @NonNull Button confirmBtn, @NonNull TextView logoutMessage) {
    this.rootView = rootView;
    this.alertGuideline = alertGuideline;
    this.cancelBtn = cancelBtn;
    this.confirmBtn = confirmBtn;
    this.logoutMessage = logoutMessage;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static LogoutAlertDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static LogoutAlertDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.logout_alert_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static LogoutAlertDialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.alert_guideline;
      View alertGuideline = ViewBindings.findChildViewById(rootView, id);
      if (alertGuideline == null) {
        break missingId;
      }

      id = R.id.cancel_btn;
      Button cancelBtn = ViewBindings.findChildViewById(rootView, id);
      if (cancelBtn == null) {
        break missingId;
      }

      id = R.id.confirm_btn;
      Button confirmBtn = ViewBindings.findChildViewById(rootView, id);
      if (confirmBtn == null) {
        break missingId;
      }

      id = R.id.logout_message;
      TextView logoutMessage = ViewBindings.findChildViewById(rootView, id);
      if (logoutMessage == null) {
        break missingId;
      }

      return new LogoutAlertDialogBinding((LinearLayout) rootView, alertGuideline, cancelBtn,
          confirmBtn, logoutMessage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
