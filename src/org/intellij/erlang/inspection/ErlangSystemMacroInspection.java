package org.intellij.erlang.inspection;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import org.intellij.erlang.psi.ErlangMacros;
import org.intellij.erlang.psi.ErlangMacrosName;
import org.intellij.erlang.psi.ErlangVisitor;
import org.intellij.erlang.psi.impl.ErlangPsiImplUtil;
import org.jetbrains.annotations.NotNull;

public class ErlangSystemMacroInspection extends ErlangInspectionBase {
  @NotNull
  @Override
  protected ErlangVisitor buildErlangVisitor(@NotNull final ProblemsHolder holder, @NotNull LocalInspectionToolSession session) {
    return new ErlangVisitor() {
      @Override
      public void visitMacros(@NotNull ErlangMacros o) {
        ErlangMacrosName macrosName = o.getMacrosName();
        if (ErlangPsiImplUtil.KNOWN_MACROS.contains(macrosName.getText())) {
          registerProblem(holder, o, "System macro: " + "'" + o.getText() + "'", null, ProblemHighlightType.WEAK_WARNING);
        }
      }
    };
  }
}
