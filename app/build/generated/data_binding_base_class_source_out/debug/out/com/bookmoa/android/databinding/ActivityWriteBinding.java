// Generated by view binder compiler. Do not edit!
package com.bookmoa.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ActivityWriteBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView writeCloseIv;

  @NonNull
  public final TextView writeDoneTv;

  private ActivityWriteBinding(@NonNull LinearLayout rootView, @NonNull ImageView writeCloseIv,
      @NonNull TextView writeDoneTv) {
    this.rootView = rootView;
    this.writeCloseIv = writeCloseIv;
    this.writeDoneTv = writeDoneTv;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityWriteBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityWriteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_write, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityWriteBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.write_close_iv;
      ImageView writeCloseIv = ViewBindings.findChildViewById(rootView, id);
      if (writeCloseIv == null) {
        break missingId;
      }

      id = R.id.write_done_tv;
      TextView writeDoneTv = ViewBindings.findChildViewById(rootView, id);
      if (writeDoneTv == null) {
        break missingId;
      }

      return new ActivityWriteBinding((LinearLayout) rootView, writeCloseIv, writeDoneTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
