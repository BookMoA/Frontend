// Generated by view binder compiler. Do not edit!
package com.bookmoa.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public final class FragmentInsearchauthorrvBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView insearchauthorDateTv;

  @NonNull
  public final TextView insearchauthorDescriptionTv;

  @NonNull
  public final ImageView insearchauthorImageIv;

  @NonNull
  public final TextView insearchauthorNameTv;

  @NonNull
  public final TextView insearchauthorTitleTv;

  private FragmentInsearchauthorrvBinding(@NonNull CardView rootView,
      @NonNull TextView insearchauthorDateTv, @NonNull TextView insearchauthorDescriptionTv,
      @NonNull ImageView insearchauthorImageIv, @NonNull TextView insearchauthorNameTv,
      @NonNull TextView insearchauthorTitleTv) {
    this.rootView = rootView;
    this.insearchauthorDateTv = insearchauthorDateTv;
    this.insearchauthorDescriptionTv = insearchauthorDescriptionTv;
    this.insearchauthorImageIv = insearchauthorImageIv;
    this.insearchauthorNameTv = insearchauthorNameTv;
    this.insearchauthorTitleTv = insearchauthorTitleTv;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentInsearchauthorrvBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentInsearchauthorrvBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_insearchauthorrv, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentInsearchauthorrvBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.insearchauthor_date_tv;
      TextView insearchauthorDateTv = ViewBindings.findChildViewById(rootView, id);
      if (insearchauthorDateTv == null) {
        break missingId;
      }

      id = R.id.insearchauthor_description_tv;
      TextView insearchauthorDescriptionTv = ViewBindings.findChildViewById(rootView, id);
      if (insearchauthorDescriptionTv == null) {
        break missingId;
      }

      id = R.id.insearchauthor_image_iv;
      ImageView insearchauthorImageIv = ViewBindings.findChildViewById(rootView, id);
      if (insearchauthorImageIv == null) {
        break missingId;
      }

      id = R.id.insearchauthor_name_tv;
      TextView insearchauthorNameTv = ViewBindings.findChildViewById(rootView, id);
      if (insearchauthorNameTv == null) {
        break missingId;
      }

      id = R.id.insearchauthor_title_tv;
      TextView insearchauthorTitleTv = ViewBindings.findChildViewById(rootView, id);
      if (insearchauthorTitleTv == null) {
        break missingId;
      }

      return new FragmentInsearchauthorrvBinding((CardView) rootView, insearchauthorDateTv,
          insearchauthorDescriptionTv, insearchauthorImageIv, insearchauthorNameTv,
          insearchauthorTitleTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
