// Generated by view binder compiler. Do not edit!
package com.bookmoa.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.bookmoa.android.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentGrouprvBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView grouprvCountTv;

  @NonNull
  public final TextView grouprvDescriptionTv;

  @NonNull
  public final TextView grouprvTitleTv;

  private FragmentGrouprvBinding(@NonNull CardView rootView, @NonNull TextView grouprvCountTv,
      @NonNull TextView grouprvDescriptionTv, @NonNull TextView grouprvTitleTv) {
    this.rootView = rootView;
    this.grouprvCountTv = grouprvCountTv;
    this.grouprvDescriptionTv = grouprvDescriptionTv;
    this.grouprvTitleTv = grouprvTitleTv;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentGrouprvBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentGrouprvBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_grouprv, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentGrouprvBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.grouprv_count_tv;
      TextView grouprvCountTv = ViewBindings.findChildViewById(rootView, id);
      if (grouprvCountTv == null) {
        break missingId;
      }

      id = R.id.grouprv_description_tv;
      TextView grouprvDescriptionTv = ViewBindings.findChildViewById(rootView, id);
      if (grouprvDescriptionTv == null) {
        break missingId;
      }

      id = R.id.grouprv_title_tv;
      TextView grouprvTitleTv = ViewBindings.findChildViewById(rootView, id);
      if (grouprvTitleTv == null) {
        break missingId;
      }

      return new FragmentGrouprvBinding((CardView) rootView, grouprvCountTv, grouprvDescriptionTv,
          grouprvTitleTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
