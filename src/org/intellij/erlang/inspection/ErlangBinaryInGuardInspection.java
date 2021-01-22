package org.intellij.erlang.inspection;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.util.PsiTreeUtil;
import org.intellij.erlang.psi.ErlangBinaryExpression;
import org.intellij.erlang.psi.ErlangClauseGuard;
import org.intellij.erlang.psi.ErlangVisitor;
import org.jetbrains.annotations.NotNull;

public class ErlangBinaryInGuardInspection extends ErlangInspectionBase {
  @NotNull
  @Override
  protected ErlangVisitor buildErlangVisitor(@NotNull ProblemsHolder holder,
                                             @NotNull LocalInspectionToolSession session) {
    return new ErlangVisitor() {
      @Override
      public void visitBinaryExpression(@NotNull ErlangBinaryExpression o) {
        var guard = PsiTreeUtil.getParentOfType(o, ErlangClauseGuard.class);
        if (guard != null) {
          registerProblem(holder, o, "binary in guard", null, ProblemHighlightType.WEAK_WARNING);
        }
      }
    };
  }
}
