package org.intellij.erlang.inspection;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.util.PsiTreeUtil;
import org.intellij.erlang.psi.*;
import org.jetbrains.annotations.NotNull;

public class ErlangMapInPatternInspection extends ErlangInspectionBase {
  @NotNull
  @Override
  protected ErlangVisitor buildErlangVisitor(@NotNull ProblemsHolder holder,
                                             @NotNull LocalInspectionToolSession session) {
    return new ErlangVisitor() {
      @Override
      public void visitMapExpression(@NotNull ErlangMapExpression o) {
        var pattern = PsiTreeUtil.getParentOfType(o, ErlangArgumentDefinition.class);
        if (pattern != null) {
          registerProblem(holder, o, "map in pattern", null, ProblemHighlightType.WEAK_WARNING);
        }
      }
    };
  }
}
