package org.intellij.erlang.inspection;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import org.intellij.erlang.psi.ErlangOptBitTypeList;
import org.intellij.erlang.psi.ErlangVisitor;
import org.jetbrains.annotations.NotNull;

public class ErlangComplexBitSpecifiersInspection extends ErlangInspectionBase {
  @NotNull
  @Override
  protected ErlangVisitor buildErlangVisitor(@NotNull ProblemsHolder holder,
                                             @NotNull LocalInspectionToolSession session) {
    return new ErlangVisitor() {
      @Override
      public void visitOptBitTypeList(@NotNull ErlangOptBitTypeList o) {
        if (o.getBitTypeList().size() > 1) {
          registerProblem(holder, o, "complex bit type specifier", null, ProblemHighlightType.WEAK_WARNING);
        }
      }
    };
  }
}
