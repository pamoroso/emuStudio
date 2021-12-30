package net.emustudio.plugins.compiler.as8080.ast.pseudo;

import net.emustudio.plugins.compiler.as8080.Either;
import net.emustudio.plugins.compiler.as8080.ast.*;
import net.emustudio.plugins.compiler.as8080.ast.expr.ExprNumber;
import org.antlr.v4.runtime.Token;

import java.util.Objects;

import static net.emustudio.plugins.compiler.as8080.ParsingUtils.parseLabel;

public class PseudoLabel extends Node {
    public final String label;

    public PseudoLabel(int line, int column, String label) {
        super(line, column);
        this.label = Objects.requireNonNull(label);
    }

    public PseudoLabel(Token label) {
        this(label.getLine(), label.getCharPositionInLine(), parseLabel(label));
    }

    @Override
    public Either<Node, Evaluated> eval(int currentAddress, int expectedSizeBytes, NameSpace env) {
        Evaluated evaluated = new Evaluated(line, column, currentAddress, expectedSizeBytes);
        evaluated.addChild(new ExprNumber(line, column, currentAddress));
        return Either.ofRight(evaluated);
    }

    public Either<Node, Evaluated> eval(int currentAddress, int expectedSizeBytes, NameSpace env, boolean evalLeft) {
        if (evalLeft) {
            return Either.ofLeft(this);
        }
        return eval(currentAddress, expectedSizeBytes, env);
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    protected String toStringShallow() {
        return "Label(" + label +")";
    }

    @Override
    protected Node mkCopy() {
        return new PseudoLabel(line, column, label);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PseudoLabel pseudoLabel1 = (PseudoLabel) o;
        return Objects.equals(label, pseudoLabel1.label);
    }
}
