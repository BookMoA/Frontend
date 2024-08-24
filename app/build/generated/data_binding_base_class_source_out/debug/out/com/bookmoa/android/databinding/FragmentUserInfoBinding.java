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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.bookmoa.android.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentUserInfoBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView answerTv4;

  @NonNull
  public final TextView answerTv41;

  @NonNull
  public final ImageView arrowIcon4;

  @NonNull
  public final View question4Guideline;

  @NonNull
  public final View question4Guideline1;

  @NonNull
  public final ConstraintLayout questionLayout4;

  @NonNull
  public final TextView questionTv4;

  @NonNull
  public final TextView quitCommentTv;

  @NonNull
  public final LinearLayout userQuitErrorGuideIc;

  @NonNull
  public final TextView userQuitErrorGuideTv;

  @NonNull
  public final LinearLayout userQuitGuideIc;

  @NonNull
  public final TextView userQuitGuideTv;

  private FragmentUserInfoBinding(@NonNull ConstraintLayout rootView, @NonNull TextView answerTv4,
      @NonNull TextView answerTv41, @NonNull ImageView arrowIcon4, @NonNull View question4Guideline,
      @NonNull View question4Guideline1, @NonNull ConstraintLayout questionLayout4,
      @NonNull TextView questionTv4, @NonNull TextView quitCommentTv,
      @NonNull LinearLayout userQuitErrorGuideIc, @NonNull TextView userQuitErrorGuideTv,
      @NonNull LinearLayout userQuitGuideIc, @NonNull TextView userQuitGuideTv) {
    this.rootView = rootView;
    this.answerTv4 = answerTv4;
    this.answerTv41 = answerTv41;
    this.arrowIcon4 = arrowIcon4;
    this.question4Guideline = question4Guideline;
    this.question4Guideline1 = question4Guideline1;
    this.questionLayout4 = questionLayout4;
    this.questionTv4 = questionTv4;
    this.quitCommentTv = quitCommentTv;
    this.userQuitErrorGuideIc = userQuitErrorGuideIc;
    this.userQuitErrorGuideTv = userQuitErrorGuideTv;
    this.userQuitGuideIc = userQuitGuideIc;
    this.userQuitGuideTv = userQuitGuideTv;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentUserInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentUserInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_user_info, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentUserInfoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.answer_tv4;
      TextView answerTv4 = ViewBindings.findChildViewById(rootView, id);
      if (answerTv4 == null) {
        break missingId;
      }

      id = R.id.answer_tv4_1;
      TextView answerTv41 = ViewBindings.findChildViewById(rootView, id);
      if (answerTv41 == null) {
        break missingId;
      }

      id = R.id.arrow_icon4;
      ImageView arrowIcon4 = ViewBindings.findChildViewById(rootView, id);
      if (arrowIcon4 == null) {
        break missingId;
      }

      id = R.id.question_4_guideline;
      View question4Guideline = ViewBindings.findChildViewById(rootView, id);
      if (question4Guideline == null) {
        break missingId;
      }

      id = R.id.question_4_guideline_1;
      View question4Guideline1 = ViewBindings.findChildViewById(rootView, id);
      if (question4Guideline1 == null) {
        break missingId;
      }

      id = R.id.question_layout4;
      ConstraintLayout questionLayout4 = ViewBindings.findChildViewById(rootView, id);
      if (questionLayout4 == null) {
        break missingId;
      }

      id = R.id.question_tv4;
      TextView questionTv4 = ViewBindings.findChildViewById(rootView, id);
      if (questionTv4 == null) {
        break missingId;
      }

      id = R.id.quit_comment_tv;
      TextView quitCommentTv = ViewBindings.findChildViewById(rootView, id);
      if (quitCommentTv == null) {
        break missingId;
      }

      id = R.id.user_quit_error_guide_ic;
      LinearLayout userQuitErrorGuideIc = ViewBindings.findChildViewById(rootView, id);
      if (userQuitErrorGuideIc == null) {
        break missingId;
      }

      id = R.id.user_quit_error_guide_tv;
      TextView userQuitErrorGuideTv = ViewBindings.findChildViewById(rootView, id);
      if (userQuitErrorGuideTv == null) {
        break missingId;
      }

      id = R.id.user_quit_guide_ic;
      LinearLayout userQuitGuideIc = ViewBindings.findChildViewById(rootView, id);
      if (userQuitGuideIc == null) {
        break missingId;
      }

      id = R.id.user_quit_guide_tv;
      TextView userQuitGuideTv = ViewBindings.findChildViewById(rootView, id);
      if (userQuitGuideTv == null) {
        break missingId;
      }

      return new FragmentUserInfoBinding((ConstraintLayout) rootView, answerTv4, answerTv41,
          arrowIcon4, question4Guideline, question4Guideline1, questionLayout4, questionTv4,
          quitCommentTv, userQuitErrorGuideIc, userQuitErrorGuideTv, userQuitGuideIc,
          userQuitGuideTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
