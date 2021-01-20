package org.intellij.erlang.inspection;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.util.PsiTreeUtil;
import org.intellij.erlang.psi.ErlangClauseGuard;
import org.intellij.erlang.psi.ErlangOrelseExpression;
import org.intellij.erlang.psi.ErlangVisitor;
import org.jetbrains.annotations.NotNull;

public class ErlangOrElseInGuardInspection extends ErlangInspectionBase {
  @NotNull
  @Override
  protected ErlangVisitor buildErlangVisitor(@NotNull ProblemsHolder holder,
                                             @NotNull LocalInspectionToolSession session) {
    return new ErlangVisitor() {
      @Override
      public void visitOrelseExpression(@NotNull ErlangOrelseExpression o) {
        var guard = PsiTreeUtil.getParentOfType(o, ErlangClauseGuard.class);
        if (guard != null) {
          registerProblem(holder, o, "orelse in guard", null, ProblemHighlightType.WEAK_WARNING);
        }
      }
    };
  }
}
