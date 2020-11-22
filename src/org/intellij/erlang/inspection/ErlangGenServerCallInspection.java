package org.intellij.erlang.inspection;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import org.intellij.erlang.psi.*;
import org.jetbrains.annotations.NotNull;

public class ErlangGenServerCallInspection extends ErlangInspectionBase {
  @NotNull
  @Override
  protected ErlangVisitor buildErlangVisitor(@NotNull final ProblemsHolder holder, @NotNull LocalInspectionToolSession session) {
    return new ErlangVisitor() {
      @Override
      public void visitGlobalFunctionCallExpression(@NotNull ErlangGlobalFunctionCallExpression o) {
        ErlangModuleRef moduleRef = o.getModuleRef();
        ErlangAtom module = moduleRef.getQAtom().getAtom();
        ErlangFunctionCallExpression functionCallExpression = o.getFunctionCallExpression();

        if (module == null) return;
        if (!module.getName().equals("gen_server")) return;
        if (!functionCallExpression.getName().equals("call")) return;

        registerProblem(holder, o, "Gen Server: " + "'" + o.getText() + "'", null, ProblemHighlightType.WEAK_WARNING);
      }
    };
  }
}
