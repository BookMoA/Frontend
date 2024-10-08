// Generated by view binder compiler. Do not edit!
package com.bookmoa.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

public final class FragmentPasswordBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView passwordBackIv;

  @NonNull
  public final TextView passwordErrorTv;

  @NonNull
  public final EditText passwordEt1;

  @NonNull
  public final EditText passwordEt2;

  @NonNull
  public final EditText passwordEt3;

  @NonNull
  public final EditText passwordEt4;

  @NonNull
  public final EditText passwordEt5;

  @NonNull
  public final EditText passwordEt6;

  private FragmentPasswordBinding(@NonNull LinearLayout rootView, @NonNull ImageView passwordBackIv,
      @NonNull TextView passwordErrorTv, @NonNull EditText passwordEt1,
      @NonNull EditText passwordEt2, @NonNull EditText passwordEt3, @NonNull EditText passwordEt4,
      @NonNull EditText passwordEt5, @NonNull EditText passwordEt6) {
    this.rootView = rootView;
    this.passwordBackIv = passwordBackIv;
    this.passwordErrorTv = passwordErrorTv;
    this.passwordEt1 = passwordEt1;
    this.passwordEt2 = passwordEt2;
    this.passwordEt3 = passwordEt3;
    this.passwordEt4 = passwordEt4;
    this.passwordEt5 = passwordEt5;
    this.passwordEt6 = passwordEt6;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPasswordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_password, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPasswordBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.password_back_iv;
      ImageView passwordBackIv = ViewBindings.findChildViewById(rootView, id);
      if (passwordBackIv == null) {
        break missingId;
      }

      id = R.id.password_error_tv;
      TextView passwordErrorTv = ViewBindings.findChildViewById(rootView, id);
      if (passwordErrorTv == null) {
        break missingId;
      }

      id = R.id.password_et1;
      EditText passwordEt1 = ViewBindings.findChildViewById(rootView, id);
      if (passwordEt1 == null) {
        break missingId;
      }

      id = R.id.password_et2;
      EditText passwordEt2 = ViewBindings.findChildViewById(rootView, id);
      if (passwordEt2 == null) {
        break missingId;
      }

      id = R.id.password_et3;
      EditText passwordEt3 = ViewBindings.findChildViewById(rootView, id);
      if (passwordEt3 == null) {
        break missingId;
      }

      id = R.id.password_et4;
      EditText passwordEt4 = ViewBindings.findChildViewById(rootView, id);
      if (passwordEt4 == null) {
        break missingId;
      }

      id = R.id.password_et5;
      EditText passwordEt5 = ViewBindings.findChildViewById(rootView, id);
      if (passwordEt5 == null) {
        break missingId;
      }

      id = R.id.password_et6;
      EditText passwordEt6 = ViewBindings.findChildViewById(rootView, id);
      if (passwordEt6 == null) {
        break missingId;
      }

      return new FragmentPasswordBinding((LinearLayout) rootView, passwordBackIv, passwordErrorTv,
          passwordEt1, passwordEt2, passwordEt3, passwordEt4, passwordEt5, passwordEt6);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
