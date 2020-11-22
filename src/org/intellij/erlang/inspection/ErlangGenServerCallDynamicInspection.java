package org.intellij.erlang.inspection;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import org.intellij.erlang.psi.*;
import org.jetbrains.annotations.NotNull;

public class ErlangGenServerCallDynamicInspection extends ErlangInspectionBase {
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

        ErlangArgumentList arguments = functionCallExpression.getArgumentList();
        ErlangExpression message = arguments.getExpressionList().get(1);

        var isAtom = false;
        if (message instanceof ErlangMaxExpression) {
          ErlangMaxExpression maxExpr = (ErlangMaxExpression) message;
          isAtom = maxExpr.getQAtom() != null;
        }

        var isTaggedTuple = false;
        if (message instanceof ErlangTupleExpression) {
          ErlangTupleExpression tupleExpr = (ErlangTupleExpression) message;
          var arity = tupleExpr.getExpressionList().size();
          if (arity >= 1) {
            var head = tupleExpr.getExpressionList().get(0);
            if (head instanceof ErlangMaxExpression) {
              ErlangMaxExpression headMaxExpr = (ErlangMaxExpression) head;
              isTaggedTuple = headMaxExpr.getQAtom() != null;
            }
          }
        }

        var isRecord = message instanceof ErlangRecordExpression;

        var tagged = isAtom || isTaggedTuple || isRecord;

        if (!tagged) {
          registerProblem(holder, o, "Dynamic Gen Server call: " + "'" + o.getText() + "'", null, ProblemHighlightType.WEAK_WARNING);
        }
      }
    };
  }
}
