package net.emustudio.plugins.compiler.as8080.ast.expr;

import net.emustudio.plugins.compiler.as8080.Either;
import net.emustudio.plugins.compiler.as8080.ast.*;
import org.antlr.v4.runtime.Token;

import java.util.Objects;

import static net.emustudio.plugins.compiler.as8080.ParsingUtils.normalizeId;

public class ExprId extends Node {
    public final String id;

    public ExprId(int line, int column, String id) {
        super(line, column);
        this.id = Objects.requireNonNull(id);
    }

    public ExprId(Token id) {
        this(id.getLine(), id.getCharPositionInLine(), id.getText());
    }

    @Override
    public Either<Node, Evaluated> eval(int currentAddress, int expectedSizeBytes, NameSpace env) {
        return env.get(normalizeId(id)).orElseGet(() -> Either.ofLeft(this));
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    protected String toStringShallow() {
        return "ExprId(" + id + ")";
    }

    @Override
    protected Node mkCopy() {
        return new ExprId(line, column, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExprId exprId = (ExprId) o;
        return Objects.equals(id, exprId.id);
    }
}
