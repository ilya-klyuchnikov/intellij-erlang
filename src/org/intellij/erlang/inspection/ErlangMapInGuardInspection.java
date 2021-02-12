package org.intellij.erlang.inspection;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.util.PsiTreeUtil;
import org.intellij.erlang.psi.*;
import org.jetbrains.annotations.NotNull;

public class ErlangMapInGuardInspection extends ErlangInspectionBase {
  @NotNull
  @Override
  protected ErlangVisitor buildErlangVisitor(@NotNull ProblemsHolder holder,
                                             @NotNull LocalInspectionToolSession session) {
    return new ErlangVisitor() {
      @Override
      public void visitMapExpression(@NotNull ErlangMapExpression o) {
        var guard = PsiTreeUtil.getParentOfType(o, ErlangClauseGuard.class);
        if (guard != null) {
          registerProblem(holder, o, "map in guard", null, ProblemHighlightType.WEAK_WARNING);
        }
      }

      @Override
      public void visitFunctionCallExpression(@NotNull ErlangFunctionCallExpression o) {
        var guard = PsiTreeUtil.getParentOfType(o, ErlangClauseGuard.class);
        if (guard != null && (o.getName().equals("map_get") || o.getName().equals("map_size") || o.getName().equals("is_map"))) {
          registerProblem(holder, o, "map in guard", null, ProblemHighlightType.WEAK_WARNING);
        }
      }
    };
  }
}
