// Generated by view binder compiler. Do not edit!
package com.bookmoa.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.bookmoa.android.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSearchTitleBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout searchTitleAvailable;

  @NonNull
  public final ConstraintLayout searchTitleNotAvailable;

  @NonNull
  public final RecyclerView searchTitleRv;

  @NonNull
  public final TextView searchTitleTv;

  private FragmentSearchTitleBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout searchTitleAvailable,
      @NonNull ConstraintLayout searchTitleNotAvailable, @NonNull RecyclerView searchTitleRv,
      @NonNull TextView searchTitleTv) {
    this.rootView = rootView;
    this.searchTitleAvailable = searchTitleAvailable;
    this.searchTitleNotAvailable = searchTitleNotAvailable;
    this.searchTitleRv = searchTitleRv;
    this.searchTitleTv = searchTitleTv;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSearchTitleBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSearchTitleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_search_title, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSearchTitleBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.search_title_available;
      ConstraintLayout searchTitleAvailable = ViewBindings.findChildViewById(rootView, id);
      if (searchTitleAvailable == null) {
        break missingId;
      }

      id = R.id.search_title_not_available;
      ConstraintLayout searchTitleNotAvailable = ViewBindings.findChildViewById(rootView, id);
      if (searchTitleNotAvailable == null) {
        break missingId;
      }

      id = R.id.search_title_rv;
      RecyclerView searchTitleRv = ViewBindings.findChildViewById(rootView, id);
      if (searchTitleRv == null) {
        break missingId;
      }

      id = R.id.search_title_tv;
      TextView searchTitleTv = ViewBindings.findChildViewById(rootView, id);
      if (searchTitleTv == null) {
        break missingId;
      }

      return new FragmentSearchTitleBinding((ConstraintLayout) rootView, searchTitleAvailable,
          searchTitleNotAvailable, searchTitleRv, searchTitleTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
