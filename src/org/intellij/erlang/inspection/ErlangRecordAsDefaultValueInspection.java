package org.intellij.erlang.inspection;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.util.PsiTreeUtil;
import org.intellij.erlang.psi.*;
import org.jetbrains.annotations.NotNull;

public class ErlangRecordAsDefaultValueInspection extends ErlangInspectionBase {
  @NotNull
  @Override
  protected ErlangVisitor buildErlangVisitor(@NotNull ProblemsHolder holder,
                                             @NotNull LocalInspectionToolSession session) {
    return new ErlangVisitor() {
      @Override
      public void visitRecordExpression(@NotNull ErlangRecordExpression o) {
        var recDef = PsiTreeUtil.getParentOfType(o, ErlangRecordDefinition.class);
        if (recDef != null) {
          registerProblem(holder, o, "record in record def", null, ProblemHighlightType.WEAK_WARNING);
        }
      }
    };
  }
}
