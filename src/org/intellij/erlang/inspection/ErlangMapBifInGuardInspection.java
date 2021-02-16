package org.intellij.erlang.inspection;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.util.PsiTreeUtil;
import org.intellij.erlang.psi.ErlangClauseGuard;
import org.intellij.erlang.psi.ErlangFunctionCallExpression;
import org.intellij.erlang.psi.ErlangMapExpression;
import org.intellij.erlang.psi.ErlangVisitor;
import org.jetbrains.annotations.NotNull;

public class ErlangMapBifInGuardInspection extends ErlangInspectionBase {
  @NotNull
  @Override
  protected ErlangVisitor buildErlangVisitor(@NotNull ProblemsHolder holder,
                                             @NotNull LocalInspectionToolSession session) {
    return new ErlangVisitor() {
      @Override
      public void visitFunctionCallExpression(@NotNull ErlangFunctionCallExpression o) {
        var guard = PsiTreeUtil.getParentOfType(o, ErlangClauseGuard.class);
        if (guard != null && (o.getName().equals("map_get") || o.getName().equals("map_size"))) {
          registerProblem(holder, o, "map bif in guard", null, ProblemHighlightType.WEAK_WARNING);
        }
      }
    };
  }
}
